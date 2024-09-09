package xmut.cs.ojbackend.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import xmut.cs.ojbackend.entity.LoginUser;
import xmut.cs.ojbackend.entity.Problem;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.entity.VO.VOProblemAdminDetail;
import xmut.cs.ojbackend.entity.VO.VOProblemDetail;
import xmut.cs.ojbackend.mapper.exam.ExamProfilesMapper;

import java.util.HashMap;
import java.util.Map;

import static xmut.cs.ojbackend.entity.table.ExamProfileTableDef.EXAM_PROFILE;

@Component
public class CommonUtil {

    @Autowired
    ExamProfilesMapper examProfilesMapper;

    public String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-real-ip");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public CommonUtil(DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration) {
    }

    public String findStringBetween(String template, String before, String after ){
        int in_begin = template.indexOf( before );
        in_begin = template.indexOf( "\n", in_begin );
        in_begin ++;
        int in_end = template.indexOf(after);
        return template.substring( in_begin, in_end );
    }

    public String applyTemplate( JSONObject template, String code ){
        String ret = "//PREPEND BEGIN\n\n";
        ret += template.getString("prepend").trim();
        ret += "\n//PREPEND END\n\n";
        ret += "//TEMPLATE BEGIN\n\n";
        ret += code.trim();
        ret += "\n//TEMPLATE END\n\n";
        ret += "//APPEND BEGIN\n\n";
        ret += template.getString("append").trim();
        ret += "\n//APPEND END\n";
        return ret;
    }

    public String makeEditorTemplate( JSONObject template ) {
        return applyTemplate( template, template.getString("template"));
    }

    public void toEditorTemplate( VOProblemAdminDetail problem ){
        Map<String, String> template = new HashMap<String,String>();
        if(!problem.getTemplate().isEmpty()) {
            for( String key : problem.getTemplate().keySet()){
                String src = makeEditorTemplate( problem.getTemplate().getJSONObject(key));
                template.put(key, src);
            }
            JSONObject obj = JSON.parseObject(JSON.toJSONString(template));
            problem.setTemplate(obj);
        }
    }

    public void fromEditorTemplate(Problem problem) {
        JSONObject template = new JSONObject();
        if(!problem.getTemplate().isEmpty()) {
            for( String key : problem.getTemplate().keySet()){
                String src = problem.getTemplate().getString(key);
                JSONObject temp = new JSONObject();
                temp.put("prepend", findStringBetween(src, "//PREPEND BEGIN", "//PREPEND END") );
                temp.put("template", findStringBetween(src, "//TEMPLATE BEGIN", "//TEMPLATE END") );
                temp.put("append", findStringBetween(src, "//APPEND BEGIN", "//APPEND END") );
                template.put(key, temp);
            }
            problem.setTemplate(template);
        }
    }

    public void toAnswerTemplate(VOProblemDetail problem) {
        Map<String, String> template = new HashMap<String,String>();
        if(!problem.getTemplate().isEmpty()) {
            for( String key : problem.getTemplate().keySet()){
                JSONObject src = problem.getTemplate().getJSONObject(key);
                template.put(key, src.getString("template"));
            }
            JSONObject obj = JSON.parseObject(JSON.toJSONString(template));
            problem.setTemplate(obj);
        }
    }

    public User getCurrentUser(){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginUser.getUser();
    }

    public < T > Page<T> genPage(Integer page, Integer limit ){
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 1000;
        }
        return new Page<T>(page, limit);
    }

    public Boolean isUserInExam(){
        User user = getCurrentUser();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.where(EXAM_PROFILE.IS_ENDED.eq(false));
        wrapper.where(EXAM_PROFILE.USER_ID.eq(user.getId()));
        return examProfilesMapper.selectCountByQuery(wrapper) != 0;
    }
}
