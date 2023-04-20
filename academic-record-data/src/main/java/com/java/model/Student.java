package com.java.model;

import com.java.dto.StudentDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student extends User {

    private Course course;



    public Student(String firstName, String lastname, String email, String password){
        this.setFirstName(firstName);
        this.setLastName(lastname);
        this.setEmail(email);
        this.setPassword(password);
        this.setCreatedAt(new Date());
    }

    public Student(StudentDto studentDto){
        this.setFirstName(studentDto.getFirstName());
        this.setLastName(studentDto.getLastName());
        this.setEmail(studentDto.getEmail());
        this.setPassword(studentDto.getPassword());
        this.setCreatedAt(new Date());
    }

}
