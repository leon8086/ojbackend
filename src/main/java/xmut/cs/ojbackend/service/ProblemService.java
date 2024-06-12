package xmut.cs.ojbackend.service;

import com.mybatisflex.core.service.IService;
import xmut.cs.ojbackend.entity.Problem;

/**
 *  服务层。
 *
 * @author leon
 * @since 2024-06-03
 */
public interface ProblemService extends IService<Problem> {
    public Object listPage(Integer page, Integer limit, String keyword, String difficulty, String tag);

    public Object info( Integer id );
}