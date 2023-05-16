package com.java.persistence;

import com.java.model.Grade;

import java.util.Collection;

public interface GradePersistence {

    void create(Grade grade) throws ExceptionPersistence;

    Collection<Grade> findAll() throws ExceptionPersistence;

    Grade findById(Integer id) throws ExceptionPersistence;

    void delete(Integer id) throws ExceptionPersistence;

    void deleteAll() throws ExceptionPersistence;

    void update(Grade grade) throws ExceptionPersistence;
}
