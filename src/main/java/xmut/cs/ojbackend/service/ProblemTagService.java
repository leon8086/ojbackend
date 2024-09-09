package xmut.cs.ojbackend.service;

import com.mybatisflex.core.service.IService;
import xmut.cs.ojbackend.entity.ProblemTag;

import java.util.Map;

/**
 *  服务层。
 *
 * @author leon
 * @since 2024-06-03
 */
public interface ProblemTagService extends IService<ProblemTag> {

    Object listMajor( String keyword );

    Object addTag(String name, Integer parentId);

    ProblemTag getMajorTagCreateWhenNotExist(String name);

    Map<String,ProblemTag> getTagsCreateWhenNotExist(String majorName, String subName);
}
