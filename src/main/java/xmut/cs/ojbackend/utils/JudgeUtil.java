package xmut.cs.ojbackend.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import xmut.cs.ojbackend.entity.*;
import xmut.cs.ojbackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Component

public class JudgeUtil {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private OptionsSysoptionsService optionsSysoptionsService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private ContestService contestService;


    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private OiContestRankService oiContestRankService;

    public static final int COMPILE_ERROR = -2;

    public static String token = "CHANGE_THIS";
    private String serverBaseUrl;
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
        //if (redisUtil.hasKey("languages")) {
        //    data = (JSONArray) redisUtil.get("languages");
        //} else {
            data = (JSONArray) optionsSysoptionsService.getValue("languages");
            //redisUtil.set("languages", data, 60 * 60);
        //}
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

    @Async
    public void judge( Submission submission, Problem problem, String judgeUrl, String token ) throws JsonProcessingException{
        //向判题服务器发送post请求
        RestTemplate restTemplate = new RestTemplate();

        //创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Judge-Server-Token", sha256(token));

        JSONObject taskInfo = new JSONObject();
        taskInfo.put("language_config", getLanguageConfig(submission.getLanguage()));
        taskInfo.put("src", submission.getCode());
        taskInfo.put("max_cpu_time", problem.getTimeLimit());
        taskInfo.put("max_memory", problem.getMemoryLimit() * 1024 * 1024);
        taskInfo.put("test_case_id", problem.getTestCaseId());
        taskInfo.put("spj_version", problem.getSpjVersion());
        taskInfo.put("spj_config", null);
        taskInfo.put("spj_compile_config", null);
        taskInfo.put("spj_src", problem.getSpjCode());
        taskInfo.put("output", true);
        //创建请求体
        HttpEntity<String> entity = new HttpEntity<>(taskInfo.toString(), headers);
        //发送请求
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(judgeUrl, entity, String.class);
        String data = responseEntity.getBody();
        JSONObject result = JSON.parseObject(data);
        System.out.println(result);
        if (result.get("err") != null) {
            JSONObject staticInfo = new JSONObject();
            staticInfo.put("err_info", result.get("data"));
            staticInfo.put("score", 0);
            submission.setResult(COMPILE_ERROR);
            submission.setStatisticInfo(staticInfo);
        } else {
            submission.setInfo(result);
            JSONArray testData = result.getObject("data", JSONArray.class);
            computeStatisticInfo(submission, testData, problem);
            if (testData.isEmpty()) {
                submission.setResult(ACCEPTED);
            } else {
                submission.setResult(PARTIALLY_ACCEPTED);
            }
        }
        submissionService.saveOrUpdate(submission);
        update_problem_status(submission, problem);
        if (submission.getContestId() != null) {
            Contest contest = contestService.getById(submission.getContestId());
            update_contest_rank(submission, problem, contest);
        } else {
            update_userprofile(submission, problem);
        }
    }
    @Async
    public void judge() throws JsonProcessingException {
        // 处理判题任务的逻辑，包括编译、运行等步骤
        // 通过异步队列或者消息队列发送判题任务
        // 可以在这里调用判题服务的接口或者方法
        if (redisUtil.llen("task_queue") == 0) {
            return;
        }
        JSONObject task = (JSONObject) redisUtil.rPop("task_queue");
        Submission submission = (Submission) task.get("submission");
        Problem problem = (Problem) task.get("problem");

        String judgeUrl = "http://192.168.1.103:8080/judge";
        judge(submission, problem, judgeUrl, token);
    }

    private void computeStatisticInfo(Submission submission, JSONArray testData, Problem problem) {
        // 用时和内存占用保存为多个测试点中最长的那个
        int timeCost = testData.stream().mapToInt(o -> ((JSONObject) o).getIntValue("cpu_time")).max().orElse(0);
        int memoryCost = testData.stream().mapToInt(o -> ((JSONObject) o).getIntValue("memory")).max().orElse(0);
        JSONObject staticInfo = new JSONObject();
        staticInfo.put("time_cost", timeCost);
        staticInfo.put("memory_cost", memoryCost);

        // sum up the score in OI mode
        if (problem.getRuleType().equals("OI")) {
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
        }
        submission.setStatisticInfo(staticInfo);
    }

