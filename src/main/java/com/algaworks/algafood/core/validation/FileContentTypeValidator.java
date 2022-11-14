package com.algaworks.algafood.core.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile>{

    private List<String> allowed;

    @Override
    public void initialize(final FileContentType constraintAnnotation) {
        this.allowed = Arrays.asList(constraintAnnotation.allowed());
    }

	@Override
	public boolean isValid(final MultipartFile value, final ConstraintValidatorContext context) {
        return value == null || this.allowed.contains(value.getContentType());
	}

    
}