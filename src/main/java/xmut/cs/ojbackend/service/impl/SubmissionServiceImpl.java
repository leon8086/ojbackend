package xmut.cs.ojbackend.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.Submission;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.mapper.SubmissionMapper;
import xmut.cs.ojbackend.service.ProblemService;
import xmut.cs.ojbackend.service.SubmissionService;
import org.springframework.stereotype.Service;
import xmut.cs.ojbackend.service.UserService;
import xmut.cs.ojbackend.utils.JudgeUtil;

import java.util.Calendar;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class SubmissionServiceImpl extends ServiceImpl<SubmissionMapper, Submission> implements SubmissionService {

    @Autowired
    JudgeUtil judgeUtil;

    @Autowired
    ProblemService problemService;

    @Autowired
    UserService userService;

    @Value("${judge-server.url}")
    private String JUDGE_URL;


    @Override
    public Object submitCode(Submission submission, String ip, Integer userId) throws JsonProcessingException {
        Calendar calendar = Calendar.getInstance();
        submission.setCreateTime(calendar.getTime());
        User user = userService.getById(userId);
        submission.setIp(ip);
        submission.setUserId(userId);
        submission.setUsername(user.getUsername());
        save(submission);
        judgeUtil.judge(submission,problemService.getById(submission.getProblemId()), JUDGE_URL, JudgeUtil.token);
        return Result.success(submission);
    }
}
