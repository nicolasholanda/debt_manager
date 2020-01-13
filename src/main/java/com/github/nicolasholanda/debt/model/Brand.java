package com.github.nicolasholanda.debt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static java.util.Collections.emptyList;
import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "brand")
public class Brand extends BaseEntity<Integer> {

    @JsonProperty("name")
    @Column(name = "name")
    @Size(min = 1, max = 120, message = "{brand.name.size}")
    @NotNull(message = "{brand.name.notnull}")
    private String name;

    @JsonProperty("description")
    @Column(name = "description")
    @Size(max = 130, message = "{brand.description.size}")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "brand", cascade = ALL)
    private List<Product> products = emptyList();

    public Brand() {
    }

    public Brand(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
