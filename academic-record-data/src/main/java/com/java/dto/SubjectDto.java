package com.java.dto;

import com.java.model.Teacher;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Set;


public class SubjectDto {

    private String subjectName;
    private Time duration;
    private Integer capacity;
    private LocalTime startDate;
    private LocalTime endDate;
    private Set<StudentDto> students;
    private Set<CourseDto> courses;
    private Teacher teacher;
    private Set<GradeDto> grades;

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

    public Set<StudentDto> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentDto> students) {
        this.students = students;
    }

    public Set<CourseDto> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseDto> courses) {
        this.courses = courses;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<GradeDto> getGrades() {
        return grades;
    }

    public void setGrades(Set<GradeDto> grades) {
        this.grades = grades;
    }
}
