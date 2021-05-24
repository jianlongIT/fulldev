package com.example.fulldev.dto;

import com.example.fulldev.dto.validators.PasswordEqual;
import lombok.Builder;


@Builder
@PasswordEqual(min = 1)
public class PerSonDTO {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    private String name;
    private String password1;
    private String password2;
}
