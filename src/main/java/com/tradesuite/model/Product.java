package com.tradesuite.model;

import com.tradesuite.controllers.main.Main;
import com.tradesuite.model.enums.ApplicationStatus;
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
public class Product implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private String photo;
    private String date;
    private int term;
    private String origin;
    private String firm;
    private float price;

    @Column(length = 5000)
    private String description;

    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Application> applications = new ArrayList<>();

    public Product(String name, String date, int term, String origin, String firm, float price, String description, Category category) {
        this.name = name;
        this.date = date;
        this.term = term;
        this.origin = origin;
        this.firm = firm;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public void set(String name, String date, int term, String origin, String firm, float price, String description, Category category) {
        this.name = name;
        this.date = date;
        this.term = term;
        this.origin = origin;
        this.firm = firm;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public float getIncome() {
        return Main.round(applications.stream().reduce(0f, (i, application) -> {
            if (application.getStatus() == ApplicationStatus.DELIVERED) return i + application.getPrice();
            return i;
        }, Float::sum));
    }

    public int getIncomeQuantity() {
        return applications.stream().reduce(0, (i, application) -> {
            if (application.getStatus() == ApplicationStatus.DELIVERED) return i + application.getQuantity();
            return i;
        }, Integer::sum);
    }
}