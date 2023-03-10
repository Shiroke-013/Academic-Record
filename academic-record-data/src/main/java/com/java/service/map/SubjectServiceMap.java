package com.java.service.map;

import com.java.model.Subject;
import com.java.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SubjectServiceMap extends AbstractMapService<Subject, Integer> implements SubjectService {

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
        return super.save(object);
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
