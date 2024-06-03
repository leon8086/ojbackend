package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import xmut.cs.ojbackend.entity.Problem;
import xmut.cs.ojbackend.mapper.ProblemMapper;
import xmut.cs.ojbackend.service.ProblemService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {

}
