package com.java.services;

import com.java.model.Professor;
import java.util.Set;

public interface ProfessorService<T> {

    T save(Professor professor) throws ExceptionService;

    Set<Professor> findAll();

    Professor findById(Integer id);

    void delete();

    void deleteById(Integer id);

    void update(Professor professor, Integer id);

}
