package com.java.model;

import jakarta.persistence.Entity;

import java.util.Set;

@Entity
public class Student extends User {

    private Course course;

    //private Set<Subject> subjects;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    //public Set<Subject> getSubjects() {
      //  return subjects;
    //}

    //public void setSubjects(Set<Subject> subjects) {
      //  this.subjects = subjects;
    //}
}
