package xmut.cs.ojbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybatisflex.core.service.IService;
import xmut.cs.ojbackend.entity.Submission;
import xmut.cs.ojbackend.entity.VO.VOSubmissionResult;

/**
 *  服务层。
 *
 * @author leon
 * @since 2024-06-03
 */
public interface SubmissionService extends IService<Submission> {

    public Object submitCode( Submission submission, String ip, Integer userId ) throws JsonProcessingException;

    public Object listPage(Integer page, Integer limit, Integer result, String username);

    public Object getSubmitResult( String id );
}
