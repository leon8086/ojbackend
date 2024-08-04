package xmut.cs.ojbackend;

import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import xmut.cs.ojbackend.entity.Exam;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.service.ProblemService;
import xmut.cs.ojbackend.service.SubmissionService;
import xmut.cs.ojbackend.service.UserService;
import xmut.cs.ojbackend.service.exam.ExamService;
import xmut.cs.ojbackend.utils.JwtUtil;
import xmut.cs.ojbackend.utils.Pkdf2Encoder;

@SpringBootTest
class OjbackendApplicationTests {

    @Autowired
    ProblemService problemService;

    @Autowired
    SubmissionService submissionService;

    @Autowired
    UserService userService;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    ExamService examService;

    @Test
    void contextLoads() {
        System.out.println(problemService
                .listPage(1,10,"", "", ""));
    }

    @Test
    void testProblemInfo(){
        System.out.println(problemService.info(22));
    }

    @Test
    void testSubmission(){
        System.out.println(submissionService.getSubmitResult("159055289629478912"));
    }

    @Test
    void testSubmissionList(){
        System.out.println(submissionService.listPage(1, 10, null, null));
    }

    @Test
    void testRedis(){
        JSONObject obj = (JSONObject) redisTemplate.opsForValue().get("user");
        System.out.println(obj);
        if(obj!=null) {
            User user = obj.toJavaObject(User.class);
            System.out.println(user);
        }
    }

    @Test
    void testUserDetail(){
        System.out.println( userDetailsService.loadUserByUsername("root") );
    }

    @Test
    void testToken(){
        User user = userService.getById(1);
        System.out.println(user);
        System.out.println(JwtUtil.generateToken(user));
    }

    @Test
    void testLogin(){
        User user = new User("root", "rootroot");
        System.out.println( userService.login(user) );
    }

    @Test
    void testExamList(){
        Exam exam = new Exam();
        exam.setId(12);
        examService.getProblems(exam);
    }

    @Test
    void testExamDetail(){
        System.out.println(examService.getExamDetail(12));
    }

    @Test
    void testExamRank(){
        System.out.println(examService.getExamRank(12));
    }

    @Test
    void testPassword(){
        Pkdf2Encoder encoder = new Pkdf2Encoder();
        System.out.println( encoder.encode("1"));
    }
}
