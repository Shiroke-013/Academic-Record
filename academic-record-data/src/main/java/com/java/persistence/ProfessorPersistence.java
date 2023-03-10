package com.java.persistence;

import com.java.dto.ProfessorDto;
import com.java.model.Professor;

import java.util.Set;

public interface ProfessorPersistence {

    void createProfessor(Professor professor);

    Set<Professor> getAllProfessor();

    Professor findProfessorById(Integer id);

    void deleteProfessor(Integer id);

    void deleteAllProfessor();

    void updateProfessor(ProfessorDto professorDto, Integer id);

}
