package xmut.cs.ojbackend.config;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import xmut.cs.ojbackend.entity.LoginUser;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.mapper.UserMapper;
import xmut.cs.ojbackend.utils.JwtUtil;
import xmut.cs.ojbackend.utils.Pkdf2Encoder;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Pkdf2Encoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(
                request->request.requestMatchers("/problem/*").permitAll()
                        .requestMatchers("user/login").permitAll()
                        .anyRequest().authenticated()
        )
        .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        http.exceptionHandling(
                handling -> handling.authenticationEntryPoint(delegatedAuthenticationEntryPoint)
        );
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
        UserMapper userMapper;

        @Autowired
        RedisTemplate<Object, Object> redisTemplate;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String token = request.getHeader("Authorization");

            if( token == null || token.isEmpty()){
                filterChain.doFilter(request, response );
                return;
            }

            Integer userId  = (Integer) JwtUtil.parseToken(token,"id");
            JSONObject tmp = (JSONObject)redisTemplate.opsForValue().get("user-id:"+userId.toString());
            if(tmp != null ) {
                User user = tmp.toJavaObject(User.class);
                LoginUser loginUser = new LoginUser(user);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response );
        }
    }
}
