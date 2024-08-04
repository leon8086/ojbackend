package xmut.cs.ojbackend.controller.admin;

import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.Course;
import xmut.cs.ojbackend.entity.DTO.DTOCourseImport;
import xmut.cs.ojbackend.service.CourseService;

import java.io.Serializable;

@RestController
@RequestMapping("/admin/course")
public class AdminCourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("update")
    public Result update(@RequestBody Course course) {
        System.out.println(course);
        if( course.getId() == null ){
            return Result.success(courseService.save(course));
        }
        else{
            return Result.success(courseService.updateById(course));
        }
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public Result listPage( Integer page, Integer limit, String keyword  ){
        return Result.success(courseService.listPage(page, limit, keyword));
    }

    @PostMapping("import-students")
    public Result importStudents(@RequestBody DTOCourseImport course){
        //System.out.println(course);
        return Result.success( courseService.importStudent(course));
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public Course getInfo(@PathVariable Serializable id) {
        return courseService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<Course> page(Page<Course> page) {
        return courseService.page(page);
    }

}
