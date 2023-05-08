package com.java.persistence;

import com.java.model.Course;

import java.util.Set;

public interface CoursePersistence {

    void create(Course course) throws ExceptionPersistence;

    Set<Course> findAll() throws ExceptionPersistence;

    Course findById(Integer id) throws ExceptionPersistence;

    void delete(Integer id) throws ExceptionPersistence;

    void deleteAll() throws ExceptionPersistence;

    void update(Course course) throws ExceptionPersistence;
}
