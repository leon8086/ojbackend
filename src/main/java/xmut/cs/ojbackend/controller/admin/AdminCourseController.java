package xmut.cs.ojbackend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmut.cs.ojbackend.base.Result;
import xmut.cs.ojbackend.entity.Course;
import xmut.cs.ojbackend.entity.DTO.DTOCourseImport;
import xmut.cs.ojbackend.entity.DTO.DTOCourseUserUpdate;
import xmut.cs.ojbackend.service.CourseService;

import java.util.Map;

@RestController
@RequestMapping("/admin/course")
public class AdminCourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("update")
    public Result update(@RequestBody Course course) {
        //System.out.println(course);
        if( course.getId() == null ){
            return Result.success(courseService.save(course));
        }
        else{
            return Result.success(courseService.updateById(course));
        }
    }

    @GetMapping("list")
    public Result listPage( Integer page, Integer limit, String keyword  ){
        return Result.success(courseService.listPage(page, limit, keyword));
    }

    @GetMapping("list-by-user")
    public Result listPageByUser( Integer page, Integer limit, String keyword  ){
        return Result.success(courseService.listPageByUser(page, limit, keyword));
    }

    @GetMapping("all")
    public Result allBrief(){
        return Result.success( courseService.adminAllBrief() );
    }

    @PostMapping("import-students")
    public Result importStudents(@RequestBody DTOCourseImport course){
        //System.out.println(course);
        return Result.success( courseService.importStudent(course));
    }

    @PostMapping("remove-student")
    public Result removeStudent( @RequestBody Map<String, Integer> params ){
        Integer studentId = params.get("studentId");
        Integer courseId = params.get("courseId");
        return Result.success( courseService.adminRmoveStudent(studentId, courseId));
    }

    @GetMapping("students")
    public Result getCourseStudents( Integer id ){
        return Result.success( courseService.getCourseStudents( id ) );
    }

    @PostMapping("update-students")
    public Result updateStudents( @RequestBody DTOCourseUserUpdate params ){
        return Result.success(courseService.updateStudents(params));
    }
}
