package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.entity.table.UserTableDef;
import xmut.cs.ojbackend.mapper.UserMapper;
import xmut.cs.ojbackend.service.UserService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
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
        if (keyword != null && !keyword.equals("")) {
            //从邮箱,用户名里面搜索
            queryWrapper.and(UserTableDef.USER.USERNAME.like("%" + keyword + "%")
                    .or(UserTableDef.USER.EMAIL.like("%" + keyword + "%")));
        }
        return this.mapper.paginate(Page.of(pageNum, pageSize), queryWrapper);
    }
}
