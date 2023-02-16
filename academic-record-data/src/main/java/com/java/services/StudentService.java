package com.java.services;

import com.java.model.Student;
import java.util.Set;

public interface StudentService extends CrudService<Student, Integer> {

    Student findByLastName(String lastName);

}
