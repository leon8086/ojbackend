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

    @Autowired
    private ProblemService problemService;

    @Autowired
    private JudgeUtil judgeUtil;


    /**
     * 添加。
     *
     * @param submission 
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody Submission submission) {
        return submissionService.save(submission);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return submissionService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param submission 
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody Submission submission) {
        return submissionService.updateById(submission);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<Submission> list() {
        return submissionService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public Submission getInfo(@PathVariable Serializable id) {
        return submissionService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<Submission> page(Page<Submission> page) {
        return submissionService.page(page);
    }

    @PostMapping("submit")
    public Object commitCode( @RequestBody Submission submission ) throws JsonProcessingException{
        //submission.setCreateTime(DateUtil.getCurrTime());
        Calendar calendar = Calendar.getInstance();
        submission.setCreateTime(calendar.getTime());
        //submission.setUserId(JwtUtil.getUserId(request.getHeader("token")));
        //submission.setUsername(JwtUtil.getUsername(request.getHeader("token")));
        submission.setIp(request.getRemoteAddr());
        submission.setUserId(1);
        submission.setUsername("root");
        submissionService.save(submission);
        judgeUtil.judge(submission,problemService.getById(submission.getProblemId()), "http://192.168.1.103:8080/judge", JudgeUtil.token);
        return Result.success(submission);
    }

}
