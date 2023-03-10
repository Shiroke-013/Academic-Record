package com.java.persistence.impl;

import com.java.dto.ProfessorDto;
import com.java.model.Professor;
import com.java.persistence.ProfessorPersistence;
import com.java.persistence.repository.ProfessorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class ProfessorPersistenceImpl implements ProfessorPersistence {

    @Autowired
    ProfessorRepository professorRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void createProfessor(Professor professor) {
        professorRepository.save(professor);
    }

    @Override
    public Collection<Professor> getAllProfessor() {
        if (professorRepository.count()!=0){
            return professorRepository.findAll();
        }
        return null;
    }

    @Override
    public Professor findProfessorById(Integer id) {
        Professor professor = professorRepository.findById(id).get();
        if (professor != null){
            return professor;
        }
        return null;
    }

    @Override
    public void deleteProfessor(Integer id) {

        Professor professor = professorRepository.getById(id);
        if (professor != null){
            professorRepository.deleteById(id);
        }
    }

    @Override
    public void deleteAllProfessor() {
        if (professorRepository.count()!=0){
            professorRepository.deleteAll();
        }
    }

    @Override
    public void updateProfessor(ProfessorDto professorDto, Integer id) {

    }
}
