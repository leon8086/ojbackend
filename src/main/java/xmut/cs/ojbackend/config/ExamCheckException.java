package xmut.cs.ojbackend.config;

import lombok.Getter;

import java.util.Map;

@Getter
public class ExamCheckException extends Exception {
    Map<String, Object> reason;

    public ExamCheckException( Map<String, Object> reason ){
        this.reason = reason;
    }

}
