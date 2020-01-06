package com.github.nicolasholanda.debt.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product")
public class Product extends BaseEntity<Integer> {

    @JsonProperty("name")
    @Column(name = "name", nullable = false, unique = true)
    @Size(min = 3, max = 100, message = "{product.name.size}")
    private String name;

    @JsonProperty("price")
    @Column(name = "price", nullable = false)
    @Positive(message = "{product.price.positive}")
    private float price;

    @JsonProperty("description")
    @Column(name = "description", nullable = false)
    @Size(min = 3, max = 250, message = "{product.description.size}")
    private String description;

    @JsonProperty("available")
    @Column(name = "available", nullable = false)
    private boolean available = true;

    public Product() {
    }

    public Product(String name, float price, String description, boolean available) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public boolean getAvailable() {
        return available;
    }
}
