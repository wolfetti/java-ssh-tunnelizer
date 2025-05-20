package org.github.wolfetti.jssht.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidIpAddressValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface ValidIpAddress {

    String message() default "Ip or hostname is invalid: ${validatedValue}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
