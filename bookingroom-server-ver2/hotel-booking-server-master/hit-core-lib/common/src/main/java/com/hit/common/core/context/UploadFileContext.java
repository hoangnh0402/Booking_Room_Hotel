package com.hit.common.core.context;

import com.hit.common.utils.UploadFileUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UploadFileContext {

    @Getter
    @Setter
    private static UploadFileUtils uploadFileUtils;

    @Autowired
    UploadFileContext(UploadFileUtils uploadFileUtils) {
        setUploadFileUtils(uploadFileUtils);
    }

}
