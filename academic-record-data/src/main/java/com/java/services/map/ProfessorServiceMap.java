package com.java.services.map;

import com.java.model.Professor;
import com.java.services.CrudService;

import java.util.Set;

public class ProfessorServiceMap extends AbstractMapService<Professor, Integer> implements CrudService<Professor, Integer> {

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
