package xmut.cs.ojbackend.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.DTO.DTOUserAdminUpdate;
import xmut.cs.ojbackend.entity.DTO.DTOUserImport;
import xmut.cs.ojbackend.entity.User;

import java.util.List;

/**
 *  服务层。
 *
 * @author leon
 * @since 2024-06-03
 */
public interface UserService extends IService<User> {
    User getByUsernameWithoutPassword(String username);

    User getByUsernameWithPassword(String username);

    Boolean isUsernameExist(String username);

    Boolean isUsernameExist(String username, Integer id);


    Boolean isEmailExist(String email);

    Boolean isEmailExist(String email, Integer id);

    Page<User> page(Integer pageNum, Integer pageSize, String keyword);

    Object login(User user);

    Object logout();

    Object check( String token );

    Object listPage(Integer page, Integer limit, String keyword, String userType);

    Integer fetchGradeId(String gradeName);

    Object importUsers(List<DTOUserImport> userList);

    Object listAdmin();

    Object adminUpdate(DTOUserAdminUpdate userUpdate);

    Object getProblemStatus();

    Object userRank(Integer page, Integer limit, Integer grade);

    Object userRankMajorTag( Integer page, Integer limit, Integer grade, Integer tag );

    Result resetPassword(User user, String original, String newPassword);

    Object getUserStatus();

    Object adminGetUserStatus(Integer id);

    Object adminResetUserPSW(Integer id);

    Object adminNewUser( User user );

    public Object listGetCourseAddStudent(String keyword, Integer courseId);

    Object listNoAdmin();

    Object adminGetUser(Integer id);
}
