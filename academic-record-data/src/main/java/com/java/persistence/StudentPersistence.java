package com.java.persistence;

import com.java.model.Student;
import com.java.model.Subject;

import java.util.Collection;

public interface StudentPersistence {

    void create(Student student) throws ExceptionPersistence;

    Student findById(Integer id) throws ExceptionPersistence;

    Collection<Student> findAll() throws ExceptionPersistence;

    void delete(Integer id) throws ExceptionPersistence;

    void deleteAll() throws ExceptionPersistence;

    void update(Student student) throws ExceptionPersistence;

    //Student findByLastName(String lastName) throws ExceptionPersistence;

    Collection<Subject> findAllSubjects(Integer id );

}
