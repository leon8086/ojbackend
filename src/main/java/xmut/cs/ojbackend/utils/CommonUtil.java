package xmut.cs.ojbackend.utils;

import org.springframework.stereotype.Component;

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
}
