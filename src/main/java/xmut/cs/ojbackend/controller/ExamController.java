package xmut.cs.ojbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.config.ExamCheckException;
import xmut.cs.ojbackend.entity.Exam;
import xmut.cs.ojbackend.entity.ExamSubmission;
import xmut.cs.ojbackend.entity.LoginUser;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.service.ExamService;
import xmut.cs.ojbackend.utils.CommonUtil;

import java.util.HashMap;
import java.util.Map;

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
    private CommonUtil commonUtil;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("problems")
    public Object getProblems(@RequestBody Exam exam ) throws ExamCheckException {
        return Result.success(examService.getProblems(exam));
    }

    @GetMapping("list-valid")
    public Object listValid() {
        return Result.success( examService.listValidExam() );
    }

    @GetMapping("list")
    public Object list( Integer page, Integer limit, Integer courseId, String keyword ) {
        return Result.success( examService.listPageExam( page, limit, courseId, keyword ) );
    }

    @GetMapping("get-brief")
    public Result getInfo( Integer id) {
        return Result.success(examService.getInfo(id));
    }

    @PostMapping("submit")
    public Result submitCode( @RequestBody ExamSubmission examSubmission ) throws JsonProcessingException, ExamCheckException {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Object ret =  examService.submitCode( examSubmission, commonUtil.getIpAddr(httpServletRequest), user );
        if( ret == null ){
            Map<String, Object> m = new HashMap<>();
            m.put("id", null);
            m.put("message", "考试已经结束");
            return Result.success(m);
        }
        return Result.success(ret);
    }

    @GetMapping("submit_result")
    public Result submitResult( String id ){
        return Result.success(examService.getSubmissionById(id));
    }

    @GetMapping( "submission_list")
    public Result getSubmissions( String examId ){
        return Result.success(examService.getProblemSubmission( examId ));
    }

    @GetMapping("profile")
    public Result getExamProfile( Integer examId ){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        return Result.success(examService.getExamProfile( user.getId(), examId));
    }

    @GetMapping("rank")
    public Result getExamRank( Integer examId ){
        if( examId == null){
            return Result.error( Result.WRONG_PARAMS);
        }
        return Result.success(examService.getExamRank(examId));
    }

    @PostMapping("quit")
    public Result quitExam( @RequestBody Map<String, Object> params ) throws ExamCheckException {
        if( !params.containsKey("examId") ){
            return Result.error( Result.WRONG_PARAMS );
        }
        Integer examId = Integer.parseInt((String)params.get("examId"));
        return Result.success(examService.quitExam(examId));
    }
}
