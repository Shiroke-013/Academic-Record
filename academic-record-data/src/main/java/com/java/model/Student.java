package com.java.model;

import com.java.dto.StudentDto;
import com.sun.tools.javac.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student extends User {

    @OneToMany(mappedBy = "student")
    private List<Grade> grades;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "stu_course",
        joinColumns =
                { @JoinColumn(name = "student_id", referencedColumnName = "id") },
        inverseJoinColumns =
                { @JoinColumn(name = "course_id", referencedColumnName = "id") })
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
