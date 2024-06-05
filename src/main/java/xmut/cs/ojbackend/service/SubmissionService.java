package xmut.cs.ojbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybatisflex.core.service.IService;
import xmut.cs.ojbackend.entity.Submission;

/**
 *  服务层。
 *
 * @author leon
 * @since 2024-06-03
 */
public interface SubmissionService extends IService<Submission> {

    public Object submitCode( Submission submission, String ip, Integer userId ) throws JsonProcessingException;
}
