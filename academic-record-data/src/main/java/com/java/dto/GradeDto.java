package com.java.dto;

import com.java.model.Student;
import com.java.model.Subject;


public class GradeDto {

    private Integer mark;
    private Student student;
    private Subject subject;

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
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
