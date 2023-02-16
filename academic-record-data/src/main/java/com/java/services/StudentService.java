package com.java.services;

import com.java.model.Student;
import java.util.Set;

public interface StudentService {

    Student findByLastName(String lastName);
    Student findById(Long id);
    Student save(Student student);
    Set<Student> findAll();
}
