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
import xmut.cs.ojbackend.entity.VO.VOSubmissionDetail;
import xmut.cs.ojbackend.service.SubmissionService;
import xmut.cs.ojbackend.utils.CommonUtil;

import java.util.Objects;

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

    @Autowired
    private CommonUtil commonUtil;

    //@PostMapping("save")
    public boolean save(@RequestBody Submission submission) {
        return submissionService.save(submission);
    }

    @GetMapping("list")
    public Object list( Integer page, Integer limit, Integer result, String username, Integer myself ){
        if( myself == 1 ){
            username = commonUtil.getCurrentUser().getUsername();
        }
        Object ret = Result.success(submissionService.listPage(page, limit, result, username ));
        return ret;
    }

    @GetMapping("get")
    public Object getInfo( String id ) {
        //return Result.success(submissionService.getById(id));
        VOSubmissionDetail ret = submissionService.getInfo(id);
        User user = commonUtil.getCurrentUser();
        if(Objects.equals(user.getAdminType(), User.ADMINTYPE_REGULAR) && !Objects.equals(user.getId(), ret.getUserId())){
            return Result.error( Result.ACCESS_DENIED );
        }
        return Result.success(ret);
    }

    @GetMapping("result")
    public Object getResult( String id ) {
        return Result.success(submissionService.getSubmitResult(id));
    }

    @PostMapping("submit")
    public Object submitCode( @RequestBody Submission submission ) throws JsonProcessingException{
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        //return submissionService.submitCode(submission, request.getRemoteAddr(), user);
        return submissionService.submitCode(submission, commonUtil.getIpAddr(request), user);
    }

}