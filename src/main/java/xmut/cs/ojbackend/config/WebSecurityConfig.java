package xmut.cs.ojbackend.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import xmut.cs.ojbackend.entity.LoginUser;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.utils.JwtUtil;
import xmut.cs.ojbackend.utils.Pkdf2Encoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Pkdf2Encoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(
                request->request
                        .requestMatchers(new MvcRequestMatcher( new HandlerMappingIntrospector(), "/user/login")).permitAll()
                        .requestMatchers(new MvcRequestMatcher( new HandlerMappingIntrospector(), "/opt")).permitAll()
                        .requestMatchers(new MvcRequestMatcher( new HandlerMappingIntrospector(), "/opt/**")).permitAll()
                        .requestMatchers(new MvcRequestMatcher( new HandlerMappingIntrospector(), "/user/stat")).permitAll()
                        .requestMatchers(new MvcRequestMatcher( new HandlerMappingIntrospector(), "/judger/heartbeat")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher( "/druid/**")).permitAll()
                        .requestMatchers(new MvcRequestMatcher( new HandlerMappingIntrospector(), "/admin/**")).hasRole("admin")
                        //.requestMatchers("/admin/problem/export").permitAll()
                        .anyRequest().authenticated()
                        //.anyRequest().permitAll()
//                request->request.anyRequest().permitAll()
        )
        .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        http.exceptionHandling(
                handling -> handling.authenticationEntryPoint(customAuthenticationEntryPoint)
                                    .accessDeniedHandler(customAccessDeniedHandler)
        );
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource url = new UrlBasedCorsConfigurationSource();
        url.registerCorsConfiguration("/**",corsConfiguration);
        http.cors( p->p.configurationSource(url));

        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Component
    public static class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
        @Autowired
        RedisTemplate<Object, Object> redisTemplate;

        @Autowired
        private HandlerExceptionResolver handlerExceptionResolver;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException, AuthenticationException {
            try {
                String token = request.getHeader("Authorization");

                if (token == null || token.isEmpty()) {
                    filterChain.doFilter(request, response);
                    return;
                }

                Integer userId = (Integer) JwtUtil.parseToken(token, "id");
                User user = (User)redisTemplate.opsForValue().get("user-id:" + userId.toString());
                if( user == null ){
                    filterChain.doFilter(request, response);
                    return;
                }
                LoginUser loginUser = new LoginUser(user);
                List<GrantedAuthority> authorities = null;
                if( user.getAdminType() == User.ADMINTYPE_ADMIN || user.getAdminType() == User.ADMINTYPE_SUPERADMIN ){
                    authorities =  new ArrayList<GrantedAuthority>();
                    authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
                }
                if( user.getAdminType() == User.ADMINTYPE_SUPERADMIN ) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_super"));
                }
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            catch(TokenExpiredException e){
                throw new AccountExpiredException("failed");
            }
            catch( Exception e){
                handlerExceptionResolver.resolveException(request, response, null, e );
            }
            filterChain.doFilter(request, response );
        }
    }
}
