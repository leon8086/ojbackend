package xmut.cs.ojbackend;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import xmut.cs.ojbackend.entity.Submission;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.service.ProblemService;
import xmut.cs.ojbackend.service.SubmissionService;
import xmut.cs.ojbackend.service.UserService;
import xmut.cs.ojbackend.utils.JwtUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
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

    @Test
    void contextLoads() {
        System.out.println(problemService
                .listPage(1,10,"", "", ""));
    }

    @Test
    void testSubmitCode() throws JsonProcessingException {
        Submission submission = new Submission();
        submission.setProblemId(21);
        String code = """
                #include <iostream>
                using namespace std;
                int main() {
                  int a, b;
                  cin >> a >> b;
                  cout << a + b << endl;
                  return 0;
                }
                """;
        submission.setCode(code);
        submission.setLanguage("C++");
        submissionService.submitCode(submission, "127.0.0.1", 1);
    }

    @Test
    void testApplyTemplate() throws JsonProcessingException {
        Submission submission = new Submission();
        submission.setProblemId(22);
        String code = """
                int add_func( int a, int b ){
                    return a+b;
                }
                """;
        submission.setCode(code);
        submission.setLanguage("C");
        submissionService.submitCode(submission, "127.0.0.1", 1);
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
}
