package com.example.fulldev.dto;

import com.example.fulldev.dto.enumeration.LoginType;
import com.example.fulldev.dto.validators.TokenPassword;

import javax.validation.constraints.NotBlank;

public class TokenGetDTO {
    @NotBlank(message = "account不允许为空")
    private String account;
    @TokenPassword(min = 6, max = 30, message = "{token.password}")
    private String password;

    private LoginType type;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginType getType() {
        return type;
    }

    public void setType(LoginType type) {
        this.type = type;
    }
}
