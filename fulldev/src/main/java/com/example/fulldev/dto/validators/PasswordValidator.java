package com.example.fulldev.dto.validators;

import com.example.fulldev.dto.PerSonDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordEqual, PerSonDTO> {
    private int min;
    private int max;

    @Override
    public void initialize(PasswordEqual constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(PerSonDTO perSonDTO, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(perSonDTO.getPassword1());
        System.out.println(perSonDTO.getPassword2());
        return perSonDTO.getPassword1().equals(perSonDTO.getPassword2());
    }
}
