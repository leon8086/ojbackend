package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.DTO.DTOUserAdminUpdate;
import xmut.cs.ojbackend.entity.DTO.DTOUserImport;
import xmut.cs.ojbackend.entity.LoginUser;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.entity.VO.VOUserBrief;
import xmut.cs.ojbackend.entity.VO.VOUserLogin;
import xmut.cs.ojbackend.mapper.UserMapper;
import xmut.cs.ojbackend.service.UserService;
import xmut.cs.ojbackend.utils.CommonUtil;
import xmut.cs.ojbackend.utils.JwtUtil;
import xmut.cs.ojbackend.utils.Pkdf2Encoder;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static xmut.cs.ojbackend.entity.table.UserTableDef.USER;
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
    UserMapper userMapper;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    CommonUtil commonUtil;

    public User getByUsernameWithoutPassword(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.where(USER.USERNAME.eq(username));
        return this.mapper.selectOneByQuery(queryWrapper);
    }

    public Boolean isUsernameExist(String username) {
        User user = this.mapper.selectOneByCondition(USER.USERNAME.eq(username));
        return user != null;
    }

    public Boolean isUsernameExist(String username, Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.where(USER.USERNAME.eq(username)).and(USER.ID.ne(id));
        return this.exists(queryWrapper);
    }

    public Boolean isEmailExist(String email) {
        User user = this.mapper.selectOneByCondition(USER.EMAIL.eq(email));
        return user != null;
    }

    public Boolean isEmailExist(String email, Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.where(USER.EMAIL.eq(email)).and(USER.ID.ne(id));
        return this.exists(queryWrapper);
    }

    public User getByUsernameWithPassword(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(USER.ALL_COLUMNS);
        queryWrapper.where(USER.USERNAME.eq(username));
        return this.mapper.selectOneByQuery(queryWrapper);
    }

    @Override
    public Page<User> page(Integer pageNum, Integer pageSize, String keyword) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(USER.ALL_COLUMNS).from(USER);
        queryWrapper.orderBy("id");
        if (keyword != null && !keyword.isEmpty()) {
            //从邮箱,用户名里面搜索
            queryWrapper.and(USER.USERNAME.like("%" + keyword + "%")
                    .or(USER.EMAIL.like("%" + keyword + "%")));
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

    public VOUserLogin getLogedUser( String token ){
        VOUserLogin userRet = new VOUserLogin();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userRet.setId(loginUser.getUser().getId());
        userRet.setUsername(loginUser.getUser().getUsername());
        userRet.setAdminType(loginUser.getUser().getAdminType());
        userRet.setIsDisabled(loginUser.getUser().getIsDisabled());
        userRet.setToken(token);
        return userRet;
    }

    @Override
    public Object check( String token ) {
        return getLogedUser(token);
    }

    @Override
    public Object listPage(Integer page, Integer limit, String keyword, String userType) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 1000;
        }
        QueryWrapper wrapper = new QueryWrapper();
        if( keyword != null && !keyword.isEmpty() ){
            wrapper.where(USER.USERNAME.like(keyword));
        }
        if( userType != null && !userType.isEmpty()){
            wrapper.where(USER.ADMIN_TYPE.eq(userType));
        }
        wrapper.orderBy(USER.ID.asc());
        return userMapper.paginateWithRelationsAs(page, limit, wrapper, User.class);
    }

    @Override
    public Object importUsers(List<DTOUserImport> userList) {
        Pkdf2Encoder encoder = new Pkdf2Encoder();
        List<VOUserLogin> failed = new ArrayList<VOUserLogin>();
        int count = 0;
        for( DTOUserImport u : userList ){
            User user = new User();
            user.setPassword( encoder.encode(u.getPassword()));
            user.setUsername( u.getUsername() );
            user.setEmail( u.getEmail() );
            user.setCreateTime(Calendar.getInstance().getTime());
            user.setAdminType( u.getAdminType());
            user.setIsDisabled(false);
            user.setGrade(u.getGrade());
            user.setRealName(u.getRealName());
            user.setFirstLogin(true);
            try {
                getMapper().insert(user);
                count ++;
            }
            catch(Exception e){
                if( failed.size() < 10){
                    VOUserLogin ur = new VOUserLogin();
                    ur.setUsername(u.getUsername());
                    ur.setRealName(e.getClass().toString());
                    ur.setToken(e.getMessage());
                    failed.add(ur);
                }
            }
        }
        Map<String, Object> ret = new HashMap<>();
        ret.put( "insert", count );
        ret.put("failed", failed);
        return ret;
    }

    @Override
    public Object listAdmin() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where(USER.ADMIN_TYPE.le(2));
        wrapper.and( USER.IS_DISABLED.eq(false));
        return userMapper.selectListByQueryAs( wrapper, VOUserBrief.class);
    }

    @Override
    public Object adminUpdate(DTOUserAdminUpdate userUpdate) {
        User user = UpdateEntity.of(User.class, userUpdate.getId());
        if( userUpdate.getPassword() != null){
            Pkdf2Encoder encoder = new Pkdf2Encoder();
            user.setPassword(encoder.encode(userUpdate.getPassword()));
        }
        user.setAdminType(userUpdate.getAdminType());
        user.setIsDisabled(userUpdate.getIsDisabled());
        user.setRealName(userUpdate.getRealName());
        user.setEmail(userUpdate.getEmail());
        user.setGrade(userUpdate.getGrade());
        String key = "user-id:"+user.getId().toString();
        redisTemplate.delete(key); // 删除redis-key，保证数据一致性。
        return getMapper().update(user);
    }
}
