package com.example.fulldev.exception;

public class UpdateSuccess extends HttpException {
    public UpdateSuccess(int code) {
        this.httpStatusCode = 200;
        this.code = code;
    }
}
