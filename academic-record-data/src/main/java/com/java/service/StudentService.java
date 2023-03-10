package com.java.service;

import com.java.model.Student;

public interface StudentService extends CrudService<Student, Integer> {

    Student findByLastName(String lastName);

}
