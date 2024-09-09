package xmut.cs.ojbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.service.CourseService;

/**
 *  控制层。
 *
 * @author leon
 * @since 2024-06-03
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("all")
    public Object all( ){
        return Result.success(courseService.allBrief());
    }
}
