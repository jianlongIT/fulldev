package com.example.fulldev.core;

import com.example.fulldev.core.configuration.ExceptionCodeConfiguration;
import com.example.fulldev.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @Autowired
    private ExceptionCodeConfiguration exceptionCodeConfiguration;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(HttpServletRequest request, Exception e) {
        String method = request.getMethod();
        String url = request.getRequestURI();
        System.out.println(e);
        UnifyResponse msg = new UnifyResponse(9999, "服务器内部错误", method + "" + url);
        return msg;
    }

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<UnifyResponse> handleHttpException(HttpServletRequest request, HttpException e) {

        String method = request.getMethod();
        String url = request.getRequestURI();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println(e.getHttpStatusCode());
        HttpStatus status = HttpStatus.resolve(e.getHttpStatusCode());
        UnifyResponse msg = new UnifyResponse(e.getCode(), exceptionCodeConfiguration.getMessage(e.getCode()), method + "   " + url);
        ResponseEntity<UnifyResponse> r = new ResponseEntity<>(msg, headers, status);
        return r;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    private UnifyResponse handleconstrantException(HttpServletRequest request, ConstraintViolationException e) {

        String method = request.getMethod();
        String url = request.getRequestURI();
        return new UnifyResponse(10001, e.getMessage(), method + "" + url);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleBeanvaldation(HttpServletRequest request, MethodArgumentNotValidException e) {
        String method = request.getMethod();
        String url = request.getRequestURI();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String errormessage = formatAllErrorMessage(errors);
        return new UnifyResponse(10001, errormessage, method + "" + url);
    }


    private String formatAllErrorMessage(List<ObjectError> errors) {
        StringBuffer errorMessage = new StringBuffer();
        errors.forEach(error -> {
            errorMessage.append(error.getDefaultMessage()).append(";");
        });
        return errorMessage.toString();
    }
}
