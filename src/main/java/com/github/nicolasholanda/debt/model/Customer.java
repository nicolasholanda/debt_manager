package com.github.nicolasholanda.debt.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customer")
public class Customer extends BaseEntity<Integer> {

    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    @Size(min = 3, max = 150, message = "{customer.name.size}")
    @NotNull(message = "{customer.name.notnull}")
    private String name;

    @JsonProperty("cpf")
    @Column(name = "cpf")
    @Pattern(regexp = "^[0-9]{3}[.][0-9]{3}[.][0-9]{3}[-][0-9]{2}$", message = "{customer.cpf.regex}")
    private String cpf;

    @JsonProperty("phone_number")
    @Column(name = "phone_number")
    @Pattern(regexp = "^[(][0-9]{2}[)][0-9]{5}[-][0-9]{4}$", message = "{customer.phone_number.regex}")
    private String phone_number;

    @JsonProperty("email")
    @Column(name = "email")
    @Pattern(regexp = "^[a-z0-9._]+@[a-z0-9]+.[a-z]+.([a-z]+)?$", message = "{customer.email.regex}")
    private String email;

    public Customer() {
    }

    public Customer(String name, String cpf, String phone_number, String email) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
