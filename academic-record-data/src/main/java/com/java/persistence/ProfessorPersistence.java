package com.java.persistence;

import com.java.dto.ProfessorDto;
import com.java.model.Professor;

import java.util.Collection;

public interface ProfessorPersistence {

    void createProfessor(Professor professor);

    Collection<Professor> getAllProfessor();

    Professor findProfessorById(Integer id);

    void deleteProfessor(Integer id);

    void deleteAllProfessor();

    void updateProfessor(ProfessorDto professorDto, Integer id);

}
