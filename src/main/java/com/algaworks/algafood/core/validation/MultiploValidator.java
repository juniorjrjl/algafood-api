package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number>{

    private int numeroMultiplo;

    @Override
    public void initialize(final Multiplo constraintAnnotation) {
        this.numeroMultiplo = constraintAnnotation.numero();
    }

	@Override
	public boolean isValid(final Number value, final ConstraintValidatorContext context) {
        boolean valido = true;
        if (value != null){
            var valorDecimal = BigDecimal.valueOf(value.doubleValue());
            var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);
            var resto = valorDecimal.remainder(multiploDecimal);
            valido = BigDecimal.ZERO.compareTo(resto) == 0;
        }
		return valido;
	}

    
}