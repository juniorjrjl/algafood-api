package com.algaworks.algafood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class PasswordCheckValidation implements ConstraintValidator<PasswordCheck, Object> {

    private String passwordField;

    private String passwordConfirmationField;

    @Override
    public void initialize(PasswordCheck constraintAnnotation) {
        this.passwordField = constraintAnnotation.passwordField();
        this.passwordConfirmationField = constraintAnnotation.passwordConfirmationField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean isValid = false;
        try {
            String password = (String) BeanUtils
                    .getPropertyDescriptor(value.getClass(), passwordField).getReadMethod()
                    .invoke(value);
            String passworConfirmation = (String) BeanUtils
                .getPropertyDescriptor(value.getClass(), passwordConfirmationField).getReadMethod()
                .invoke(value);
            isValid = password.equals(passworConfirmation);
        } catch (Exception e) {
            throw new ValidationException(e);
        } 
        return isValid;
    }

    
}