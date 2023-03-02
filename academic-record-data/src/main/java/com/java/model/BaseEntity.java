package com.java.model;

import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
public class BaseEntity implements Serializable {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
