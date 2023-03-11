package com.java.service.impl;


import com.java.model.Grade;
import com.java.service.GradeService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GradeServiceImpl extends AbstractMapService<Grade, Integer> implements GradeService {

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
        return super.save(object);
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
