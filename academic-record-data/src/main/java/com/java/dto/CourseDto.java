package com.java.dto;

import com.java.model.BaseEntity;

import java.io.Serializable;
import java.time.LocalDate;

public class CourseDto extends BaseEntity implements Serializable {

    private String courseName;
    private Integer numberOfStudents;
    private LocalDate courseStart;
    private LocalDate courseEnd;

}
