package com.java.service;

import com.java.dto.GradeDto;

import java.util.Set;

public interface GradeService<T> {

    T save(GradeDto gradeDto) throws ExceptionService;

    T findById(Integer id) throws ExceptionService;

    Set<T> findAll() throws ExceptionService;

    void delete() throws ExceptionService;

    void deleteById(Integer id) throws ExceptionService;

    void update(GradeDto gradeDto, Integer id) throws ExceptionService;

}
