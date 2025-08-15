package org.example.hwspringdatajpa.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomerProperty {
    ID("id"),NAME("name");
    private final String fieldName;
}
