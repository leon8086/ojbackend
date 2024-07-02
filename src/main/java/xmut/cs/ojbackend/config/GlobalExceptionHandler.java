package xmut.cs.ojbackend.config;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xmut.cs.ojbackend.base.Result;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public Result accessError(HttpServletRequest req, AccessDeniedException e){
        return Result.error(Result.ACCESS_DENIED);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public Result authError(HttpServletRequest req, AuthenticationException e){
        return Result.error(Result.AUTHENTICATION_FAILED);
    }

    @ExceptionHandler(value = SignatureVerificationException.class)
    @ResponseBody
    public Result jwtFailed(HttpServletRequest req, Exception e){
        return Result.error(Result.AUTHENTICATION_FAILED);
    }

    @ExceptionHandler(value = Exception.class )
    @ResponseBody
    public Result general(HttpServletRequest req, Exception e){
        return Result.error(Result.GENERAL_ERROR, e);
    }
}