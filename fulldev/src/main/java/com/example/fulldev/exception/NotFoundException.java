package com.example.fulldev.exception;

public class NotFoundException extends HttpException {
    public NotFoundException(int code){
        this.httpStatusCode=404;
        this.code=code;
    }
}
