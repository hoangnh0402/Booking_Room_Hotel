package com.hit.common.core.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendMailRequest {

    private String to;

    private String subject;

    private String content;

    private Map<String, Object> properties;

}
