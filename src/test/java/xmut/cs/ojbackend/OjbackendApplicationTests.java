package xmut.cs.ojbackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmut.cs.ojbackend.service.ProblemService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OjbackendApplicationTests {

    @Autowired
    ProblemService problemService;
    @Test
    void contextLoads() {
        System.out.println(problemService
                .listPage(1,10,"", "", "队列"));
    }

}
