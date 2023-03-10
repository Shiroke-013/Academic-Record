package com.java.services.impl;

import com.java.model.Professor;
import com.java.services.ProfessorService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProfessorServiceImpl extends AbstractMapService<Professor, Integer> implements ProfessorService {

    @Override
    public Professor findByLastName(String lastName) {
        return null;
    }

    @Override
    public Set<Professor> findAll() {
        return super.findAll();
    }

    @Override
    public Professor findById(Integer id) {
        return super.findById(id);
    }

    @Override
    public Professor save(Professor object) {
        return super.save(object);
    }

    @Override
    public void delete(Professor object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Integer id) {
        super.deleteById(id);
    }

    @Override
    public void update(Professor professor, Integer id) {
        Professor oldProfessor = super.findById(id);
        if (oldProfessor.getId() != null) {
            oldProfessor.setFirstName(professor.getFirstName());
            oldProfessor.setLastName(professor.getLastName());
            oldProfessor.setEmail(professor.getEmail());
            oldProfessor.setPassword(professor.getPassword());
        }
    }
}
