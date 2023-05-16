package com.java.persistence;

import com.java.model.Subject;

import java.util.Collection;

public interface SubjectPersistence {

    void create(Subject subject) throws ExceptionPersistence;

    Collection<Subject> findAll() throws ExceptionPersistence;

    Subject findById(Integer id) throws ExceptionPersistence;

    void delete(Integer id) throws ExceptionPersistence;

    void deleteAll() throws ExceptionPersistence;

    void update(Subject subject) throws ExceptionPersistence;

}
