package com.java.dto;


import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Set;


public class CourseDto {

    @NotEmpty(message = "Course name is required")
    private String courseName;
    private Integer numberOfStudents;
    @NotEmpty(message = "Start date for course is required")
    private LocalDate courseStart;
    @NotEmpty(message = "End date for course is required")
    private LocalDate courseEnd;

    private Set<String> studentsNames;
    private Set<String> subjectsNames;
    private Set<String> teachersNames;

    public CourseDto() { /*empty constructor*/ }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(Integer numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public LocalDate getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(LocalDate courseStart) {
        this.courseStart = courseStart;
    }

    public LocalDate getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(LocalDate courseEnd) {
        this.courseEnd = courseEnd;
    }

    public Set<String> getStudentsNames() {
        return studentsNames;
    }

    public void setStudentsNames(Set<String> studentsNames) {
        this.studentsNames = studentsNames;
    }

    public Set<String> getSubjectsNames() {
        return subjectsNames;
    }

    public void setSubjectsNames(Set<String> subjectsNames) {
        this.subjectsNames = subjectsNames;
    }

    public Set<String> getTeachersNames() {
        return teachersNames;
    }

    public void setTeachersNames(Set<String> teachersNames) {
        this.teachersNames = teachersNames;
    }
}
