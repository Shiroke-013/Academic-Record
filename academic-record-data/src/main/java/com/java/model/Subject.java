package com.java.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;



import java.sql.Time;
import java.time.LocalTime;



@Entity
@Table(name = "subject")
public class Subject extends BaseEntity{

    private String subjectName;
    private Time duration;
    private Integer capacity;
    private LocalTime startDate;
    private LocalTime endDate;
    private Teacher teacher;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public LocalTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalTime startDate) {
        this.startDate = startDate;
    }

    public LocalTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalTime endDate) {
        this.endDate = endDate;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
