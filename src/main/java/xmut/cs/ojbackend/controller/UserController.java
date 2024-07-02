package xmut.cs.ojbackend.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.service.UserService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

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

    //@GetMapping("list")
    public List<User> list() {
        return userService.list();
    }

    @GetMapping("getInfo/{id}")
    public User getInfo(@PathVariable Serializable id) {
        return userService.getById(id);
    }

    @GetMapping("page")
    public Page<User> page(Page<User> page) {
        return userService.page(page);
    }

}
