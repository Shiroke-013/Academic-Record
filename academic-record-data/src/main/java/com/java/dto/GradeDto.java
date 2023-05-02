package com.java.dto;

import com.java.model.Student;
import com.java.model.Subject;


public class GradeDto {

    private Integer calification;
    private Student student;
    private Subject subject;

    public Integer getCalification() {
        return calification;
    }

    public void setCalification(Integer calification) {
        this.calification = calification;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
