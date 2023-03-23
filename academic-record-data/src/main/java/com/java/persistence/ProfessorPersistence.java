package com.java.persistence;

import com.java.dto.ProfessorDto;
import com.java.model.Professor;

import java.util.Collection;

public interface ProfessorPersistence {

    void create(Professor professor) throws ExceptionPersistence;

    Collection<Professor> getAll() throws ExceptionPersistence;

    Professor findById(Integer id) throws ExceptionPersistence;

    void delete(Integer id) throws ExceptionPersistence;

    void deleteAll() throws ExceptionPersistence;

    void update(ProfessorDto professorDto, Integer id) throws ExceptionPersistence;

}
