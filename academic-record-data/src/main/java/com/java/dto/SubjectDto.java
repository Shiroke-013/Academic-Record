package com.java.dto;

import com.java.model.BaseEntity;
import com.java.model.Teacher;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalTime;


@Getter
@Setter
public class SubjectDto extends BaseEntity {

    private String subjectName;
    private Time duration;
    private Integer capacity;
    private LocalTime startDate;
    private LocalTime endDate;
    private Teacher teacher;

}
