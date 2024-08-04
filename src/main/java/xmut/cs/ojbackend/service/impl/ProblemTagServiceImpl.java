package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xmut.cs.ojbackend.entity.ProblemTag;
import xmut.cs.ojbackend.mapper.ProblemTagMapper;
import xmut.cs.ojbackend.service.ProblemTagService;

import static xmut.cs.ojbackend.entity.table.ProblemTagTableDef.PROBLEM_TAG;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class ProblemTagServiceImpl extends ServiceImpl<ProblemTagMapper, ProblemTag> implements ProblemTagService {

    @Override
    public Object adminListMajor( String keyword ) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where(PROBLEM_TAG.PARENT_TAG.isNull());
        if( keyword != null && !keyword.isEmpty()){
            wrapper.and( PROBLEM_TAG.NAME.like(keyword));
        }
        return mapper.selectListWithRelationsByQuery(wrapper);
    }

    @Override
    public Object addTag(String name, Integer parent) {
        ProblemTag tag = new ProblemTag();
        tag.setName(name);
        tag.setParentTag(parent);
        mapper.insert(tag);
        return "添加成功";
    }
}
