package com.java.service;

import com.java.dto.ProfessorDto;
import com.java.model.Professor;

import java.util.Set;

public interface ProfessorService<T> {

    T save(ProfessorDto professorDto) throws ExceptionService;

    T findById(Integer id) throws ExceptionService;

    Set<T> findAll() throws ExceptionService;

    void delete() throws ExceptionService;

    void deleteById(Integer id) throws ExceptionService;

    void update(Professor professor, Integer id) throws ExceptionService;

}
