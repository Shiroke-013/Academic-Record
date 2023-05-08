package com.java.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;


@Entity
@Table(name = "student")
public class Student extends User {

    @OneToMany(mappedBy = "student")
    private List<Grade> grades;

    @ManyToMany
    @JoinTable(name = "student_course",
                joinColumns = @JoinColumn(name = "id"),
                inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<Course> courses;

    public Student(String firstName, String lastName, String email, String password, List<Grade> grades, Set<Course> course) {
        super(firstName, lastName, email, password);
        this.grades = grades;
        this.courses = course;
    }

    public Student(){ }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Set<Course> getCourse() {
        return courses;
    }

    public void setCourse(Set<Course> course) {
        this.courses = course;
    }
}
