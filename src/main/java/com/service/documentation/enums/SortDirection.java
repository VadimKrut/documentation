package com.service.documentation.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Sorting direction for query results")
public enum SortDirection {

    ASC("ASC"),

    DESC("DESC");

    private final String value;

    SortDirection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}