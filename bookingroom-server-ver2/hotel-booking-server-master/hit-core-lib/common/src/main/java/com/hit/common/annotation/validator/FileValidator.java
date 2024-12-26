package com.hit.common.annotation.validator;

import com.hit.common.annotation.ValidFile;
import com.hit.common.core.constants.enums.FileExtensionEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private FileExtensionEnum[] extensions;

    @Override
    public void initialize(ValidFile constraintAnnotation) {
        this.extensions = constraintAnnotation.extensions();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;
        if (ObjectUtils.isNotEmpty(file)) {
            String contentType = file.getContentType();
            String fileExtension = contentType != null ? contentType.substring(contentType.lastIndexOf("/") + 1) : null;
            if (!isSupportedExtension(fileExtension)) {
                result = false;
            }
        }
        return result;
    }

    private boolean isSupportedExtension(String fileExtension) {
        return Arrays.stream(extensions).noneMatch(file -> Arrays.asList(file.getExtension()).contains(fileExtension));
    }

}
