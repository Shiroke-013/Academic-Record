package com.java.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class Subject extends BaseEntity{

    private String subjectName;
    private Time duration;
    private Integer capacity;
    private LocalTime startDate;
    private LocalTime endDate;
    private Professor professor;

}
