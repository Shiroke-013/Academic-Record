package com.java.service;

import com.java.dto.StudentDto;

import java.util.Collection;

public interface StudentService<T> {

    void save(StudentDto studentDto) throws ExceptionService;

    T findById(Integer id) throws ExceptionService;

    T findByLastName(String lastname) throws ExceptionService;

    Collection<T> findAll(Integer id) throws ExceptionService;

    void deleteById(Integer id) throws ExceptionService;

    void update(StudentDto studentDto, Integer id) throws ExceptionService;

    T calculateAverage() throws ExceptionService;

    T howMuchToGetInFinalExam() throws ExceptionService;

    void registerIntoCourse() throws ExceptionService;

    void registerIntoSubject() throws ExceptionService;

}
