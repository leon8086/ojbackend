package xmut.cs.ojbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.service.GradeService;

/**
 *  控制层。
 *
 * @author leon
 * @since 2024-08-14
 */
@RestController
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("all")
    public Object list() {
        return Result.success(gradeService.all());
    }
}
