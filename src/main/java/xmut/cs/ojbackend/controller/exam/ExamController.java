package xmut.cs.ojbackend.controller.exam;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.Exam;
import xmut.cs.ojbackend.entity.ExamSubmission;
import xmut.cs.ojbackend.entity.LoginUser;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.service.exam.ExamService;

/**
 *  控制层。
 *
 * @author leon
 * @since 2024-06-26
 */
@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("problems")
    public Object getProblems(@RequestBody Exam exam ){
        return Result.success(examService.getProblems(exam));
    }

    @GetMapping("list")
    public Object list() {
        return Result.success( examService.list() );
    }

    @GetMapping("get")
    public Result getInfo( Integer id) {
        return Result.success(examService.getInfo(id));
    }

    @PostMapping("submit")
    public Result submitCode( @RequestBody ExamSubmission examSubmission ) throws JsonProcessingException {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        ExamSubmission ret = (ExamSubmission) examService.submitCode( examSubmission, httpServletRequest.getRemoteAddr(), user );
        return Result.success(ret);
    }

    @GetMapping("submit_result")
    public Result submitResult( String id ){
        return Result.success(examService.getSubmissionById(id));
    }

    @GetMapping( "submission_list")
    public Result getSubmissions( String examId, Integer problemId ){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        return Result.success(examService.getProblemSubmission( examId, problemId, user.getId() ));
    }

    @GetMapping("profile")
    public Result getExamProfile( Integer examId ){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        return Result.success(examService.getExamProfile( user.getId(), examId));
    }

    @GetMapping("rank")
    public Result getExamRank( Integer examId ){
        return Result.success(examService.getExamRank(examId));
    }
}
