package com.java.persistence;

import com.java.dto.ProfessorDto;
import com.java.model.Professor;

import java.util.Collection;

public interface ProfessorPersistence {

    void create(Professor professor) throws ExceptionPersistence;

    Collection<Professor> getAll() throws ExceptionPersistence;

    Professor findById(Integer id) throws ExceptionPersistence;

    void deleteById(Integer id) throws ExceptionPersistence;

    void delete() throws ExceptionPersistence;

    void update(Professor professor, Integer id) throws ExceptionPersistence;

}
