package xmut.cs.ojbackend.service;

import com.mybatisflex.core.service.IService;
import xmut.cs.ojbackend.entity.Course;
import xmut.cs.ojbackend.entity.DTO.DTOCourseImport;

/**
 *  服务层。
 *
 * @author leon
 * @since 2024-06-03
 */
public interface CourseService extends IService<Course> {

    Object listPage(Integer page, Integer limit, String keyword);

    Object importStudent(DTOCourseImport course);
}
