package com.java.dto;

import com.java.model.BaseEntity;
import com.java.model.Student;
import com.java.model.Subject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class GradeDto extends BaseEntity implements Serializable {

    private Integer calification;
    private Student student;
    private Subject subject;

}
