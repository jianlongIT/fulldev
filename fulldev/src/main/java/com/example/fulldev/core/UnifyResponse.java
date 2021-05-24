package com.example.fulldev.core;

import lombok.Getter;

public class UnifyResponse {
    public UnifyResponse(int code, String message, String request) {
        this.code = code;
        this.message = message;
        this.request = request;
    }

    @Getter
    private int code;
    @Getter
    private String message;
    @Getter
    private String request;


}
