package xmut.cs.ojbackend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.service.ProblemTagService;

import java.util.Map;

/**
 *  控制层。
 *
 * @author leon
 * @since 2024-06-03
 */
@RestController
@RequestMapping("/admin/tag")
public class AdminProblemTagController {

    @Autowired
    private ProblemTagService problemTagService;

    @GetMapping("list-major")
    public Object listMajor( String keyword) {
        return Result.success(problemTagService.adminListMajor( keyword));
    }

    @PostMapping("new")
    public Result newTag(@RequestBody Map<String, Object> params ){
        System.out.println(params);
        String name = (String)params.get("name");
        Integer parentId = (Integer) params.get("parentId");
        return Result.success(problemTagService.addTag( name, parentId ));
    }
}
