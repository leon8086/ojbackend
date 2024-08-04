package xmut.cs.ojbackend.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.Problem;
import xmut.cs.ojbackend.entity.Submission;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.entity.VO.VOSubmissionDetail;
import xmut.cs.ojbackend.entity.VO.VOSubmissionExamList;
import xmut.cs.ojbackend.entity.VO.VOSubmissionList;
import xmut.cs.ojbackend.entity.VO.VOSubmissionResult;
import xmut.cs.ojbackend.mapper.SubmissionMapper;
import xmut.cs.ojbackend.service.ProblemService;
import xmut.cs.ojbackend.service.SubmissionService;
import xmut.cs.ojbackend.service.UserService;
import xmut.cs.ojbackend.utils.JudgeUtil;

import java.util.Calendar;

import static xmut.cs.ojbackend.entity.table.SubmissionTableDef.SUBMISSION;

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
    SubmissionMapper submissionMapper;

    @Autowired
    UserService userService;

    @Value("${judge-server.url}")
    private String JUDGE_URL;


    @Override
    public Object submitCode(Submission submission, String ip, User user) throws JsonProcessingException {
        Calendar calendar = Calendar.getInstance();
        submission.setCreateTime(calendar.getTime());
        submission.setIp(ip);
        submission.setUserId(user.getId());
        submission.setUsername(user.getUsername());
        //System.out.println(submission);
        save(submission);
        //System.out.println(submission);
        Problem problem = problemService.getById(submission.getProblemId());
        judgeUtil.judgeNormal(submission,problem, JUDGE_URL, JudgeUtil.token);
        //System.out.println(submission);
        return Result.success(submission);
    }

    @Override
    public Object listPage(Integer page, Integer limit, Integer result, String username) {
        QueryWrapper wrapper = new QueryWrapper();
        if( result != null ) {
            wrapper.where(SUBMISSION.RESULT.eq(result));
        }
        if( username != null ) {
            wrapper.where(SUBMISSION.USERNAME.eq(username));
        }
        wrapper.orderBy(SUBMISSION.CREATE_TIME.desc());
        return submissionMapper.paginateWithRelationsAs(page, limit, wrapper, VOSubmissionList.class );
    }

    @Override
    public Object getSubmitResult(String id) {
        //QueryWrapper wrapper = new QueryWrapper();
        //wrapper.select(SUBMISSION.ID, SUBMISSION.RESULT, SUBMISSION.USER_ID, SUBMISSION.PROBLEM_ID, SUBMISSION.INFO);
        //wrapper.where(SUBMISSION.ID.eq(id));
        //VOSubmissionResult res = submissionMapper.selectOneByQueryAs(wrapper, VOSubmissionResult.class);
        VOSubmissionResult res = submissionMapper.selectOneWithRelationsByIdAs(id,VOSubmissionResult.class);
        return res;
    }

    @Override
    public Object getInfo(String id) {
        VOSubmissionDetail res = this.mapper.selectOneWithRelationsByIdAs(id, VOSubmissionDetail.class);
        return Result.success(res);
    }

    @Override
    public Object listExam() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.limit(10);
        wrapper.orderBy(SUBMISSION.CREATE_TIME.desc());
        return Result.success(submissionMapper.selectListByQueryAs( wrapper, VOSubmissionExamList.class ));
    }
}
