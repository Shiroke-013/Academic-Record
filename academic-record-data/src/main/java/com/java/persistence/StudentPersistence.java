package com.java.persistence;

import com.java.dto.StudentDto;
import com.java.model.Student;

import java.util.Set;

public interface StudentPersistence {

    void createStudent(Student student);

    Set<Student> getAllStudent();

    Student findStudentById(Integer id);

    void deleteStudent(Integer id);

    void deleteAllStudent();

    void updateStudent(StudentDto studentDto, Integer id);
}
