package com.java.persistence.impl;

import com.java.dto.ProfessorDto;
import com.java.model.Professor;
import com.java.persistence.ProfessorPersistence;
import com.java.persistence.repository.ProfessorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class ProfessorPersistenceImpl implements ProfessorPersistence {

    @Autowired
    ProfessorRepository professorRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void createProfessor(Professor professor) {

    }

    @Override
    public Set<Professor> getAllProfessor() {
        return null;
    }

    @Override
    public Professor findProfessorById(Integer id) {
        return null;
    }

    @Override
    public void deleteProfessor(Integer id) {

    }

    @Override
    public void deleteAllProfessor() {

    }

    @Override
    public void updateProfessor(ProfessorDto professorDto, Integer id) {

    }
}
