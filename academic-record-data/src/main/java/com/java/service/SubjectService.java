package com.java.service;

import com.java.dto.SubjectDto;

import java.util.Collection;

public interface SubjectService<T> {

    void save(SubjectDto subjecDto) throws ExceptionService;

    T findById(Integer id) throws ExceptionService;

    Collection<T> findAll() throws ExceptionService;

    void deleteAll() throws ExceptionService;

    void deleteById(Integer id) throws ExceptionService;

    void update(SubjectDto subjectDto, Integer id) throws ExceptionService;

    void addTeacher(Integer subjectId, Integer teacherId) throws ExceptionService;

    Collection<T> findStudents(Integer id) throws ExceptionService;

    void deleteTeacherFromSubject(Integer subjectId, Integer teacherId) throws ExceptionService;
}
