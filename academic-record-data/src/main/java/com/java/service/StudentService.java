package com.java.service;

import com.java.dto.StudentDto;

import java.util.Collection;

public interface StudentService<T> {

    void save(StudentDto studentDto) throws ExceptionService;

    T findById(Integer id) throws ExceptionService;

    Collection<T> findAll() throws ExceptionService;

    boolean deleteById(Integer id) throws ExceptionService;

    void deleteAll() throws ExceptionService;

    void update(StudentDto studentDto, Integer id) throws ExceptionService;

    void registerIntoCourse(Integer studentId, Integer courseId) throws ExceptionService;

    void registerIntoSubject(Integer studentId, Integer subjectId) throws ExceptionService;

    T findCourse(Integer id) throws ExceptionService;

    Collection<T> findAllSubjects(Integer id) throws ExceptionService;

}
