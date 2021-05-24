package com.example.fulldev.exception;


import org.springframework.http.HttpStatus;


public class ForbiddenException extends HttpException {

    public ForbiddenException(Integer code) {
        this.code=code;
        this.httpStatusCode=  HttpStatus.FORBIDDEN.value();
    }
}
