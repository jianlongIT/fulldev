package com.example.fulldev.exception;

public class CreateSuccess extends HttpException {
    public CreateSuccess(int code){
        this.httpStatusCode = 201;
        this.code = code;
    }
}
