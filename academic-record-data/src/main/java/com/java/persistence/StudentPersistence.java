package com.java.persistence;

import com.java.model.Student;

import java.util.Collection;
import java.util.Optional;

public interface StudentPersistence {

    void create(Student student) throws ExceptionPersistence;

    Optional<Student> findById(Integer id) throws ExceptionPersistence;

    Collection<Student> findAll() throws ExceptionPersistence;

    void delete(Integer id) throws ExceptionPersistence;

    void deleteAll() throws ExceptionPersistence;

    void update(Student student) throws ExceptionPersistence;

}
