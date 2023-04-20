package com.java.dto;

import com.java.model.BaseEntity;
import com.java.model.Professor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;


@Getter
@Setter
public class SubjectDto extends BaseEntity implements Serializable {

    private String subjectName;
    private Time duration;
    private Integer capacity;
    private LocalTime startDate;
    private LocalTime endDate;
    private Professor professor;

}
