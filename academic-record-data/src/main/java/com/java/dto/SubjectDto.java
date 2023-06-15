package com.java.dto;

import com.java.enums.Day;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;


public class SubjectDto {

    @NotEmpty(message = "Subject name is required")
    private String subjectName;
    @NotEmpty(message = "Duration(H) is required")
    private Integer duration;
    @NotEmpty(message = "Capacity is required")
    private Integer capacity;
    @NotEmpty(message = "Start date is required")
    private LocalDate startDate;
    @NotEmpty(message = "End date is required")
    private LocalDate endDate;
    @NotEmpty(message = "Start time is required")
    private LocalTime startTime;
    @NotEmpty(message = "End time is required")
    private LocalTime endTime;
    @NotEmpty
    private Day day;
    private Set<String> studentsNames;
    private Set<String> coursesNames;
    private String teacherName;

    public SubjectDto() { /* Empty Constructor*/ }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Set<String> getStudentsNames() {
        return studentsNames;
    }

    public void setStudentsNames(Set<String> studentsNames) {
        this.studentsNames = studentsNames;
    }

    public Set<String> getCoursesNames() {
        return coursesNames;
    }

    public void setCoursesNames(Set<String> coursesNames) {
        this.coursesNames = coursesNames;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

}
