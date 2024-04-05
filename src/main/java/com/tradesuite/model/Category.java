package com.tradesuite.model;

import com.tradesuite.controllers.main.Main;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public void set(String name) {
        this.name = name;
    }

    public float getIncome() {
        return Main.round(products.stream().reduce(0f, (i, product) -> i + product.getIncome(), Float::sum));
    }

    public int getIncomeQuantity() {
        return products.stream().reduce(0, (i, product) -> i + product.getIncomeQuantity(), Integer::sum);
    }
}