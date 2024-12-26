package com.hit.common.annotation;

import com.hit.common.annotation.validator.FileValidator;
import com.hit.common.core.constants.enums.FileExtensionEnum;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Constraint(validatedBy = {FileValidator.class})
public @interface ValidFile {

    String message() default "Invalid file";

    FileExtensionEnum[] extensions() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
