package xmut.cs.ojbackend.service.impl.exam;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xmut.cs.ojbackend.entity.*;
import xmut.cs.ojbackend.entity.VO.*;
import xmut.cs.ojbackend.entity.entitymapper.VoProblemDetailWrapper;
import xmut.cs.ojbackend.entity.entitymapper.VoProblemTitleWrapper;
import xmut.cs.ojbackend.mapper.ProblemMapper;
import xmut.cs.ojbackend.mapper.exam.ExamMapper;
import xmut.cs.ojbackend.mapper.exam.ExamProfilesMapper;
import xmut.cs.ojbackend.mapper.exam.ExamSubmissionMapper;
import xmut.cs.ojbackend.service.exam.ExamService;
import xmut.cs.ojbackend.utils.CommonUtil;
import xmut.cs.ojbackend.utils.JudgeUtil;

import java.io.Serializable;
import java.util.*;

import static xmut.cs.ojbackend.entity.table.ExamProfileTableDef.EXAM_PROFILE;
import static xmut.cs.ojbackend.entity.table.ExamSubmissionTableDef.EXAM_SUBMISSION;
import static xmut.cs.ojbackend.entity.table.ProblemTableDef.PROBLEM;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-26
 */
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    VoProblemDetailWrapper voProblemDetailWrapper;

    @Autowired
    VoProblemTitleWrapper voProblemTitleWrapper;

    @Autowired
    ExamSubmissionMapper examSubmissionMapper;

    @Autowired
    ExamProfilesMapper examProfilesMapper;

    @Autowired
    ExamMapper examMapper;

    @Autowired
    CommonUtil commonUtil;

    @Autowired
    JudgeUtil judgeUtil;

    @Value("${judge-server.url}")
    private String JUDGE_URL;

    @Override
    public Object updateExam(Exam exam) {
        if(exam.getId() == null) {
            // go insert
            save(exam);
        }
        else {
            updateById(exam);
        }
        return exam;
    }
    @Override
    public ExamProfile getExamProfile(Integer userId, Integer examId){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.and( EXAM_PROFILE.USER_ID.eq(userId));
        wrapper.and(EXAM_PROFILE.EXAM_ID.eq(examId));
        return examProfilesMapper.selectOneByQuery(wrapper);
    }
    public void beginExam(User user, Exam exam, List<Integer> problemSet ){
        ExamProfile profile = new ExamProfile();
        profile.setUserId(user.getId());
        profile.setExamId(exam.getId());
        profile.setProblemConfig(JSONArray.from(problemSet));
        profile.setScore(0);
        Map<String, Integer> info = new HashMap<String, Integer>();
        for( Integer i : problemSet ){
            info.put(i.toString(), 0 );
        }
        //System.out.println(JSON.toJSONString(info));
        //System.out.println(JSON.parseObject(JSON.toJSONString(info)));
        profile.setInfo(JSON.parseObject(JSON.toJSONString(info)));
        examProfilesMapper.insert(profile);
    }
    public List<Integer> getExamProblemSet( User user, Exam exam ){
        ExamProfile profiles = getExamProfile( user.getId(), exam.getId() );
        if( profiles != null ){
            return profiles.getProblemConfig().toList(Integer.class);
        }
        JSONArray pArray = exam.getProblemConfig();
        List<Integer> problems = new ArrayList<Integer>();
        for( int i=0; i<pArray.size(); ++i ){
            JSONObject job = pArray.getJSONObject(i);
            //System.out.println(job);
            int count = job.getIntValue("quantity");
            List<Integer> base = job.getList("problems", Integer.class);
            Collections.shuffle(base);  // 随机打乱
            for( int j=0; j<count; ++j){  // 取前n个
                problems.add(base.get(j));
            }
        }
        beginExam(user, exam, problems);
        return problems;
    };
    @Override
    public Object getProblems(Exam exam) {
        exam = getById(exam.getId());
        LoginUser loginUser = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        List<Integer> problems = getExamProblemSet( user, exam );
        QueryWrapper wrapper = new QueryWrapper();
        for( Integer key : problems ){
            wrapper.or(PROBLEM.ID.eq(key));
        }
        List<VOProblemDetail> problemDetailList = problemMapper.selectListWithRelationsByQueryAs(wrapper, VOProblemDetail.class);
        Map<Integer, VOProblemDetail> map = new HashMap<Integer, VOProblemDetail>();
        problemDetailList.forEach( item ->{map.put(item.getId(), item);} );
        List<VOProblemDetail> ret = new ArrayList<VOProblemDetail>();
        for (Integer problem : problems) {
            VOProblemDetail prob = map.get(problem);
            commonUtil.replaceTemplate(prob);
            ret.add(prob);
        }
        return ret;
    }

    @Override
    public Object getExamDetail(Integer id) {
        VOExamDetail exam = examMapper.selectOneWithRelationsByIdAs(id, VOExamDetail.class);
        Set<String> ids = new HashSet<String>();
        JSONArray pArray = exam.getProblemConfig();
        for( int i=0; i<pArray.size(); ++i ){
            JSONObject job = pArray.getJSONObject(i);
            int count = job.getIntValue("quantity");
            List<String> base = job.getList("problems", String.class);
            QueryWrapper wrapper = new QueryWrapper();
            for( String key : base ){
                wrapper.or( PROBLEM.ID.eq(key));
            }
            List<VOProblemTitle> pList = problemMapper.selectListWithRelationsByQueryAs( wrapper, VOProblemTitle.class);
            job.put("problems", pList);
        }
        return exam;
    }

    @Override
    public Object getInfo(Serializable id) {
        return examMapper.selectOneWithRelationsByIdAs(id, VOExamBrief.class);
    }

    @Override
    public Object submitCode(ExamSubmission examSubmission, String ip, User user) throws JsonProcessingException {
        Calendar calendar = Calendar.getInstance();
        examSubmission.setCreateTime(calendar.getTime());
        examSubmission.setIp(ip);
        examSubmission.setUserId(user.getId());
        examSubmission.setUsername(user.getUsername());
        //System.out.println(submission);
        //save(examSubmission);
        examSubmissionMapper.insert(examSubmission);
        //System.out.println(submission);
        Problem problem = problemMapper.selectOneById(examSubmission.getProblemId());
        judgeUtil.judgeExam(examSubmission, problem, JUDGE_URL, JudgeUtil.token);

        ExamProfile profile = getExamProfile(user.getId(), examSubmission.getExamId());
        JSONObject obj = profile.getInfo();
        Integer oldScore = obj.getInteger(examSubmission.getProblemId().toString());
        Integer newScore = examSubmission.getStatisticInfo().getInteger("score");
        if( oldScore < newScore ){
            obj.put(examSubmission.getProblemId().toString(), newScore);
            Integer totalScore = 0;
            for( Object map : obj.entrySet()){
                totalScore += (Integer)((Map.Entry)map).getValue();
            }
            //System.out.println(totalScore);
            profile.setScore(totalScore);
            examProfilesMapper.update(profile);
        }
        return examSubmission;
    }

    @Override
    public Object getProblemSubmission(String examId, Integer problemId, Integer userId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.and(EXAM_SUBMISSION.EXAM_ID.eq(examId));
        wrapper.and(EXAM_SUBMISSION.PROBLEM_ID.eq(problemId));
        wrapper.and(EXAM_SUBMISSION.USER_ID.eq(userId));
        wrapper.orderBy(EXAM_SUBMISSION.CREATE_TIME.desc());
        return examSubmissionMapper.selectListByQueryAs(wrapper, VOSubmissionDetail.class);
    }

    @Override
    public Object getSubmissionById(String id) {
        return examSubmissionMapper.selectOneById(id);
    }
}
