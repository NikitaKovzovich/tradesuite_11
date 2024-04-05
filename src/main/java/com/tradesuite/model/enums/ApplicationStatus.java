package com.tradesuite.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationStatus {
    NOT_CONFIRMED("Не подтверждено"),
    ASSEMBLY("В сборке"),
    PACKED("Упаковывается"),
    IN_DELIVERY("В доставке"),
    DELIVERED("Доставлено"),
    ;

    private final String name;
}