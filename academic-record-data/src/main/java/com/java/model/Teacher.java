package com.java.model;

import com.java.dto.TeacherDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "teacher")
public class Teacher extends User {

    public Teacher(TeacherDto teacherDto, Date createdAt) {
        this.setFirstName(teacherDto.getFirstName());
        this.setLastName(teacherDto.getLastName());
        this.setEmail(teacherDto.getEmail());
        this.setPassword(teacherDto.getPassword());
        this.setCreatedAt(createdAt);
    }

    public Teacher(String firstName, String lastname, String email, String password){
        this.setFirstName(firstName);
        this.setLastName(lastname);
        this.setEmail(email);
        this.setPassword(password);
        this.setCreatedAt(new Date());
    }

    public Teacher() {

    }

}
