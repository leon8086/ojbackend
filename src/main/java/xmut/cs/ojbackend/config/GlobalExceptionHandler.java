package xmut.cs.ojbackend.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.access.AccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;
import xmut.cs.ojbackend.base.Result;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public Result accessError(HttpServletRequest req, AccessDeniedException e){
        return Result.error("权限不足！");
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public Result authError(HttpServletRequest req, AuthenticationException e){
        return Result.error("未登陆，请先登陆");
    }

    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, Exception e){
        return Result.error("内部错误");
    }
}