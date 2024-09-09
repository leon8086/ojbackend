package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xmut.cs.ojbackend.entity.ProblemTag;
import xmut.cs.ojbackend.mapper.ProblemTagMapper;
import xmut.cs.ojbackend.service.ProblemTagService;

import java.util.HashMap;
import java.util.Map;

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
    public Object listMajor( String keyword ) {
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

    @Override
    public ProblemTag getMajorTagCreateWhenNotExist(String name) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where(PROBLEM_TAG.NAME.eq(name));
        wrapper.and( PROBLEM_TAG.PARENT_TAG.isNull());
        ProblemTag tag = mapper.selectOneByQuery(wrapper);
        if( tag == null ){
            tag = new ProblemTag();
            tag.setName(name);
            tag.setParentTag(null);
            mapper.insert(tag);
        }
        return tag;
    }

    @Override
    public Map<String,ProblemTag> getTagsCreateWhenNotExist(String majorName, String subName) {
        ProblemTag major = getMajorTagCreateWhenNotExist(majorName);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where(PROBLEM_TAG.NAME.eq(subName));
        wrapper.and(PROBLEM_TAG.PARENT_TAG.eq(major.getId()));
        ProblemTag sub = mapper.selectOneByQuery(wrapper);
        if( sub == null ){
            sub = new ProblemTag();
            sub.setName(subName);
            sub.setParentTag(major.getId());
            mapper.insert(sub);
        }
        Map<String, ProblemTag> ret = new HashMap<>();
        ret.put("major", major);
        ret.put("sub", sub);
        return ret;
    }
}
