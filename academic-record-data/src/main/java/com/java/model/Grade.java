package com.java.model;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Grade extends BaseEntity{

    private Integer calification;
    private Student student;
    private Subject subject;

}
