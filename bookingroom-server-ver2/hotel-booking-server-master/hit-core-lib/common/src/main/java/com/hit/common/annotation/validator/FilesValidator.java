package com.hit.common.annotation.validator;

import com.hit.common.annotation.ValidFiles;
import com.hit.common.core.constants.enums.FileExtensionEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class FilesValidator implements ConstraintValidator<ValidFiles, List<MultipartFile>> {

    private FileExtensionEnum[] extensions;

    @Override
    public void initialize(ValidFiles constraintAnnotation) {
        this.extensions = constraintAnnotation.extensions();
    }

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;
        if (CollectionUtils.isNotEmpty(files)) {
            for (MultipartFile file : files) {
                String contentType = file.getContentType();
                String fileExtension = contentType != null ? contentType.substring(contentType.lastIndexOf("/") + 1) : null;
                if (!isSupportedExtension(fileExtension)) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    private boolean isSupportedExtension(String fileExtension) {
        return Arrays.stream(extensions).noneMatch(file -> Arrays.asList(file.getExtension()).contains(fileExtension));
    }
}