    private void update_problem_status(Submission submission, Problem problem) {
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

    private void update_userprofile(Submission submission, Problem problem) {
        // update_userprofile
        UserProfile userProfile = userProfileService.getByUserId(submission.getUserId());
        userProfile.setSubmissionNumber(userProfile.getSubmissionNumber() + 1);
        JSONObject oiProblemsStatus = userProfile.getOiProblemsStatus();
        int score = submission.getStatisticInfo().getIntValue("score");
        if (!oiProblemsStatus.containsKey(String.valueOf(problem.getId()))) {
            userProfile.addScore(score);
            oiProblemsStatus.put(String.valueOf(problem.getId()), new JSONObject());
            oiProblemsStatus.getJSONObject(String.valueOf(problem.getId())).put("status", submission.getResult());
            oiProblemsStatus.getJSONObject(String.valueOf(problem.getId())).put("displayId", problem.getDisplayId());
            oiProblemsStatus.getJSONObject(String.valueOf(problem.getId())).put("score", score);
            if (submission.getResult() == ACCEPTED) {
                userProfile.setAcceptedNumber(userProfile.getAcceptedNumber() + 1);
            }
        } else {
            if (oiProblemsStatus.getJSONObject(String.valueOf(problem.getId())).getIntValue("status") != ACCEPTED) {
                userProfile.addScore(score, oiProblemsStatus.getJSONObject(String.valueOf(problem.getId())).getIntValue("score"));
                oiProblemsStatus.getJSONObject(String.valueOf(problem.getId())).put("score", score);
                oiProblemsStatus.getJSONObject(String.valueOf(problem.getId())).put("status", submission.getResult());
                if (submission.getResult() == ACCEPTED) {
                    userProfile.setAcceptedNumber(userProfile.getAcceptedNumber() + 1);
                }
            }
        }
        userProfile.setOiProblemsStatus(oiProblemsStatus);
        userProfileService.updateById(userProfile);
    }

    //        if self.contest.rule_type == ContestRuleType.OI or self.contest.real_time_rank:
//            cache.delete(f"{CacheKey.contest_rank_cache}:{self.contest.id}")
    private void update_contest_rank(Submission submission, Problem problem, Contest contest) {
        if (contest.getRuleType().equals("OI") || contest.getRealTimeRank()) {
            redisUtil.del("contest_rank_cache:" + contest.getId());
        }
        OiContestRank oiContestRank = oiContestRankService.getByUserIdAndContestId(submission.getUserId(), contest.getId());
        if (oiContestRank == null) {
            oiContestRank = new OiContestRank(contest.getId(), submission.getUserId());
            oiContestRankService.save(oiContestRank);
        }
        update_oi_contest_rank(submission, problem, contest, oiContestRank);
    }

    private void update_oi_contest_rank(Submission submission, Problem problem, Contest contest, OiContestRank oiContestRank) {
        String problemId = String.valueOf(submission.getProblemId());
        int currentScore = submission.getStatisticInfo().getIntValue("score");
        JSONObject info = oiContestRank.getSubmissionInfo();
        if (currentScore < info.getIntValue(problemId)) {
            return;
        }
        System.out.println(oiContestRank.getSubmissionInfo());
        Integer lastScore = oiContestRank.getSubmissionInfo().getInteger(problemId);
        if (lastScore != null) {
            oiContestRank.setTotalScore(oiContestRank.getTotalScore() - lastScore + currentScore);
        } else {
            oiContestRank.setTotalScore(oiContestRank.getTotalScore() + currentScore);
        }
        oiContestRank.setSubmissionNumber(oiContestRank.getSubmissionNumber() + 1);
        oiContestRank.getSubmissionInfo().put(problemId, currentScore);
        oiContestRankService.saveOrUpdate(oiContestRank);
    }


}
