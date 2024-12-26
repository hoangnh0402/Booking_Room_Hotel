package com.hit.common.annotation.validator;

import com.hit.common.annotation.ValidDate;
import com.hit.common.utils.TimeUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

    private String pattern;

    private boolean isEmpty;

    @Override
    public void initialize(ValidDate constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
        this.isEmpty = constraintAnnotation.isEmpty();
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        if (isEmpty && ObjectUtils.isEmpty(date)) {
            return true;
        }
        try {
            return !ObjectUtils.isEmpty(date) && TimeUtils.parseToLocalDate(date, pattern) != null;
        } catch (Exception ex) {
            return false;
        }
    }
}
