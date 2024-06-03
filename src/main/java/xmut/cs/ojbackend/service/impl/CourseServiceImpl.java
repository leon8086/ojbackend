package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import xmut.cs.ojbackend.entity.Course;
import xmut.cs.ojbackend.mapper.CourseMapper;
import xmut.cs.ojbackend.service.CourseService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

}
