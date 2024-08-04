package xmut.cs.ojbackend.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.DTO.DTOUserAdminUpdate;
import xmut.cs.ojbackend.entity.DTO.DTOUserImport;
import xmut.cs.ojbackend.service.UserService;

import java.util.List;

/**
 *  控制层。
 *
 * @author leon
 * @since 2024-06-03
 */
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @GetMapping("list")
    public Object list( Integer page, Integer limit, String keyword, String userType ){
        return Result.success(userService.listPage( page, limit, keyword, userType ));
    }

    @GetMapping("list-admin")
    public Object listAdmin(){
        return Result.success(userService.listAdmin());
    }

    @PostMapping("import")
    public Object importUsers(@RequestBody List<DTOUserImport> userList){
        //System.out.println(userList);
        return Result.success(userService.importUsers(userList));
    }

    @PostMapping("update")
    public Object update(@RequestBody DTOUserAdminUpdate userUpdate ){
        return Result.success(userService.adminUpdate(userUpdate));
    }
}
