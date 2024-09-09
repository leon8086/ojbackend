package xmut.cs.ojbackend.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import xmut.cs.ojbackend.entity.*;
import xmut.cs.ojbackend.mapper.ScoreMapper;
import xmut.cs.ojbackend.mapper.SubmissionMapper;
import xmut.cs.ojbackend.mapper.exam.ExamSubmissionMapper;
import xmut.cs.ojbackend.service.JudgeServerService;
import xmut.cs.ojbackend.service.OptionsSysoptionsService;
import xmut.cs.ojbackend.service.ProblemService;
import xmut.cs.ojbackend.service.UserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import static xmut.cs.ojbackend.entity.table.ScoreTableDef.SCORE;


@Component
public class JudgeUtil {

    @Autowired
    private OptionsSysoptionsService optionsSysoptionsService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubmissionMapper submissionMapper;

    @Autowired
    private ExamSubmissionMapper examSubmissionMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    JudgeServerService judgeServerService;

    @Autowired
    private CommonUtil commonUtil;

//    @Value("${judge-server.url}")
//    private String JudgeServerUrl;

    @Value("${judge-server.token}")
    public String judgeToken;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    public static final int COMPILE_ERROR = -2;

    public static final int WRONG_ANSWER = -1;
    public static final int ACCEPTED = 0;
    public static final int CPU_TIME_LIMIT_EXCEEDED = 1;
    public static final int REAL_TIME_LIMIT_EXCEEDED = 2;
    public static final int MEMORY_LIMIT_EXCEEDED = 3;
    public static final int RUNTIME_ERROR = 4;
    public static final int SYSTEM_ERROR = 5;
    public static final int PENDING = 6;
    public static final int JUDGING = 7;
    public static final int PARTIALLY_ACCEPTED = 8;

    private static String sha256(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getLanguageConfig(String language) {
        JSONArray data;
        if(Boolean.TRUE.equals(redisTemplate.hasKey("languages"))){
            data = (JSONArray) redisTemplate.opsForValue().get("languages");
        }
        else {
            data = (JSONArray) optionsSysoptionsService.getValue("languages");
            redisTemplate.opsForValue().set("languages", data );
        }
        JSONObject languageConfig = null;
        for (Object item : data) {
            languageConfig = (JSONObject) item;
            if (languageConfig.getString("name").equals(language)) {
                break;
            }
        }
        JSONObject config = languageConfig.getJSONObject("config");
        return config;
    }

    public JSONObject getJudgeResult( Problem problem, String code, String language, String judgeUrl, String token ) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        if(!problem.getTemplate().isEmpty() && !problem.getTemplate().getJSONObject(language).isEmpty()) {
            code = commonUtil.applyTemplate(problem.getTemplate().getJSONObject(language), code);
        }
        else{
            code = code;
        }
        //创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Judge-Server-Token", sha256(token));

        JSONObject taskInfo = new JSONObject();
        taskInfo.put("language_config", getLanguageConfig(language));
        taskInfo.put("src", code);
        taskInfo.put("max_cpu_time", problem.getTimeLimit());
        taskInfo.put("max_memory", problem.getMemoryLimit() * 1024 * 1024);
        taskInfo.put("test_case_id", problem.getTestCaseId());
        taskInfo.put("spj_version", null);
        taskInfo.put("spj_config", null);
        taskInfo.put("spj_compile_config", null);
        taskInfo.put("spj_src", null);
        //taskInfo.put("output", false);
        taskInfo.put("output", true);
        //创建请求体
        HttpEntity<String> entity = new HttpEntity<>(taskInfo.toString(), headers);
        //发送请求
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(judgeUrl, entity, String.class);
        String data = responseEntity.getBody();
        return JSON.parseObject(data);
    }

    public void judgeExam( ExamSubmission examSubmission, Problem problem ) throws JsonProcessingException{
        //向判题服务器发送post请求
        judge(examSubmission, problem);
        examSubmissionMapper.update(examSubmission);
        // 更新exam的状态
    }

    public void judgeNormal( Submission submission, Problem problem ) throws JsonProcessingException {
        judge(submission, problem);
        submissionMapper.update(submission);
        updateProblemStatus(submission, problem);
        updateUserprofile(submission, problem);
    }

    @Async("AsyncJudgeConfig")
    public void judge( Submission submission, Problem problem ) throws JsonProcessingException{

        String judgeUrl = judgeServerService.selectServer();
//        System.out.println(judgeUrl);
        JSONObject result = getJudgeResult(problem, submission.getCode(), submission.getLanguage(), judgeUrl, judgeToken);
        //System.out.println(result);
        if (result.get("err") != null) {
            JSONObject staticInfo = new JSONObject();
            staticInfo.put("err_info", result.get("data"));
            staticInfo.put("score", 0);
            submission.setResult(COMPILE_ERROR);
            submission.setStatisticInfo(staticInfo);
        } else {
            submission.setInfo(result);
            JSONArray testData = result.getObject("data", JSONArray.class);
            int count = 0;
            int total = 0;
            for (Object testDatum : testData) {
                total ++;
                JSONObject item = (JSONObject) testDatum;
                if (item.getInteger("result") != ACCEPTED) {
                    count++;
                }
            }
            computeStatisticInfo(submission, testData, problem);
            if (count == 0) {
                submission.setResult(ACCEPTED);
            } else if( count != total ) {
                submission.setResult(PARTIALLY_ACCEPTED);
            } else {
                submission.setResult(WRONG_ANSWER);
            }
        }
    }

