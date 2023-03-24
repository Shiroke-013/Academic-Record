package com.java.service;

import com.java.dto.ProfessorDto;

import java.util.Collection;

public interface ProfessorService<T> {

    T save(ProfessorDto professorDto) throws ExceptionService;

    T findById(Integer id) throws ExceptionService;

    Collection<T> findAll() throws ExceptionService;

    void delete() throws ExceptionService;

    void deleteById(Integer id) throws ExceptionService;

    void update(ProfessorDto professorDto, Integer id) throws ExceptionService;

}
