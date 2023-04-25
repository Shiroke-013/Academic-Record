package com.java.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "grade")
public class Grade extends BaseEntity{

    private Integer calification;
    private Student student;
    private Subject subject;

}
