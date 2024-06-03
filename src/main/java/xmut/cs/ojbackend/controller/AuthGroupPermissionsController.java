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
import xmut.cs.ojbackend.entity.AuthGroupPermissions;
import xmut.cs.ojbackend.service.AuthGroupPermissionsService;
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
@RequestMapping("/authGroupPermissions")
public class AuthGroupPermissionsController {

    @Autowired
    private AuthGroupPermissionsService authGroupPermissionsService;

    /**
     * 添加。
     *
     * @param authGroupPermissions 
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody AuthGroupPermissions authGroupPermissions) {
        return authGroupPermissionsService.save(authGroupPermissions);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return authGroupPermissionsService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param authGroupPermissions 
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody AuthGroupPermissions authGroupPermissions) {
        return authGroupPermissionsService.updateById(authGroupPermissions);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<AuthGroupPermissions> list() {
        return authGroupPermissionsService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public AuthGroupPermissions getInfo(@PathVariable Serializable id) {
        return authGroupPermissionsService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<AuthGroupPermissions> page(Page<AuthGroupPermissions> page) {
        return authGroupPermissionsService.page(page);
    }

}
