package com.java.service;

import com.java.dto.GradeDto;

import java.util.Collection;

public interface GradeService<T> {

    void save(GradeDto gradeDto) throws ExceptionService;

    T findById(Integer id) throws ExceptionService;

    Collection<T> findAll() throws ExceptionService;

    void deleteAll() throws ExceptionService;

    void deleteById(Integer id) throws ExceptionService;

    void update(GradeDto gradeDto, Integer id) throws ExceptionService;

}
