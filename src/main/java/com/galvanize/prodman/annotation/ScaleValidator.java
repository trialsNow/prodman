package com.galvanize.prodman.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class ScaleValidator implements ConstraintValidator<BigDecimalScale, Object> {
    BigDecimalScale annotation;

    @Override
    public void initialize(BigDecimalScale constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (object != null) {
            BigDecimal value = (BigDecimal) object;
            isValid = value.scale() <= annotation.scale();
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("max scale: " + annotation.scale()).addConstraintViolation();
        }
        return isValid;
    }
}
