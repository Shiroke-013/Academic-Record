package com.java.services.map;

import com.java.model.Student;
import com.java.services.CrudService;

import java.util.Set;

public class StudentServiceMap extends AbstractMapService<Student, Integer> implements CrudService<Student, Integer> {

    @Override
    public Set<Student> findAll() {
        return super.findAll();
    }

    @Override
    public Student findById(Integer id) {
        return super.findById(id);
    }

    @Override
    public Student save(Student object) {
        return super.save(object.getId(), object);
    }

    @Override
    public void delete(Student object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Integer id) {
        super.deleteById(id);
    }
}
