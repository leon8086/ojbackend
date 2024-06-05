package xmut.cs.ojbackend;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmut.cs.ojbackend.entity.Submission;
import xmut.cs.ojbackend.service.ProblemService;
import xmut.cs.ojbackend.service.SubmissionService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OjbackendApplicationTests {

    @Autowired
    ProblemService problemService;

    @Autowired
    SubmissionService submissionService;
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
                  short a, b;
                  cin >> a >> b;
                  cout << a + b << endl;
                  return 0;
                }
                """;
        submission.setCode(code);
        submission.setLanguage("C++");
        submissionService.submitCode(submission, "127.0.0.1", 1);
    }
}
