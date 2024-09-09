package xmut.cs.ojbackend.controller.admin;

import com.mybatisflex.core.util.UpdateEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.DTO.DTOUserAdminUpdate;
import xmut.cs.ojbackend.entity.DTO.DTOUserImport;
import xmut.cs.ojbackend.entity.Grade;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.service.GradeService;
import xmut.cs.ojbackend.service.UserService;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private GradeService gradeService;

    @GetMapping("list")
    public Object list( Integer page, Integer limit, String keyword, String userType ){
        return Result.success(userService.listPage( page, limit, keyword, userType ));
    }

    @GetMapping("no-admin")
    public Object listNoAdmin( ){
        return Result.success(userService.listNoAdmin());
    }

    @GetMapping("list-course-add-user")
    public Object listCourseAddUser( String keyword, Integer id ){
        return Result.success(userService.listGetCourseAddStudent( keyword, id ));
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

    @GetMapping("user-status")
    public Result getUserStatus( Integer id ){
        return Result.success(userService.adminGetUserStatus(id));
    }

    @PostMapping("reset-psw")
    public Result resetUserPsw( @RequestBody Map<String,String> params ){
        Integer id = Integer.parseInt(params.get("id"));
        return Result.success(userService.adminResetUserPSW(id));
    }

    @PostMapping("new-user")
    public Result newUser( @RequestBody User user) throws Exception{
        return Result.success( userService.adminNewUser(user));
    }

    @PostMapping("new-grade")
    public Result newGrade( @RequestBody Map<String, String> params ){
        String name = params.get("name");
        Grade grade = new Grade();
        grade.setName(name);
        gradeService.save(grade);
        return Result.success(grade);
    }

    @PostMapping("update-grade")
    public Result updateGrade( @RequestBody Grade grade ){
        Grade ug = UpdateEntity.of(Grade.class, grade.getId());
        return Result.success(grade);
    }
}
