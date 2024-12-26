package com.hit.common.annotation.validator;

import com.hit.common.annotation.ValidFileImage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class FileImageValidator implements ConstraintValidator<ValidFileImage, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;
        if (file != null) {
            String contentType = file.getContentType();
            if (!this.isSupportedContentType(Objects.requireNonNull(contentType))) {
                result = false;
            }
        }
        return result;
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg")
                || contentType.equals("image/webp")
                || contentType.equals("image/gif");
    }

}
