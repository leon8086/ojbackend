package xmut.cs.ojbackend.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.util.UpdateEntity;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.stereotype.Service;
import xmut.cs.ojbackend.config.ExamCheckException;
import xmut.cs.ojbackend.entity.*;
import xmut.cs.ojbackend.entity.VO.*;
import xmut.cs.ojbackend.entity.entitymapper.VoExamBriefWrapper;
import xmut.cs.ojbackend.entity.entitymapper.VoProblemDetailWrapper;
import xmut.cs.ojbackend.entity.entitymapper.VoProblemTitleWrapper;
import xmut.cs.ojbackend.mapper.CourseMapper;
import xmut.cs.ojbackend.mapper.CourseUserMapper;
import xmut.cs.ojbackend.mapper.ProblemMapper;
import xmut.cs.ojbackend.mapper.exam.ExamMapper;
import xmut.cs.ojbackend.mapper.exam.ExamProfilesMapper;
import xmut.cs.ojbackend.mapper.exam.ExamSubmissionMapper;
import xmut.cs.ojbackend.service.CourseService;
import xmut.cs.ojbackend.service.ExamService;
import xmut.cs.ojbackend.utils.CommonUtil;
import xmut.cs.ojbackend.utils.JudgeUtil;

import java.io.Serializable;
import java.util.*;

