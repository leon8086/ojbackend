package xmut.cs.ojbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.LoginUser;
import xmut.cs.ojbackend.entity.Submission;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.service.SubmissionService;

/**
 *  控制层。
 *
 * @author leon
 * @since 2024-06-03
 */
@RestController
@RequestMapping("/submission")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private HttpServletRequest request;

    //@PostMapping("save")
    public boolean save(@RequestBody Submission submission) {
        return submissionService.save(submission);
    }

    @GetMapping("list")
    public Object list( Integer page, Integer limit, Integer result, String username ){
        Object ret = Result.success(submissionService.listPage(page, limit, result, username ));
        return ret;
    }

    @GetMapping("exam_list")
    public Object listExam(){
        return submissionService.listExam();
    }

    @GetMapping("get")
    public Object getInfo( String id ) {
        //return Result.success(submissionService.getById(id));
        return submissionService.getInfo(id);
    }

    @GetMapping("result")
    public Object getResult( String id ) {
        return Result.success(submissionService.getSubmitResult(id));
    }

    @PostMapping("submit")
    public Object submitCode( @RequestBody Submission submission ) throws JsonProcessingException{
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        return submissionService.submitCode(submission, request.getRemoteAddr(), user);
    }

}