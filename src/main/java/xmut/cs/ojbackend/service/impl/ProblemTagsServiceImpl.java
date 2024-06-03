package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import xmut.cs.ojbackend.entity.ProblemTags;
import xmut.cs.ojbackend.mapper.ProblemTagsMapper;
import xmut.cs.ojbackend.service.ProblemTagsService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class ProblemTagsServiceImpl extends ServiceImpl<ProblemTagsMapper, ProblemTags> implements ProblemTagsService {

}
