package xmut.cs.ojbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.LoginUser;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.service.UserService;
import xmut.cs.ojbackend.utils.CommonUtil;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Map;

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
    DataSource dataSource;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonUtil commonUtil;

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

    @GetMapping("problem-status")
    public Result problemStatus(){
        return Result.success(userService.getProblemStatus());
    }

    @GetMapping("rank")
    public Result userRank( Integer page, Integer limit, Integer grade ){
        return Result.success( userService.userRank(page, limit, grade) );
    }

    @GetMapping("rank-tag")
    public Result userRankTag( Integer page, Integer limit, Integer grade, Integer tag ){
        return Result.success( userService.userRankMajorTag(page, limit, grade, tag) );
    }

    @PostMapping( "reset-psw" )
    public Result resetPassword( @RequestBody Map<String, String> params ){
        User user = commonUtil.getCurrentUser();
        String newPassword = params.get("password");
        String repeat = params.get("repeat");
        String original = params.get("original");
        if( !newPassword.equals(repeat) ){
            return Result.error(Result.WRONG_PARAMS,"密码和重复密码不相等");
        }
        Result obj = userService.resetPassword( user, original, newPassword );
        return obj;
    }

    @GetMapping("user-status")
    public Result getUserStatus(){
        return Result.success(userService.getUserStatus());
    }
}
