package com.algaworks.algafood.core.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { PasswordCheckValidation.class })
public @interface PasswordCheck {

    String message() default "as senhas são diferentes";

	Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String passwordField();

    String passwordConfirmationField();
    
}