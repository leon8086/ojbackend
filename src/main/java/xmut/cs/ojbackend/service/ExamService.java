package xmut.cs.ojbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybatisflex.core.service.IService;
import xmut.cs.ojbackend.config.ExamCheckException;
import xmut.cs.ojbackend.entity.Exam;
import xmut.cs.ojbackend.entity.ExamProfile;
import xmut.cs.ojbackend.entity.ExamSubmission;
import xmut.cs.ojbackend.entity.User;

import java.io.Serializable;
import java.util.Date;

/**
 *  服务层。
 *
 * @author leon
 * @since 2024-06-26
 */
public interface ExamService extends IService<Exam> {

    Object adminUpdateExam(Exam exam);

    Object adminCloseExam(Integer id, Boolean isEnded);

    Object adminListPage(Integer page, Integer limit, String keyword, Integer courseId );

    Object adminListByOwnerPage(Integer page, Integer limit, String keyword, Integer courseId);

    Object adminGetExamDetail(Integer id);

    Object getProblems(Exam exam) throws ExamCheckException;

    Object getInfo(Serializable id);

    Object submitCode(ExamSubmission examSubmission, String ip, User user) throws JsonProcessingException, ExamCheckException;

    Object getProblemSubmission(String examId);

    Object getSubmissionById(String id);

    ExamProfile getExamProfile(Integer userId, Integer examId);

    Object getExamRank(Integer examId);

    Object quitExam(Integer examId) throws ExamCheckException;

    Object listValidExam();

    Object listPageExam( Integer page, Integer limit, Integer courseId, String keyword );

    Object adminGetExamSubmission(Integer examId, Integer userId);

    Object adminRestart(Integer id, Date endTime);

    Object adminGetUserExamProfile(Integer examId, Integer userId);

    Object adminRestartUser(Integer examId, Integer userId) throws ExamCheckException;

    Object restart(Integer examId) throws ExamCheckException;

    Object adminRecount(Integer examId);
}
