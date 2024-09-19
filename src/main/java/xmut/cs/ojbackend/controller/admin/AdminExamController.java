package xmut.cs.ojbackend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.Exam;
import xmut.cs.ojbackend.service.ExamService;

import java.util.Map;

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
        return Result.success( examService.adminUpdateExam(exam) );
    }

    @GetMapping("get")
    public Result get( Integer id ) {
        return Result.success(examService.adminGetExamDetail(id));
    }

    @GetMapping("list")
    public Object list( Integer page, Integer limit, String keyword, Integer courseId ){
        Object ret = examService.adminListPage(page, limit, keyword, courseId );
        return Result.success(ret);
    }
    @GetMapping("list-by-owner")
    public Object listByOwner( Integer page, Integer limit, String keyword, Integer courseId ){
        Object ret = examService.adminListByOwnerPage(page, limit, keyword, courseId );
        return Result.success(ret);
    }

    @PostMapping("restart")
    public Result restart( @RequestBody Exam exam ){
        return Result.success( examService.restart(exam.getId(), exam.getEndTime() ) );
    }

    @GetMapping("submission")
    public Result getExamSubmission( Integer examId, Integer userId ){
        return Result.success(examService.adminGetExamSubmission(examId, userId));
    }

    @PostMapping("close")
    public Object close( @RequestBody Map<String, Object> params){
        Integer id = (Integer)params.get("id");
        Boolean isEnded = (Boolean) params.get("isEnded");

        Object ret = examService.adminCloseExam( id, isEnded );
        return Result.success(ret);
    }

    @GetMapping("user-profile")
    public Result userProfile( Integer examId, Integer userId ){
        return Result.success( examService.adminGetUserExamProfile( examId, userId ) );
    }
}
