package com.java.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "teacher")
public class Teacher extends User {

    @OneToMany(mappedBy = "teacher")
    private Set<Subject> subjects = new HashSet<>();

    public Teacher(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

    public Teacher() { super(); }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
