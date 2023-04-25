package com.java.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "course")
public class Course extends BaseEntity{

    private String courseName;
    private Integer numberOfStudents;
    private LocalDate courseStart;
    private LocalDate courseEnd;

}
