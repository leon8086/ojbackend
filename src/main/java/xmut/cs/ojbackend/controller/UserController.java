package xmut.cs.ojbackend.controller;

import com.mybatisflex.core.paginate.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.LoginUser;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.service.UserService;

import java.io.Serializable;

/**
 *  控制层。
 *
 * @author leon
 * @since 2024-06-03
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("login")
    public Object login( @RequestBody User user){
        return userService.login(user);
    }

    @PostMapping("logout")
    public Object logout(){
        return userService.logout();
    }

    //@PostMapping("save")
    public boolean save(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("getInfo/{id}")
    public User getInfo(@PathVariable Serializable id) {
        return userService.getById(id);
    }

    @GetMapping("profile")
    public User profile() {
        LoginUser loginUser = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        //return Result.success(userProfileService)
        return null;
    }

    @GetMapping("check")
    public Result check(){
        String token = httpServletRequest.getHeader("Authorization");
        return Result.success(userService.check(token));
    }

    @GetMapping("page")
    public Page<User> page(Page<User> page) {
        return userService.page(page);
    }

}
