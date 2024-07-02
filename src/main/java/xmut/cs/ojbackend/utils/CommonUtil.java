package xmut.cs.ojbackend.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Component;
import xmut.cs.ojbackend.entity.VO.VOProblemDetail;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommonUtil {
    public String findStringBetween(String template, String before, String after ){
        int in_begin = template.indexOf( before );
        in_begin = template.indexOf( "\n", in_begin );
        in_begin ++;
        int in_end = template.indexOf(after);
        return template.substring( in_begin, in_end );
    }

    public String applyCPPTemplate( String template, String code ){
        String ret = "";
        ret += findStringBetween(template, "//PREPEND BEGIN", "//PREPEND END");
        ret += "\n";
        ret += code;
        ret += "\n";
        ret += findStringBetween(template, "//APPEND BEGIN", "//APPEND END");
        return ret;
    }

    public void replaceTemplate(VOProblemDetail problem) {
        Map<String, String> template = new HashMap<String,String>();
        if(!problem.getTemplate().isEmpty()) {
            for( String key : problem.getTemplate().keySet()){
                String src = problem.getTemplate().getString(key);
                template.put(key, findStringBetween(src, "//TEMPLATE BEGIN", "//TEMPLATE END"));
            }
            JSONObject obj = JSON.parseObject(JSON.toJSONString(template));
            problem.setTemplate(obj);
        }
    }
}
