package com.java.dto;

import javax.validation.constraints.NotEmpty;


public class GradeDto {

    @NotEmpty(message = "Mark is required")
    private Double mark;

    private Integer studentId;
    private Integer subjectId;

    public GradeDto() { /* Empty Constructor */}

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }
}
