package com.java.persistence;

import com.java.model.Teacher;

import java.util.Collection;

public interface TeacherPersistence {

    void create(Teacher teacher) throws ExceptionPersistence;

    Collection<Teacher> getAll() throws ExceptionPersistence;

    Teacher findById(Integer id) throws ExceptionPersistence;

    void deleteById(Integer id) throws ExceptionPersistence;

    void delete() throws ExceptionPersistence;

    void update(Teacher teacher, Integer id) throws ExceptionPersistence;

}
