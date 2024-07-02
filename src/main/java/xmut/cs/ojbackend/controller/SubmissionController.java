package xmut.cs.ojbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybatisflex.core.paginate.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.Submission;
import xmut.cs.ojbackend.service.ProblemService;
import xmut.cs.ojbackend.service.SubmissionService;
import org.springframework.web.bind.annotation.RestController;
import xmut.cs.ojbackend.utils.DateUtil;
import xmut.cs.ojbackend.utils.JudgeUtil;
import xmut.cs.ojbackend.utils.JwtUtil;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

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
        return submissionService.submitCode(submission, request.getRemoteAddr(), 1);
    }

}