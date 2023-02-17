package com.java.services.map;

import com.java.model.Student;
import com.java.services.StudentService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StudentServiceMap extends AbstractMapService<Student, Integer> implements StudentService {

    @Override
    public Student findByLastName(String lastName) {
        return null;
    }

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
