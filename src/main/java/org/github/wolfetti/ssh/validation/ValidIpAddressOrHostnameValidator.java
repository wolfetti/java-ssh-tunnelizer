package org.github.wolfetti.ssh.validation;

import com.google.common.net.InetAddresses;
import com.google.common.net.InternetDomainName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class ValidIpAddressOrHostnameValidator implements ConstraintValidator<ValidIpAddressOrHostname, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StringUtils.isBlank(value)){
            return true;
        }
        
        return InetAddresses.isInetAddress(value)
            || InternetDomainName.isValid(value)
        ;
    }
}
