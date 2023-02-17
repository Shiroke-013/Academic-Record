package com.java.services.map;

import com.java.model.Grade;
import com.java.services.CrudService;
import com.java.services.GradeService;

import java.util.Set;

public class GradeServiceMap extends AbstractMapService<Grade, Integer> implements GradeService {

    @Override
    public Set<Grade> findAll() {
        return super.findAll();
    }

    @Override
    public Grade findById(Integer id) {
        return super.findById(id);
    }

    @Override
    public Grade save(Grade object) {
        return super.save(object.getId(), object);
    }

    @Override
    public void delete(Grade object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Integer id) {
        super.deleteById(id);
    }
}
