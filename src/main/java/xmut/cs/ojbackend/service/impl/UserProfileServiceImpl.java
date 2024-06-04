package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import xmut.cs.ojbackend.entity.UserProfile;
import xmut.cs.ojbackend.entity.table.UserProfileTableDef;
import xmut.cs.ojbackend.mapper.UserProfileMapper;
import xmut.cs.ojbackend.service.UserProfileService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class UserProfileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfile> implements UserProfileService {
    @Override
    public UserProfile getByUserId(Integer userId) {
        return this.getOne(UserProfileTableDef.USER_PROFILE.USER_ID.eq(userId));
    }

    @Override
    public Page<UserProfile> page(Integer pageNum, Integer pageSize, String rule) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (rule.equals("ACM")) {
            queryWrapper.orderBy(UserProfileTableDef.USER_PROFILE.ACCEPTED_NUMBER, false);
        } else {
            queryWrapper.orderBy(UserProfileTableDef.USER_PROFILE.TOTAL_SCORE, false);
        }
        return this.mapper.paginateWithRelations(Page.of(pageNum, pageSize), queryWrapper);
    }

    @Override
    public void removeByUserId(Integer userId) {
        this.mapper.deleteByCondition(UserProfileTableDef.USER_PROFILE.USER_ID.eq(userId));
    }
}
