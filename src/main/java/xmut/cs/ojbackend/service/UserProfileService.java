package xmut.cs.ojbackend.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import xmut.cs.ojbackend.entity.UserProfile;

/**
 *  服务层。
 *
 * @author leon
 * @since 2024-06-03
 */
public interface UserProfileService extends IService<UserProfile> {
    UserProfile getByUserId(Integer userId);

    Page<UserProfile> page(Integer pageNum, Integer pageSize, String rule);

    void removeByUserId(Integer userId);
}
