package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xmut.cs.ojbackend.entity.LoginUser;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.mapper.UserMapper;

import static xmut.cs.ojbackend.entity.table.UserTableDef.USER;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select(USER.ID, USER.USERNAME, USER.PASSWORD, USER.IS_DISABLED, USER.ADMIN_TYPE, USER.FIRST_LOGIN);
        wrapper.where(USER.USERNAME.eq(username));
        User user = userMapper.selectOneByQuery(wrapper);
        if( user == null ){
            return null;
        }
        return new LoginUser(user);
    }
}
