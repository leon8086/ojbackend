package xmut.cs.ojbackend.service;

import com.mybatisflex.core.service.IService;
import xmut.cs.ojbackend.entity.Course;
import xmut.cs.ojbackend.entity.DTO.DTOCourseImport;
import xmut.cs.ojbackend.entity.DTO.DTOCourseUserUpdate;
import xmut.cs.ojbackend.entity.User;

import java.util.List;

/**
 *  服务层。
 *
 * @author leon
 * @since 2024-06-03
 */
public interface CourseService extends IService<Course> {

    Object listPage(Integer page, Integer limit, String keyword);

    Object importStudent(DTOCourseImport course);

    Object removeStudent(Integer id);

    Object listPageByUser(Integer page, Integer limit, String keyword);

    Object adminAllBrief();

    Object allBrief();

    List<Course> getUsersCourse(User user );

    Object adminRmoveStudent(Integer studentId, Integer courseId);

    Object getCourseStudents(Integer id);

    Object updateStudents(DTOCourseUserUpdate params);
}
