package com.github.nicolasholanda.debt.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@MappedSuperclass
public abstract class BaseEntity<I extends Number> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private I id;

    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
