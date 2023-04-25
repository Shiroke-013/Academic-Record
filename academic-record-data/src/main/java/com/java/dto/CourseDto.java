package com.java.dto;

import com.java.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class CourseDto extends BaseEntity {

    private String courseName;
    private Integer numberOfStudents;
    private LocalDate courseStart;
    private LocalDate courseEnd;

}
