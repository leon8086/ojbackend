package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.LoginUser;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.entity.VO.VOUserLogin;
import xmut.cs.ojbackend.entity.table.UserTableDef;
import xmut.cs.ojbackend.mapper.UserMapper;
import xmut.cs.ojbackend.service.UserService;
import org.springframework.stereotype.Service;
import xmut.cs.ojbackend.utils.JwtUtil;

import java.util.concurrent.TimeUnit;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    public User getByUsernameWithoutPassword(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.where(UserTableDef.USER.USERNAME.eq(username));
        return this.mapper.selectOneByQuery(queryWrapper);
    }

    public Boolean isUsernameExist(String username) {
        User user = this.mapper.selectOneByCondition(UserTableDef.USER.USERNAME.eq(username));
        return user != null;
    }

    public Boolean isUsernameExist(String username, Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.where(UserTableDef.USER.USERNAME.eq(username)).and(UserTableDef.USER.ID.ne(id));
        return this.exists(queryWrapper);
    }

    public Boolean isEmailExist(String email) {
        User user = this.mapper.selectOneByCondition(UserTableDef.USER.EMAIL.eq(email));
        return user != null;
    }

    public Boolean isEmailExist(String email, Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.where(UserTableDef.USER.EMAIL.eq(email)).and(UserTableDef.USER.ID.ne(id));
        return this.exists(queryWrapper);
    }

    public User getByUsernameWithPassword(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(UserTableDef.USER.ALL_COLUMNS);
        queryWrapper.where(UserTableDef.USER.USERNAME.eq(username));
        return this.mapper.selectOneByQuery(queryWrapper);
    }

    @Override
    public Page<User> page(Integer pageNum, Integer pageSize, String keyword) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(UserTableDef.USER.ALL_COLUMNS).from(UserTableDef.USER);
        queryWrapper.orderBy("id");
        if (keyword != null && !keyword.isEmpty()) {
            //从邮箱,用户名里面搜索
            queryWrapper.and(UserTableDef.USER.USERNAME.like("%" + keyword + "%")
                    .or(UserTableDef.USER.EMAIL.like("%" + keyword + "%")));
        }
        return this.mapper.paginate(Page.of(pageNum, pageSize), queryWrapper);
    }

    @Override
    public Object login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        }
        catch( Exception e ){
            return Result.loginFailed();
        }
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        User redisUser = this.mapper.selectOneWithRelationsById(loginUser.getUser().getId());
        String key = "user-id:"+redisUser.getId().toString();
        // 超时时间1天
        redisTemplate.opsForValue().set(key, redisUser, 1, TimeUnit.DAYS);
        String jwt = JwtUtil.generateToken(loginUser.getUser());
        VOUserLogin userRet = new VOUserLogin();
        userRet.setId(loginUser.getUser().getId());
        userRet.setUsername(loginUser.getUser().getUsername());
        userRet.setAdminType(loginUser.getUser().getAdminType());
        userRet.setIsDisabled(loginUser.getUser().getIsDisabled());
        userRet.setToken(jwt);
        return Result.success(userRet);
    }

    @Override
    public Object logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer userid = loginUser.getUser().getId();
        redisTemplate.delete("user-id:"+userid);
        return Result.success(0);
    }
}
