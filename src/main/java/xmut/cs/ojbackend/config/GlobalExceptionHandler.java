package xmut.cs.ojbackend.config;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xmut.cs.ojbackend.base.Result;

@ControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<Result> genResult( Result result ){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE );
        return new ResponseEntity<>( result, headers, HttpStatus.OK );
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<Result> accessError(AccessDeniedException e){
        return  genResult( Result.error(Result.ACCESS_DENIED) );
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<Result> authError(AuthenticationException e){
        return genResult(Result.error(Result.AUTHENTICATION_FAILED));
    }

    @ExceptionHandler(value = SignatureVerificationException.class)
    @ResponseBody
    public ResponseEntity<Result> jwtFailed(Exception e){
        return genResult(Result.error(Result.AUTHENTICATION_FAILED));
    }

    @ExceptionHandler(value = ExamCheckException.class)
    @ResponseBody
    public ResponseEntity<Result> examCheckFailed(ExamCheckException e){
        return genResult(Result.error(Result.EXAM_CHECK_FAILED, e.getReason()));
    }

    @ExceptionHandler(value = Exception.class )
    @ResponseBody
    public ResponseEntity<Result> general( Exception e){
        return genResult(Result.error(Result.GENERAL_ERROR, e));
    }
}