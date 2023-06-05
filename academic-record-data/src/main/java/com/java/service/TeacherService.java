package com.java.service;

import com.java.dto.GradeDto;
import com.java.dto.TeacherDto;

import java.util.Collection;

public interface TeacherService<T> {

    T save(TeacherDto teacherDto) throws ExceptionService;

    T findById(Integer id) throws ExceptionService;

    Collection<T> findAll() throws ExceptionService;

    void delete() throws ExceptionService;

    void deleteById(Integer id) throws ExceptionService;

    void update(TeacherDto teacherDto, Integer id) throws ExceptionService;

    T findCourse(Integer id) throws ExceptionService;

    T findSubjects(Integer id) throws ExceptionService;

    void addGrade(GradeDto gradeDto, Integer subjectId, Integer studentId) throws ExceptionService;

}
