package com.java.service;

import com.java.dto.ProfessorDto;
import com.java.model.Professor;

import java.util.Set;

public interface ProfessorService<T> {

    T save(ProfessorDto professorDto) throws ExceptionService;

    T findById(Integer id);

    Set<T> findAll();

    void delete();

    void deleteById(Integer id);

    void update(Professor professor, Integer id);

}
