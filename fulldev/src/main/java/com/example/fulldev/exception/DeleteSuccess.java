package com.example.fulldev.exception;

public class DeleteSuccess extends HttpException {
    public DeleteSuccess(int code) {
        this.httpStatusCode = 200;
        this.code = code;
    }
}
