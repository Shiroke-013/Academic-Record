package com.java.service.impl;

import com.java.dto.ProfessorDto;
import com.java.model.Professor;
import com.java.persistence.ProfessorPersistence;
import com.java.service.ExceptionService;
import com.java.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    @Autowired
    private ProfessorPersistence professorPersistence;

    @Override
    public Professor save(ProfessorDto professorDto) throws ExceptionService {
        try {
            Integer id = UUID.randomUUID().hashCode();
            Professor professor = new Professor(id, professorDto, new Date());
            professorPersistence.createProfessor(professor);
            return professor;
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Set<Professor> findAll() {
        return null;
    }

    @Override
    public Professor findById(Integer id) {
        return null;
    }

    @Override
    public void delete() {
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void update(Professor professor, Integer id) {

    }
}
