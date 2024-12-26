package com.hit.common.core.tracker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldChange {

    private String fieldName;

    private String oldValue;

    private String newValue;

}
