package com.algaworks.algafood.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class PasswordCheckValidation implements ConstraintValidator<PasswordCheck, Object> {

    private String passwordField;

    private String passwordConfirmationField;

    @Override
    public void initialize(final PasswordCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean isValid;
        try {
            var passwordField = getFieldName(value, PasswordCheck.PasswordField.class);
            var passwordConfirmationField = getFieldName(value, PasswordCheck.PasswordConfirmationField.class);
            var password = getPasswordFieldValue(value, passwordField);
            var passwordConfirmationValue = getPasswordFieldValue(value, passwordConfirmationField);
            isValid = password.equals(passwordConfirmationValue);
        } catch (Exception e) {
            throw new ValidationException(e);
        } 
        return isValid;
    }

    private String getFieldName(final Object value, final Class<? extends Annotation> clazz){
        return Arrays.stream(value.getClass().getDeclaredFields()).filter(f -> f.isAnnotationPresent(clazz))
                .findFirst().get().getName();
    }

    private String getPasswordFieldValue(final Object value, final String fieldName) throws InvocationTargetException, IllegalAccessException {
        return (String) BeanUtils.getPropertyDescriptor(value.getClass(), fieldName).getReadMethod()
                .invoke(value);
    }
    
}