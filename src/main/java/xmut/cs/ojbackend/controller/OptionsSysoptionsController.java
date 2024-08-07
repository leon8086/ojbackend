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
import xmut.cs.ojbackend.entity.OptionsSysoptions;
import xmut.cs.ojbackend.service.OptionsSysoptionsService;
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
@RequestMapping("/opt_info")
public class OptionsSysoptionsController {

    @Autowired
    private OptionsSysoptionsService optionsSysoptionsService;

    /**
     * 添加。
     *
     * @param optionsSysoptions 
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody OptionsSysoptions optionsSysoptions) {
        return optionsSysoptionsService.save(optionsSysoptions);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return optionsSysoptionsService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param optionsSysoptions 
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody OptionsSysoptions optionsSysoptions) {
        return optionsSysoptionsService.updateById(optionsSysoptions);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public Object list() {
        Result ret = optionsSysoptionsService.getInfo();
        return optionsSysoptionsService.getInfo();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public OptionsSysoptions getInfo(@PathVariable Serializable id) {
        return optionsSysoptionsService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<OptionsSysoptions> page(Page<OptionsSysoptions> page) {
        return optionsSysoptionsService.page(page);
    }

}
