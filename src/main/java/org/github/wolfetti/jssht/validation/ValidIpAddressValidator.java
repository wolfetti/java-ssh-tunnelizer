package org.github.wolfetti.jssht.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;

public class ValidIpAddressValidator implements ConstraintValidator<ValidIpAddress, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StringUtils.isBlank(value)){
            return true;
        }
        
        return InetAddressValidator.getInstance().isValid(value);
    }
}