import static xmut.cs.ojbackend.entity.table.CourseTableDef.COURSE;
import static xmut.cs.ojbackend.entity.table.CourseUserTableDef.COURSE_USER;
import static xmut.cs.ojbackend.entity.table.ExamProfileTableDef.EXAM_PROFILE;
import static xmut.cs.ojbackend.entity.table.ExamSubmissionTableDef.EXAM_SUBMISSION;
import static xmut.cs.ojbackend.entity.table.ExamTableDef.EXAM;
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
    CourseUserMapper courseUserMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    ExamProfilesMapper examProfilesMapper;

    @Autowired
    ExamMapper examMapper;

    @Autowired
    VoExamBriefWrapper voExamBriefWrapper;

    @Autowired
    CourseService courseService;

    @Autowired
    CommonUtil commonUtil;

    @Autowired
    JudgeUtil judgeUtil;

    @Autowired
    private HttpServletRequest httpServletRequest;

    public static int EXAM_VALID = 0;
    public static int EXAM_NOT_START = 1;
    public static int EXAM_ENDED = 2;
    public static int EXAM_NO_EXAM = 3;
    public static int EXAM_IP_NOT_ALLOWED = 3;

    public static Map<String, Object> examValid(){
        return Map.of( "valid", true, "message","", "status", EXAM_VALID);
    }

    public static Map<String, Object> examNotStart(){
        return Map.of( "valid", false, "message","考试尚未开始", "status", EXAM_NOT_START);
    }

    public static Map<String, Object> examEnded(){
        return Map.of( "valid", false, "message","考试已经结束", "status", EXAM_ENDED);
    }

    public static Map<String, Object> examNoExam(){
        return Map.of( "valid", false, "message","未找到相关考试", "status", EXAM_NO_EXAM);
    }

    public static Map<String, Object> examIPNotAllowed(){
        return Map.of( "valid", false, "message","ip地址不在允许范围之内", "status", EXAM_IP_NOT_ALLOWED);
    }

    public boolean ipNotAllowed(String ip, JSONArray ranges ){
        if(ranges.isEmpty()){  // 空 == 不限制
            return false;
        }
        for( int i=0; i<ranges.size(); ++i ){
            String subnet = ranges.getString(i);
            IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(subnet);
            //System.out.println(ip);
            //System.out.println(ipAddressMatcher.matches(ip));
            if( ipAddressMatcher.matches(ip)){
                return false;
            }
        }
        return true;
    }

    Map<String, Object> getExamStatus( Exam exam ){
        //System.out.println(exam);
        User user = commonUtil.getCurrentUser();
        if( exam.getIsEnded() ){
            return examEnded();
        }

        if( exam.getStartTime().after(Calendar.getInstance().getTime())){
            return examNotStart();
        }

        if( user.getAdminType() == User.ADMINTYPE_SUPERADMIN){
            return examValid();
        }

        if(ipNotAllowed(commonUtil.getIpAddr(httpServletRequest), exam.getAllowedIpRanges())){
            return examIPNotAllowed();
        }

        QueryWrapper wrapper = new QueryWrapper();
        if(Objects.equals(user.getAdminType(), User.ADMINTYPE_REGULAR)){
            // 检查该学生是否有该考试的资格
            wrapper.from(COURSE_USER);
            wrapper.where(COURSE_USER.COURSE_ID.eq(exam.getCourseId()));
            wrapper.where(COURSE_USER.USER_ID.eq(user.getId()));
            if( courseUserMapper.selectCountByQuery(wrapper) == 0 ){
                return examNoExam();
            }
        }
        else{
            wrapper.from(COURSE);
            wrapper.where(COURSE.OWNER_ID.eq(user.getId()));
            wrapper.where(COURSE.ID.eq(exam.getCourseId()));
            if( courseMapper.selectCountByQuery(wrapper) == 0 ){
                return examNoExam();
            }
        }
        return examValid();
    }

    // 管理员 的状态检查
    Map<String, Object> getAdminExamStatus( Exam exam ) {
        User user = commonUtil.getCurrentUser();
        if(Objects.equals(user.getAdminType(), User.ADMINTYPE_REGULAR)){
            throw new AccessDeniedException("没有权限");
        }
        if(Objects.equals(user.getAdminType(), User.ADMINTYPE_ADMIN)) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.from(COURSE);
            wrapper.where(COURSE.OWNER_ID.eq(user.getId()));
            wrapper.where(COURSE.ID.eq(exam.getCourseId()));
            if( courseMapper.selectCountByQuery(wrapper) == 0 ){
                return examNoExam();
            }
        }
        return examValid();
    }

    private QueryWrapper genListWrapper( String keyword, Integer courseId ){
        QueryWrapper wrapper = new QueryWrapper();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.where(EXAM.TITLE.like(keyword));
        }
        if( courseId != null && courseId != 0 ) {
            wrapper.where(EXAM.COURSE_ID.eq(courseId));
        }
        return wrapper;
    }

    private Integer getProblemCount( JSONArray problemConfig ){
        int count = 0;
        for( int i=0; i<problemConfig.size(); ++i ){
            JSONObject obj = problemConfig.getJSONObject(i);
            count += obj.getIntValue("quantity");
        }
        return count;
    }

    private void beginExam(User user, Exam exam, JSONArray problemSet ){
        ExamProfile profile = new ExamProfile();
        profile.setUserId(user.getId());
        profile.setExamId(exam.getId());
        profile.setProblemConfig(problemSet);
        profile.setScore(0);
        profile.setIsEnded(false);
        Map<String, Integer> info = new HashMap<String, Integer>();
        for( int i=0; i<problemSet.size(); ++i ){
            JSONObject obj = (JSONObject) problemSet.get(i);
            String id = ((Integer)obj.get("id")).toString();
            info.put( id, 0 );
        }
        //System.out.println(JSON.toJSONString(info));
        //System.out.println(JSON.parseObject(JSON.toJSONString(info)));
        Calendar calendar = Calendar.getInstance();
        profile.setLastUpdate(calendar.getTime());
        profile.setInfo(JSON.parseObject(JSON.toJSONString(info)));
        examProfilesMapper.insert(profile);
    }

    private JSONArray genProblemSet( Exam exam ){
        JSONArray pArray = exam.getProblemConfig();
        JSONArray problems = new JSONArray();
        for( int i=0; i<pArray.size(); ++i ){
            JSONObject job = pArray.getJSONObject(i);
            //System.out.println(job);
            int count = job.getIntValue("quantity");
            List<String> base = new ArrayList<String>(job.getJSONObject("problems").keySet());
            Collections.shuffle(base);  // 随机打乱
            for( int j=0; j<count; ++j){  // 取前n个
                problems.add(job.getJSONObject("problems").get(base.get(j)));
            }
        }
        return problems;
    }

    private JSONArray getExamProblemSet( User user, Exam exam ){
        // 这里应该不需要考虑结束，前方有判定
        ExamProfile profiles = getExamProfile( user.getId(), exam.getId() );
        if( profiles != null ){
            return profiles.getProblemConfig();
        }
        else {
            JSONArray problems = genProblemSet(exam);
            beginExam(user, exam, problems);
            return problems;
        }
    };

    public void setExamProfilesEnded( Exam exam, boolean isEnded ){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where( EXAM_PROFILE.EXAM_ID.eq(exam.getId()));
        wrapper.and( EXAM_PROFILE.IS_ENDED.eq(!isEnded));
        List<ExamProfile> profiles = examProfilesMapper.selectListByQuery(wrapper);
        for( ExamProfile profile : profiles){
            profile.setIsEnded(isEnded);
        }
        Db.updateEntitiesBatch(profiles);
    }

    @Override
    public Object adminUpdateExam(Exam exam) {
        if(exam.getId() == null) {
            // go insert
            exam.setCreateTime(Calendar.getInstance().getTime());
            exam.setLastUpdateTime(Calendar.getInstance().getTime());
            exam.setVisible(true);
            exam.setProblemCount(getProblemCount(exam.getProblemConfig()));
            //System.out.println(exam.getAllowedIpRanges());
            //System.out.println(exam);
            save(exam);
        }
        else {
            exam.setLastUpdateTime(Calendar.getInstance().getTime());
            updateById(exam);
            //System.out.println(exam);
            //System.out.println(exam.getAllowedIpRanges());
        }
        return exam;
    }

    @Override
    public Object adminListPage(Integer page, Integer limit, String keyword, Integer courseId ) {
        QueryWrapper wrapper = genListWrapper( keyword, courseId);
        wrapper.orderBy(EXAM.START_TIME.desc());
        Page<VOExamDetail> pg = commonUtil.genPage(page, limit);
        Page<VOExamDetail> ret = examMapper.paginateWithRelationsAs(pg, wrapper, VOExamDetail.class);
        for( VOExamDetail v : ret.getRecords()){
            if( v.getEndTime().before(Calendar.getInstance().getTime()) ){
                v.setIsEnded(true);
            }
        }
        return ret;
    }

    @Override
    public Object adminListByOwnerPage(Integer page, Integer limit, String keyword, Integer courseId ) {
        User user = commonUtil.getCurrentUser();
        QueryWrapper wrapper;
        if( courseId != 0 ){
            wrapper = genListWrapper(keyword, courseId);
        }
        else{
            wrapper = new QueryWrapper();
            wrapper.from(EXAM.as("e"), COURSE.as("c"));
            wrapper.where(COURSE.OWNER_ID.eq(user.getId()));
            wrapper.and(EXAM.COURSE_ID.eq(COURSE.ID));
        }
        wrapper.orderBy(EXAM.START_TIME.desc());
        Page<VOExamDetail> pg = commonUtil.genPage(page, limit);
        pg = examMapper.paginateWithRelationsAs(pg, wrapper, VOExamDetail.class);
        for( VOExamDetail v : pg.getRecords() ){
            if( v.getEndTime().before(Calendar.getInstance().getTime())){
                v.setIsEnded( true );
            }
        }
        return pg;
    }

    @Override
    //TODO 这里需要处理参数
    public Object adminCloseExam(Integer id, Boolean isEnded) {
        Exam exam = UpdateEntity.of(Exam.class, id);
        exam.setIsEnded(true);
        mapper.update(exam);
        setExamProfilesEnded(exam, true);
        return exam;
    }

    @Override
    public Object adminGetExamDetail(Integer id) {
        VOExamDetail exam = examMapper.selectOneWithRelationsByIdAs(id, VOExamDetail.class);
        Set<String> ids = new HashSet<String>();
        return exam;
    }

    @Override
    public Object adminGetExamSubmission( Integer examId, Integer userId ){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where( EXAM_SUBMISSION.EXAM_ID.eq(examId));
        wrapper.and( EXAM_SUBMISSION.USER_ID.eq(userId));
        wrapper.orderBy(EXAM_SUBMISSION.CREATE_TIME.desc());
        return examSubmissionMapper.selectListWithRelationsByQueryAs(wrapper, VOSubmissionDetail.class);
    }

    @Override
    public Object adminRestart(Integer id, Date endTime) {
        Exam exam = UpdateEntity.of(Exam.class, id);
        exam.setEndTime(endTime);
        exam.setIsEnded(false);
        mapper.update(exam);
        setExamProfilesEnded(exam, false);
        return "更新成功";
    }

    @Override
    public Object adminGetUserExamProfile(Integer examId, Integer userId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where( EXAM_PROFILE.USER_ID.eq(userId));
        wrapper.where( EXAM_PROFILE.EXAM_ID.eq(examId));
        return examProfilesMapper.selectOneByQuery(wrapper);
    }

    public void restartImp( Integer examId, Integer userId ) throws ExamCheckException{
        Exam exam = getById(examId);
        Map<String, Object> examStatus = getExamStatus(exam);
        if( !(Boolean) examStatus.get("valid") ){
            throw new ExamCheckException(examStatus);
        }
        QueryWrapper wrapper = QueryWrapper.create()
                .where(EXAM_PROFILE.USER_ID.eq(userId))
                .and(EXAM_PROFILE.EXAM_ID.eq(examId));
        ExamProfile profile = examProfilesMapper.selectOneByQuery(wrapper);
        if( profile.getIsEnded() ){
            profile.setIsEnded(false);
            examProfilesMapper.update(profile);
        }
    }

    @Override
    public Object adminRestartUser(Integer examId, Integer userId) throws ExamCheckException {
        restartImp(examId, userId);
        return "操作成功";
    }

    @Override
    public Object restart(Integer examId) throws ExamCheckException {
        User user = commonUtil.getCurrentUser();
        restartImp(examId, user.getId());
        return "操作成功";
    }

    @Override
    public Object adminRecount(Integer examId) {
        QueryWrapper wrapper = QueryWrapper.create()
                .where(EXAM_PROFILE.EXAM_ID.eq(examId));
        List<ExamProfile> profiles = examProfilesMapper.selectListByQuery(wrapper);
        for( ExamProfile p : profiles ){
            JSONObject obj = p.getInfo();
            calcScore(p, obj);
            examProfilesMapper.update(p);
        }
        return "调用成功";
    }

    private void calcScore(ExamProfile p, JSONObject obj) {
        Integer totalScore = 0;
        for( Map.Entry<?, ?> map : obj.entrySet()){
            if( map.getKey().toString().contains("count")){
                continue;
            }
            totalScore += (Integer) map.getValue();
        }
        p.setScore(totalScore);
    }

    @Override
    public ExamProfile getExamProfile(Integer userId, Integer examId){
        //TODO 这里可能要考虑是否结束
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.and( EXAM_PROFILE.USER_ID.eq(userId));
        wrapper.and(EXAM_PROFILE.EXAM_ID.eq(examId));
        return examProfilesMapper.selectOneByQuery(wrapper);
    }

    @Override
    public Object getExamRank(Integer examId) {
        //TODO 这里可能要考虑是否结束
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where(EXAM_PROFILE.EXAM_ID.eq(examId));
        wrapper.orderBy(EXAM_PROFILE.SCORE.desc(), EXAM_PROFILE.LAST_UPDATE.asc());
        return examProfilesMapper.selectListWithRelationsByQueryAs( wrapper, VOExamProfileRank.class);
    }

    @Override
    public Object quitExam(Integer examId) throws ExamCheckException {
        User user = commonUtil.getCurrentUser();
        ExamProfile profile = getExamProfile(user.getId(), examId);
        if( profile == null){
            throw new ExamCheckException(examNoExam());
        }
        profile.setIsEnded(true);
        profile.setLastUpdate(Calendar.getInstance().getTime());
        examProfilesMapper.update(profile);
        return profile;
    }

    @Override
    public Object listValidExam() {
        //TODO 这里可能要考虑是否结束
        User user = commonUtil.getCurrentUser();
        List<Course> courses = courseService.getUsersCourse(user);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where( EXAM.VISIBLE.eq(true));
        wrapper.and( EXAM.IS_ENDED.eq(false));
        wrapper.and( EXAM.END_TIME.ge(Calendar.getInstance().getTime()));
        wrapper.and( w ->{
            for( Course c : courses ){
                w.or( EXAM.COURSE_ID.eq(c.getId()));
            }
        });
        List<VOExamBrief> tmp = examMapper.selectListWithRelationsByQueryAs(wrapper, VOExamBrief.class);
        List<VOExamBrief> ret = new ArrayList<>();
        for( VOExamBrief e : tmp ){
            wrapper.clear();
            wrapper.where(EXAM_PROFILE.EXAM_ID.eq(e.getId()));
            wrapper.and( EXAM_PROFILE.USER_ID.eq(user.getId()));
            ExamProfile profile = examProfilesMapper.selectOneByQuery(wrapper);
            if( profile != null && profile.getIsEnded() ){
                continue;
            }
            ret.add(e);
        }
        return ret;
    }

    @Override
    public Object listPageExam( Integer page, Integer limit, Integer courseId, String keyword ) {
        User user = commonUtil.getCurrentUser();
        QueryWrapper wrapper;
        if( courseId != 0 ){
            wrapper = genListWrapper(keyword, courseId);
        }
        else{
            List<Course> courses = courseService.getUsersCourse(user);
            wrapper = new QueryWrapper();
            wrapper.where( EXAM.VISIBLE.eq(true));
            wrapper.and( w ->{
                for( Course c : courses ){
                    w.or( EXAM.COURSE_ID.eq(c.getId()));
                }
            });
        }
        Page<VOExamBrief> pg = commonUtil.genPage(page, limit);
        wrapper.orderBy( EXAM.START_TIME.desc());
        pg = examMapper.paginateWithRelationsAs( pg, wrapper, VOExamBrief.class);
        for( VOExamBrief b : pg.getRecords() ){
            if( b.getEndTime().before(Calendar.getInstance().getTime())){
                b.setIsEnded(true);
            }
        }
        return pg;
    }

    @Override
    public Object getProblems(Exam exam) throws ExamCheckException {
        exam = getById(exam.getId());
        Map<String, Object> examStatus = getExamStatus(exam);
        if( !(Boolean) examStatus.get("valid") ){
            throw new ExamCheckException(examStatus);
        }
        LoginUser loginUser = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        JSONArray problems = getExamProblemSet( user, exam );
        QueryWrapper wrapper = new QueryWrapper();
        for (Object problem : problems) {
            JSONObject obj = (JSONObject) problem;
            Integer id = (Integer) obj.get("id");
            wrapper.or(PROBLEM.ID.eq(id));
        }
        List<VOProblemDetail> problemDetailList = problemMapper.selectListWithRelationsByQueryAs(wrapper, VOProblemDetail.class);
        Map<Integer, VOProblemDetail> map = new HashMap<Integer, VOProblemDetail>();
        problemDetailList.forEach( item ->{map.put(item.getId(), item);} );
        List<VOProblemDetail> ret = new ArrayList<VOProblemDetail>();
        //for (Integer problem : problems) {
        for (Object problem : problems) {
            JSONObject obj = (JSONObject) problem;
            Integer id = (Integer) obj.get("id");
            VOProblemDetail prob = map.get(id);
            commonUtil.toAnswerTemplate(prob);
            ret.add(prob);
        }
        return ret;
    }

    @Override
    public Object getInfo(Serializable id) {
        return examMapper.selectOneWithRelationsByIdAs(id, VOExamBrief.class);
    }

    @Override
    public Object submitCode(ExamSubmission examSubmission, String ip, User user) throws JsonProcessingException, ExamCheckException {
        ExamProfile profile = getExamProfile(user.getId(), examSubmission.getExamId());
        Exam exam = getById(examSubmission.getExamId());
        Map<String, Object> examStatus = getExamStatus(exam);
        if( !(Boolean) examStatus.get("valid") ){
            throw new ExamCheckException(examStatus);
        }
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
        judgeUtil.judgeExam(examSubmission, problem);

        JSONObject obj = profile.getInfo();
        Integer oldScore = obj.getInteger(examSubmission.getProblemId().toString());
        Integer submitCount = obj.getIntValue("count_"+examSubmission.getProblemId().toString());
        if( submitCount == null){
            submitCount = 0;
        }
        submitCount ++;
        obj.put("count_"+examSubmission.getProblemId().toString(), submitCount);
        Integer newScore = examSubmission.getStatisticInfo().getInteger("score");
        if( oldScore < newScore ){
            obj.put(examSubmission.getProblemId().toString(), newScore);
            calcScore(profile, obj);
            profile.setLastUpdate(calendar.getTime());
        }
        examProfilesMapper.update(profile);
        return examSubmission;
    }

    @Override
    public Object getProblemSubmission(String examId) {
        User user = commonUtil.getCurrentUser();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.and(EXAM_SUBMISSION.EXAM_ID.eq(examId));
        wrapper.and(EXAM_SUBMISSION.USER_ID.eq(user.getId()));
        wrapper.orderBy(EXAM_SUBMISSION.CREATE_TIME.desc());
        return examSubmissionMapper.selectListByQueryAs(wrapper, VOSubmissionDetail.class);
    }

    @Override
    public Object getSubmissionById(String id) {
        return examSubmissionMapper.selectOneById(id);
    }
}
