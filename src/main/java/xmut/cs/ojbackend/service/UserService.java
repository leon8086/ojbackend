package xmut.cs.ojbackend.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
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

    Object importUsers(List<DTOUserImport> userList);

    Object listAdmin();

    Object adminUpdate(DTOUserAdminUpdate userUpdate);
}
