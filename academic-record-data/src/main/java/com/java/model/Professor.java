package com.java.model;

import com.java.dto.ProfessorDto;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.Date;
import java.util.Set;


@Entity
public class Professor extends User {

    public Professor(Integer id, ProfessorDto professorDto, Date createdAt) {
        this.setId(id);
        this.setFirstName(professorDto.getFirstName());
        this.setLastName(professorDto.getLastName());
        this.setEmail(professorDto.getEmail());
        this.setPassword(professorDto.getPassword());
        this.setCreatedAt(createdAt);
    }

    //@OneToMany
    //private Set<Subject> subjects;

    //public Set<Subject> getSubjects() {
      //  return subjects;
    //}

   // public void setSubjects(Set<Subject> subjects) {
        //this.subjects = subjects;
    //}
}
