package xmut.cs.ojbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.Problem;
import xmut.cs.ojbackend.entity.entitymapper.VoProblemTitleWrapper;
import xmut.cs.ojbackend.service.ProblemService;

/**
 *  控制层。
 *
 * @author leon
 * @since 2024-06-03
 */
@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private VoProblemTitleWrapper voProblemTitleWrapper;

    @PostMapping("save")
    public boolean save(@RequestBody Problem problem) {
        return problemService.save(problem);
    }

    @PutMapping("update")
    public boolean update(@RequestBody Problem problem) {
        return problemService.updateById(problem);
    }

    @GetMapping("list")
    public Object list( Integer page, Integer limit, String keyword, String difficulty, Integer[] tags ){
        Object ret = Result.success(problemService.listPage(page, limit, keyword, difficulty, tags ));
        return ret;
    }

    @GetMapping("detail")
    public Object getInfo(Integer id) {
        Object ret = problemService.info(id);
        return Result.success(ret);
    }

    @GetMapping("brief-all")
    public Result getAllBrief(){
        Object ret = problemService.getAllBrief();
        return Result.success(ret);
    }
}
