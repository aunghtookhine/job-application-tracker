package com.buildmyself.job_application_tracker.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, String> {

    private Set<String> allowedValues;
    @Override
    public void initialize(ValueOfEnum constraintAnnotation) {
        allowedValues = Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (allowedValues.contains(s)) {
            return true;
        }
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate("must be one of: " + String.join(", ", allowedValues))
                .addConstraintViolation();
        return false;
    }
}
