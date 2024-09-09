package xmut.cs.ojbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.service.ProblemTagService;

/**
 *  控制层。
 *
 * @author leon
 * @since 2024-06-03
 */
@RestController
@RequestMapping("/tag")
public class ProblemTagController {

    @Autowired
    private ProblemTagService problemTagService;

    @GetMapping("list")
    public Object list() {
        return Result.success(problemTagService.listMajor(null));
    }
}
