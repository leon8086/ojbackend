package xmut.cs.ojbackend.service.exam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybatisflex.core.service.IService;
import xmut.cs.ojbackend.entity.Exam;
import xmut.cs.ojbackend.entity.ExamProfile;
import xmut.cs.ojbackend.entity.ExamSubmission;
import xmut.cs.ojbackend.entity.User;

import java.io.Serializable;

/**
 *  服务层。
 *
 * @author leon
 * @since 2024-06-26
 */
public interface ExamService extends IService<Exam> {

    Object updateExam(Exam exam);

    Object getProblems(Exam exam);

    Object getExamDetail(Integer id);

    Object getInfo(Serializable id);

    Object submitCode(ExamSubmission examSubmission, String ip, User user) throws JsonProcessingException;

    Object getProblemSubmission(String examId, Integer problemId, Integer userId);

    Object getSubmissionById(String id);

    ExamProfile getExamProfile(Integer userId, Integer examId);
}
