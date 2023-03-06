package com.java.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.Set;


@Entity
public class Professor extends User {

    //@OneToMany
    //private Set<Subject> subjects;

    //public Set<Subject> getSubjects() {
      //  return subjects;
    //}

   // public void setSubjects(Set<Subject> subjects) {
        //this.subjects = subjects;
    //}
}
