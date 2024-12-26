package com.hit.common.core.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hit.common.config.locale.Translator;
import com.hit.common.utils.TimeUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseStatus {

    private String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeUtils.DATE_TIME_PATTERN)
    private LocalDateTime responseTime;

    public ResponseStatus(String code, Object message) {
        this.code = code;
        this.message = message;
        this.responseTime = LocalDateTime.now();
    }

    public ResponseStatus(String code, String[] params, boolean setMessageImplicitly) {
        this.setStatus(code, params, setMessageImplicitly);
    }

    public void setStatus(String code, String[] params, boolean setMessageImplicitly) {
        this.code = code;
        if (setMessageImplicitly) {
            this.message = Translator.toLocale(code, params);
        }
        this.responseTime = LocalDateTime.now();
    }
}
