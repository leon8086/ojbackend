package xmut.cs.ojbackend.base;

import lombok.Data;

@Data
public class Result {
    Integer code;
    String  message;
    Object  data;

    public Result(int code, String msg, Object data ){
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public static Result success( Object data ){
        return new Result(100,"success", data);
    }
    public static Result error( String msg ){
        return new Result(100, msg, null );
    }
}