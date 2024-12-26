package com.hit.common.annotation.validator;

import com.hit.common.annotation.ValidDateTime;
import com.hit.common.utils.TimeUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class DateTimeValidator implements ConstraintValidator<ValidDateTime, String> {

    private String pattern;

    private boolean isEmpty;

    @Override
    public void initialize(ValidDateTime constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
        this.isEmpty = constraintAnnotation.isEmpty();
    }

    @Override
    public boolean isValid(String dateTime, ConstraintValidatorContext constraintValidatorContext) {
        if (isEmpty && ObjectUtils.isEmpty(dateTime)) {
            return true;
        }
        try {
            return !ObjectUtils.isEmpty(dateTime) && TimeUtils.parseToLocalDateTime(dateTime, pattern) != null;
        } catch (Exception ex) {
            return false;
        }
    }
}
