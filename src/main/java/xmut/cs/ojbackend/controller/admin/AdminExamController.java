package xmut.cs.ojbackend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.Exam;
import xmut.cs.ojbackend.service.exam.ExamService;

/**
 *  控制层。
 *
 * @author leon
 * @since 2024-06-26
 */
@RestController
@RequestMapping("/admin/exam")
public class AdminExamController {

    @Autowired
    private ExamService examService;

    @PostMapping("update_exam")
    public Object update(@RequestBody Exam exam) {
        System.out.println(exam);
        return Result.success( examService.updateExam(exam) );
    }

    @GetMapping("get")
    public Result get( Integer id ) {
        return Result.success(examService.getExamDetail(id));
    }
}
