package com.java.model;

import jakarta.persistence.Entity;

import java.util.Set;


@Entity
public class Professor extends User {

    private Set<Subject> subjects;

    public Professor(String name, String lastname, Set<Subject> subjects) {
        this.setFirsName(name);
        this.setLastName(lastname);
        this.subjects = subjects;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
