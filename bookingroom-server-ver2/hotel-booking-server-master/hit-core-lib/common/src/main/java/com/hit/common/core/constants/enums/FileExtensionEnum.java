package com.hit.common.core.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Define the supported file extensions in the project
 */
@Getter
@AllArgsConstructor
public enum FileExtensionEnum {
    DOCUMENT(new String[]{"txt", "doc", "pdf", "ppt", "pps", "xlsx", "xls", "docx"}),
    VIDEO(new String[]{"mp4", "mpg", "mpe", "mpeg", "webm", "mov", "m4v"}),
    IMAGE(new String[]{"png", "jpg", "jpeg", "webp", "gif"});

    private final String[] extension;
}
