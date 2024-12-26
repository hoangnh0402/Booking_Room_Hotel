package com.hit.common.core.domain.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum Operator {

    INCLUDE("include"),
    IN("in"),
    NIN("nin"),
    EQUAL("eq"),
    LIKE("like"),
    NOT_EQUAL("ne"),
    GREATER_THAN("gt"),
    LESS_THAN("lt"),
    GREATER_THAN_OR_EQUAL("gte"),
    LESS_THAN_OR_EQUAL("lte"),
    NONE("none");

    private final String value;

    private static final Map<String, Operator> MAPPING_OPERATOR = new HashMap<>();

    static {
        for (Operator operator : Operator.values()) {
            MAPPING_OPERATOR.put(operator.getValue(), operator);
        }
    }

    public static Operator fromOperator(String operator) {
        return MAPPING_OPERATOR.getOrDefault(operator, NONE);
    }
}
