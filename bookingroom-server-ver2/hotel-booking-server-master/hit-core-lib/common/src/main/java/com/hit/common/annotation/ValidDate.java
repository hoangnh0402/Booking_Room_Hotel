package com.hit.common.annotation;

import com.hit.common.annotation.validator.DateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Constraint(validatedBy = DateValidator.class)
public @interface ValidDate {

    String message() default "Invalid date";

    String pattern() default "";

    boolean isEmpty() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
