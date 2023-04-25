package com.java.persistence;

import com.java.model.Student;
import com.java.model.Subject;

import java.util.Collection;

public interface StudentPersistence {

    void create(Student student) throws ExceptionPersistence;

    Collection<Student> getAll() throws ExceptionPersistence;

    Student findById(Integer id) throws ExceptionPersistence;

    void delete(Integer id) throws ExceptionPersistence;

    void deleteAll() throws ExceptionPersistence;

    void update(Student student) throws ExceptionPersistence;

    Student findByLastName(String lastName);

    Collection<Subject> findAllSubjects(Integer id );

}
