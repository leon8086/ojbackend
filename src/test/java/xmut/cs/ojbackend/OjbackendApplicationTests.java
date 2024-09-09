package xmut.cs.ojbackend;

import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.core.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import xmut.cs.ojbackend.entity.Course;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.mapper.CourseMapper;
import xmut.cs.ojbackend.mapper.CourseUserMapper;
import xmut.cs.ojbackend.service.ExamService;
import xmut.cs.ojbackend.service.ProblemService;
import xmut.cs.ojbackend.service.SubmissionService;
import xmut.cs.ojbackend.service.UserService;
import xmut.cs.ojbackend.utils.JwtUtil;
import xmut.cs.ojbackend.utils.Pkdf2Encoder;

import java.util.List;

import static xmut.cs.ojbackend.entity.table.CourseTableDef.COURSE;
import static xmut.cs.ojbackend.entity.table.CourseUserTableDef.COURSE_USER;

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

    @Autowired
    CourseUserMapper courseUserMapper;

    @Autowired
    CourseMapper courseMapper;

    @Test
    void contextLoads() {
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
    void testExamRank(){
        System.out.println(examService.getExamRank(12));
    }

    @Test
    void testPassword(){
        Pkdf2Encoder encoder = new Pkdf2Encoder();
        System.out.println( encoder.encode("1"));
    }

    @Test
    void testUserCourse(){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select(COURSE.ALL_COLUMNS)
            .select(COURSE_USER.USER_ID,COURSE_USER.COURSE_ID)
            .from(COURSE.as("c"), COURSE_USER.as("cu"))
            .where(COURSE_USER.COURSE_ID.eq(COURSE.ID))
            .and(COURSE_USER.USER_ID.eq(5));
        List<Course> ret = courseMapper.selectListByQuery(wrapper);
        System.out.println(ret);
        //User user = new User();
        //user.setId(5);
        //System.out.println( userService.getUserCourses(user));
    }
}
