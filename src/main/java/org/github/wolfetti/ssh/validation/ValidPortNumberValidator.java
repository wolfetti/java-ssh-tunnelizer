package org.github.wolfetti.ssh.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPortNumberValidator implements ConstraintValidator<ValidPortNumber, Integer>{

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        
        return value >= 1 && value <= 65535;
    }
}
