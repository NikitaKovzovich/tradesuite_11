package com.tradesuite.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Department {
    ASSEMBLING("Сборка"),
    PACKAGING("Упаковка"),
    DELIVERY("Доставка"),
    ;

    private final String name;
}