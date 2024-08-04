package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmut.cs.ojbackend.entity.Course;
import xmut.cs.ojbackend.entity.CourseUser;
import xmut.cs.ojbackend.entity.DTO.DTOCourseImport;
import xmut.cs.ojbackend.entity.DTO.DTOUserImport;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.entity.VO.VOCourseList;
import xmut.cs.ojbackend.entity.VO.VOUserLogin;
import xmut.cs.ojbackend.entity.entitymapper.DTOUserImportWrapper;
import xmut.cs.ojbackend.mapper.CourseMapper;
import xmut.cs.ojbackend.mapper.CourseUserMapper;
import xmut.cs.ojbackend.mapper.UserMapper;
import xmut.cs.ojbackend.service.CourseService;
import xmut.cs.ojbackend.utils.Pkdf2Encoder;

import java.util.*;

import static xmut.cs.ojbackend.entity.table.CourseTableDef.COURSE;
import static xmut.cs.ojbackend.entity.table.UserTableDef.USER;

/**
 *  服务层实现。
 *
 * @author leon
 * @since 2024-06-03
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DTOUserImportWrapper dtoUserImportWrapper;

    @Autowired
    private CourseUserMapper courseUserMapper;

    @Override
    public Object listPage(Integer page, Integer limit, String keyword) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 1000;
        }
        QueryWrapper wrapper = new QueryWrapper();
        if(keyword != null && !keyword.isEmpty()){
            wrapper.where(COURSE.COURSE_NAME.like(keyword));
        }
        return courseMapper.paginateWithRelationsAs(page, limit, wrapper, VOCourseList.class);
    }

    @Override
    public Object importStudent(DTOCourseImport course) {
        QueryWrapper wrapper = new QueryWrapper();
        List<VOUserLogin> failed = new ArrayList<VOUserLogin>();
        Integer count = 0;
        Pkdf2Encoder encoder = new Pkdf2Encoder();
        for(DTOUserImport u : course.getStudents()){
            try {
                wrapper.clear();
                wrapper.where(USER.USERNAME.eq(u.getUsername()));
                User user = userMapper.selectOneByQuery(wrapper);
                if (user == null) {
                    user = dtoUserImportWrapper.toEntity(u);
                    user.setPassword(encoder.encode(user.getPassword()));
                    user.setCreateTime(Calendar.getInstance().getTime());
                    //System.out.println(user);
                    userMapper.insert(user);
                }
                CourseUser cu = new CourseUser();
                cu.setUserId(user.getId());
                cu.setCourseId(course.getId());
                courseUserMapper.insert(cu);
                count ++;
            }
            catch(Exception e){
                if( failed.size() < 10){
                    VOUserLogin ur = new VOUserLogin();
                    ur.setUsername(u.getUsername());
                    ur.setRealName(e.getClass().toString());
                    ur.setToken(e.getMessage());
                    failed.add(ur);
                }
            }
        }
        Map<String, Object> ret = new HashMap<>();
        ret.put( "insert", count );
        ret.put("failed", failed);
        return ret;
    }
}
