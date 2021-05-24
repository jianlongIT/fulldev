package com.example.fulldev.exception;

public class ServerException extends HttpException {
    public ServerException(int code) {
        this.code = code;
        this.httpStatusCode = 500;
    }
}
