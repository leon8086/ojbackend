package xmut.cs.ojbackend.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmut.cs.ojbackend.entity.Course;
import xmut.cs.ojbackend.entity.CourseUser;
import xmut.cs.ojbackend.entity.DTO.DTOCourseImport;
import xmut.cs.ojbackend.entity.DTO.DTOCourseUserUpdate;
import xmut.cs.ojbackend.entity.DTO.DTOUserImport;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.entity.VO.VOCourseBrief;
import xmut.cs.ojbackend.entity.VO.VOCourseList;
import xmut.cs.ojbackend.entity.VO.VOUserCourse;
import xmut.cs.ojbackend.entity.VO.VOUserLogin;
import xmut.cs.ojbackend.entity.entitymapper.DTOUserImportWrapper;
import xmut.cs.ojbackend.mapper.CourseMapper;
import xmut.cs.ojbackend.mapper.CourseUserMapper;
import xmut.cs.ojbackend.mapper.UserMapper;
import xmut.cs.ojbackend.service.CourseService;
import xmut.cs.ojbackend.service.UserService;
import xmut.cs.ojbackend.utils.CommonUtil;
import xmut.cs.ojbackend.utils.Pkdf2Encoder;

import java.util.*;

import static xmut.cs.ojbackend.entity.table.CourseTableDef.COURSE;
import static xmut.cs.ojbackend.entity.table.CourseUserTableDef.COURSE_USER;
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

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private UserService userService;

    @Override
    public Object listPage(Integer page, Integer limit, String keyword) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 1000;
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select(COURSE.ALL_COLUMNS);
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
                    user = new User();
                    user.setUsername(u.getUsername());
                    user.setEmail(u.getEmail());
                    user.setCreateTime(Calendar.getInstance().getTime());
                    user.setIsDisabled(false);
                    user.setGrade(userService.fetchGradeId(u.getGrade()));
                    user.setPassword(encoder.encode(u.getPassword()));
                    if( user.getGrade() == 1 ){ // 管理员
                        user.setAdminType(User.ADMINTYPE_ADMIN);
                    }
                    else{
                        user.setAdminType(User.ADMINTYPE_REGULAR);
                    }
                    user.setRealName(u.getRealName());
                    user.setFirstLogin(true);
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
                //System.out.println(e);
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

    @Override
    public Object removeStudent(Integer id) {
        return null;
    }

    @Override
    public Object listPageByUser(Integer page, Integer limit, String keyword) {
        User user = commonUtil.getCurrentUser();
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
        wrapper.and(COURSE.OWNER_ID.eq(user.getId()));
        return courseMapper.paginateWithRelationsAs(page, limit, wrapper, VOCourseList.class);
    }

    @Override
    public Object adminAllBrief() {
        User user = commonUtil.getCurrentUser();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select("id","course_name","owner_id","is_closed");
        wrapper.where(COURSE.IS_CLOSED.eq(false));
        if( user.getAdminType() == User.ADMINTYPE_ADMIN ){
            wrapper.and( COURSE.OWNER_ID.eq(user.getId()));
        }
        wrapper.orderBy( COURSE.OWNER_ID.asc());
        List<VOCourseBrief> courses = courseMapper.selectListWithRelationsByQueryAs(wrapper, VOCourseBrief.class);
        Map<String, List<VOCourseBrief>> ret = new HashMap<>();
        for( VOCourseBrief c : courses ){
            List<VOCourseBrief> subCourse = ret.computeIfAbsent(c.getOwner(), k -> new ArrayList<>());
            subCourse.add(c);
        }
        return ret;
    }

    @Override
    public Object allBrief() {
        User user = commonUtil.getCurrentUser();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.from(COURSE.as("c"), COURSE_USER.as("cu"));
        wrapper.where(COURSE.ID.eq(COURSE_USER.COURSE_ID));
        wrapper.and(COURSE_USER.USER_ID.eq(user.getId()));
        wrapper.and(COURSE.IS_CLOSED.eq(false));
        List<VOCourseBrief> courses = courseMapper.selectListWithRelationsByQueryAs(wrapper, VOCourseBrief.class);
        return courses;
    }

    @Override
    public List<Course> getUsersCourse(User user) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select(COURSE.ALL_COLUMNS)
                .select(COURSE_USER.USER_ID,COURSE_USER.COURSE_ID)
                .from(COURSE.as("c"), COURSE_USER.as("cu"))
                .where(COURSE_USER.COURSE_ID.eq(COURSE.ID))
                .and(COURSE_USER.USER_ID.eq(user.getId()));
        return courseMapper.selectListByQuery(wrapper);
    }

    @Override
    public Object adminRmoveStudent(Integer studentId, Integer courseId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where(COURSE_USER.USER_ID.eq(studentId));
        wrapper.where(COURSE_USER.COURSE_ID.eq(courseId));
        courseUserMapper.deleteByQuery(wrapper);
        return "删除成功";
    }

    @Override
    public Object getCourseStudents(Integer id) {
        QueryWrapper wrapper = new QueryWrapper();
        //System.out.println(id);
        wrapper.from(USER.as("u"), COURSE_USER.as("cu"));
        wrapper.where(COURSE_USER.COURSE_ID.eq(id));
        wrapper.where(COURSE_USER.USER_ID.eq(USER.ID));
        return userMapper.selectListWithRelationsByQueryAs(wrapper, VOUserCourse.class );
    }

    @Override
    public Object updateStudents(DTOCourseUserUpdate params) {
        Integer courseId = params.getId();
        List<Integer> addList = params.getAdd();
        List<Integer> removeList = params.getRemove();

        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        for( Integer id: addList ){
            try {
                CourseUser entity = new CourseUser();
                entity.setCourseId(courseId);
                entity.setUserId(id);
                courseUserMapper.insert(entity);
            }
            catch(Exception e){
                Map<String, Object> reason = new HashMap<String, Object>();
                reason.put("id", id);
                reason.put("opr", "add");
                reason.put("error", e);
                ret.add(reason);
            }
        }
        for( Integer id: removeList){
            try{
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.where( COURSE_USER.USER_ID.eq(id));
                wrapper.where( COURSE_USER.COURSE_ID.eq(courseId));
                courseUserMapper.deleteByQuery(wrapper);
            }
            catch (Exception e){
                Map<String, Object> reason = new HashMap<String, Object>();
                reason.put("id", id);
                reason.put("opr", "remove");
                reason.put("error", e);
                ret.add(reason);
            }
        }
        return ret;
    }
}
