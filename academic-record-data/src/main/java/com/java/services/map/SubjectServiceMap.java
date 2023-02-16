package com.java.services.map;

import com.java.model.Subject;
import com.java.services.CrudService;

import java.util.Set;

public class SubjectServiceMap extends AbstractMapService<Subject, Integer> implements CrudService<Subject, Integer> {

    @Override
    public Set<Subject> findAll() {
        return super.findAll();
    }

    @Override
    public Subject findById(Integer id) {
        return super.findById(id);
    }

    @Override
    public Subject save(Subject object) {
        return super.save(object.getId(), object);
    }

    @Override
    public void delete(Subject object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Integer id) {
        super.deleteById(id);
    }
}
