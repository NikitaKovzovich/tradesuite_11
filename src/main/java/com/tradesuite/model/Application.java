package com.tradesuite.model;

import com.tradesuite.controllers.main.Main;
import com.tradesuite.model.enums.ApplicationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Application implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private int quantity;
    private float price;
    private String contact;
    private String address;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.NOT_CONFIRMED;

    @ManyToOne
    private Product product;
    @ManyToOne
    private AppUser owner;

    public Application(int quantity, String contact, String address, Product product, AppUser owner) {
        this.quantity = quantity;
        this.contact = contact;
        this.address = address;
        this.product = product;
        this.owner = owner;
        this.price = Main.round(product.getPrice() * quantity);
    }
}