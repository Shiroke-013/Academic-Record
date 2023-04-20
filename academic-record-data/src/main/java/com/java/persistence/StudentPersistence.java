package com.java.persistence;

import com.java.dto.StudentDto;
import com.java.model.Student;

import java.util.Set;

public interface StudentPersistence {

    void create(Student student);

    Set<Student> getAll();

    Student findById(Integer id);

    void delete(Integer id);

    void deleteAll();

    void update(StudentDto studentDto, Integer id);
}
