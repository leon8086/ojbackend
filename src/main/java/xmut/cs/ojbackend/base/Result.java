package xmut.cs.ojbackend.base;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {
    public static final Integer SUCCESS=100;
    public static final Integer GENERAL_ERROR=200;
    public static final Integer LOGIN_FAILED=210;
    public static final Integer AUTHENTICATION_FAILED=211;
    public static final Integer ACCESS_DENIED=212;
    public static final Integer EXAM_CHECK_FAILED=213;
    public static final Integer WRONG_PARAMS=220;
    public static final Integer INVALID_FILE_CONTENT=230;
    public static final Integer FILE_TOO_LARGE=231;
    public static final Integer UNSUPPORTED_FILE=232;
    public static final Integer UPLOAD_FAILED=233;
    private final static Map<Integer, String> messageMap = new HashMap<Integer,String>(){{
        put(SUCCESS, "调用成功");
        put(GENERAL_ERROR, "通用错误");
        put(LOGIN_FAILED, "用户名或密码错误");
        put(AUTHENTICATION_FAILED, "未登录，请先登录");
        put(ACCESS_DENIED, "权限不足");
        put(EXAM_CHECK_FAILED, "考试调用失败");
        put(WRONG_PARAMS, "参数错误");
        put(INVALID_FILE_CONTENT, "错误的文件类型");
        put(FILE_TOO_LARGE, "文件大小超限");
        put(UNSUPPORTED_FILE, "不支持的文件格式");
        put(UPLOAD_FAILED, "文件上传失败");
    }};
    Integer code;
    String  message;
    Object  data;

    public Result(Integer code, String msg, Object data ){
        this.code = code;
        this.message = msg;
        this.data = data;
        //System.out.println(this);
    }

    public Result( Integer code, Object data ){
        this.code = code;
        this.message = messageMap.get(code);
        this.data = data;
    }

    public Result( Integer code ){
        this.code = code;
        this.message = messageMap.get(code);
        this.data = null;
    }

    public static Result success( Object data ){
        return new Result(SUCCESS, data);
    }
    public static Result error( Integer code ){
        return new Result(code, messageMap.get(code), null );
    }

    public static Result error( Integer code, Object data ){
        return new Result(code, messageMap.get(code), data );
    }

    public static Result errorGeneral(){
        return new Result(GENERAL_ERROR, messageMap.get(GENERAL_ERROR), null );
    }

    public static Result errorGeneral(Exception e){
        return new Result(GENERAL_ERROR, messageMap.get(GENERAL_ERROR), e );
    }

    public static Result loginFailed(){
        return Result.error(LOGIN_FAILED);
    }
}