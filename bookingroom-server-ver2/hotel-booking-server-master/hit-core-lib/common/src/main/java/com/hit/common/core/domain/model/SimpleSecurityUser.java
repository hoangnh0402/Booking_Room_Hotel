package com.hit.common.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleSecurityUser {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private List<String> authorities;

}
