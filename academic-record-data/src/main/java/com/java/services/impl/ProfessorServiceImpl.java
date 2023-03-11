package com.java.services.impl;

import com.java.model.Professor;
import com.java.services.ExceptionService;
import com.java.services.ProfessorService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    @Override
    public Professor save(Professor professor) throws ExceptionService {
        try {

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
        Professor oldProfessor = .findById(id);
        if (oldProfessor.getId() != null) {
            oldProfessor.setFirstName(professor.getFirstName());
            oldProfessor.setLastName(professor.getLastName());
            oldProfessor.setEmail(professor.getEmail());
            oldProfessor.setPassword(professor.getPassword());
        }
    }
}
