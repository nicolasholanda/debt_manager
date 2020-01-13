package com.github.nicolasholanda.debt.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product")
public class Product extends BaseEntity<Integer> {

    @JsonProperty("name")
    @Column(name = "name", nullable = false, unique = true)
    @NotNull(message = "{product.name.notnull}")
    @Size(min = 3, max = 300, message = "{product.name.size}")
    private String name;

    @JsonProperty("price")
    @Column(name = "price", nullable = false)
    @Positive(message = "{product.price.positive}")
    private float price;

    @JsonProperty("description")
    @Column(name = "description", nullable = false)
    @NotNull(message = "{product.descriptio.notnull}")
    @Size(min = 3, max = 1000, message = "{product.description.size}")
    private String description;

    @JsonProperty("available")
    @Column(name = "available")
    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "brand_id", foreignKey = @ForeignKey(name = "fk_brand"))
    @JsonProperty("brand")
    @NotNull(message = "{product.brand.notnull}")
    private Brand brand;

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