    private void computeStatisticInfo(Submission submission, JSONArray testData, Problem problem) {
        // 用时和内存占用保存为多个测试点中最长的那个
        int timeCost = testData.stream().mapToInt(o -> ((JSONObject) o).getIntValue("cpu_time")).max().orElse(0);
        int memoryCost = testData.stream().mapToInt(o -> ((JSONObject) o).getIntValue("memory")).max().orElse(0);
        JSONObject staticInfo = new JSONObject();
        staticInfo.put("time_cost", timeCost);
        staticInfo.put("memory_cost", memoryCost);

        // sum up the score in OI mode
        int score = 0;
        for (int i = 0; i < testData.size(); i++) {
            JSONObject testCase = testData.getJSONObject(i);
            if (testCase.getIntValue("result") == ACCEPTED) {
                int testCaseScore = problem.getTestCaseScore().getJSONObject(i).getIntValue("score");
                testCase.put("score", testCaseScore);
                score += testCaseScore;
            } else {
                testCase.put("score", 0);
            }
            testData.set(i, testCase);
        }
        staticInfo.put("score", score);
        submission.setStatisticInfo(staticInfo);
    }

    private void updateProblemStatus(Submission submission, Problem problem) {
        int result = submission.getResult();
        problem.setSubmissionNumber(problem.getSubmissionNumber() + 1);
        if (submission.getResult() == ACCEPTED) {
            problem.setAcceptedNumber(problem.getAcceptedNumber() + 1);
        }
        JSONObject problemInfo = problem.getStatisticInfo();
        problemInfo.put(String.valueOf(result), problemInfo.getIntValue(String.valueOf(result)) + 1);
        problem.setStatisticInfo(problemInfo);
        problemService.updateById(problem);
    }

    private void updateScore( User user, Integer major ){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where( SCORE.USER_ID.eq( user.getId()) );
        wrapper.and( SCORE.MAJOR_TAG.eq(major));
        Score score = scoreMapper.selectOneByQuery(wrapper);
        if( score == null ){
            score = new Score();
            score.setUserId(user.getId());
            score.setMajorTag(major);
            score.setScore(1);
            scoreMapper.insert(score);
        }
        else{
            score.setScore(score.getScore()+1);
            scoreMapper.update(score);
        }
    }

    private void updateUserprofile(Submission submission, Problem problem) {
        // update_userprofile
        //UserProfile userProfile = userProfileService.getByUserId(submission.getUserId());
        User user = userService.getById(submission.getUserId());
        //System.out.println(user.getUsername());
        user.setSubmissionNumber(user.getSubmissionNumber() + 1);
        JSONObject oiProblemsStatus = user.getProblemsStatus();
        //System.out.println(user.getUsername());
        int score = submission.getStatisticInfo().getIntValue("score");
        if (!oiProblemsStatus.containsKey(String.valueOf(problem.getId()))) {
            oiProblemsStatus.put(String.valueOf(problem.getId()), new JSONObject());
            oiProblemsStatus.getJSONObject(String.valueOf(problem.getId())).put("status", submission.getResult());
            oiProblemsStatus.getJSONObject(String.valueOf(problem.getId())).put("displayId", problem.getDisplayId());
            oiProblemsStatus.getJSONObject(String.valueOf(problem.getId())).put("score", score);
            if (submission.getResult() == ACCEPTED) {
                user.setAcceptedNumber(user.getAcceptedNumber() + 1);
                user.setLastAccept(submission.getCreateTime());
                updateScore(user, problem.getMajorTagId());
            }
        } else {
            if (oiProblemsStatus.getJSONObject(String.valueOf(problem.getId())).getIntValue("status") != ACCEPTED) {
                if(oiProblemsStatus.getJSONObject(String.valueOf(problem.getId())).getIntValue("score") < score ){
                    oiProblemsStatus.getJSONObject(String.valueOf(problem.getId())).put("score", score);
                    oiProblemsStatus.getJSONObject(String.valueOf(problem.getId())).put("status", submission.getResult());
                }
                if (submission.getResult() == ACCEPTED) {
                    user.setAcceptedNumber(user.getAcceptedNumber() + 1);
                    user.setLastAccept(submission.getCreateTime());
                    updateScore(user, problem.getMajorTagId());
                }
            }
        }
        user.setProblemsStatus(oiProblemsStatus);
        userService.updateById(user);

        String key = "user-id:"+user.getId().toString();
        //需要更新redis数据。
        redisTemplate.opsForValue().set(key, user, 7, TimeUnit.DAYS);
    }
}
