package com.java.model;

import com.java.dto.StudentDto;
import jakarta.persistence.*;

import java.util.List;


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

    public Student (String firstName, String lastName, String email, String password){
        super();
    }

    public Student(StudentDto studentDto){
        super.setId(studentDto.getId());
        super.setFirstName(studentDto.getFirstName());
        super.setLastName(studentDto.getLastName());
        super.setEmail(studentDto.getEmail());
        super.setPassword(studentDto.getPassword());
    }

    public Student() {

    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
