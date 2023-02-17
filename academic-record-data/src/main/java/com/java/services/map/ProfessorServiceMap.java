package com.java.services.map;

import com.java.model.Professor;
import com.java.services.ProfessorService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProfessorServiceMap extends AbstractMapService<Professor, Integer> implements ProfessorService {
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
        return super.save(object.getId(), object);
    }

    @Override
    public void delete(Professor object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Integer id) {
        super.deleteById(id);
    }
}
