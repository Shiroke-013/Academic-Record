package com.java.service;

import com.java.dto.CourseDto;

import java.util.Collection;

public interface CourseService<T> {

    void save(CourseDto teacherDto) throws ExceptionService;

    T findById(Integer id) throws ExceptionService;

    Collection<T> findAll() throws ExceptionService;

    void deleteAll() throws ExceptionService;

    void deleteById(Integer id) throws ExceptionService;

    void update(CourseDto courseDto, Integer id) throws ExceptionService;

    Collection<T> findTeachers(Integer id) throws ExceptionService;

    Collection<T> findStudents(Integer id) throws ExceptionService;

    Collection<T> findSubjects(Integer id) throws ExceptionService;

    void deleteTeacherFromCourse(Integer courseId, Integer teacherId) throws ExceptionService;

    void addTeacher(Integer courseId, Integer teacherId) throws ExceptionService;

    void addSubject(Integer courseId, Integer subjectId) throws ExceptionService;
}
