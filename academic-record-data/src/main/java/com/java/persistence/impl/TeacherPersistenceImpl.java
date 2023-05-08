package com.java.persistence.impl;

import com.java.model.Teacher;
import com.java.persistence.ExceptionPersistence;
import com.java.persistence.TeacherPersistence;
import com.java.persistence.repository.TeacherRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;


@Repository
public class TeacherPersistenceImpl implements TeacherPersistence {

    @Autowired
    TeacherRepository teacherRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void create(Teacher teacher) throws ExceptionPersistence {
        try {
            teacherRepository.save(teacher);
        } catch (Exception e) {
            throw new ExceptionPersistence("Failed to create Teacher");
        }
    }

    @Override
    public Collection<Teacher> findAll() throws ExceptionPersistence {
        try {
            return teacherRepository.findAll();
        } catch (Exception e) {
            throw new ExceptionPersistence("There's no teachers");
        }
    }

    @Override
    public Teacher findById(Integer id) throws ExceptionPersistence {

        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isPresent()) {
            return teacher.get();
        } else {
            throw new ExceptionPersistence("Could not find Teacher with id: " + id);
        }
    }

    @Override
    public void deleteById(Integer id) throws ExceptionPersistence {
        try {
            teacherRepository.deleteById(id);
        } catch (Exception e) {
            throw new ExceptionPersistence("Could not delete Teacher with id: " + id);
        }
    }

    @Override
    public void delete() throws ExceptionPersistence {
        try {
            if (teacherRepository.count() != 0) {
                teacherRepository.deleteAll();
            }
        } catch (Exception e) {
            throw new ExceptionPersistence("Could not delete Teachers");
        }
    }

    @Override
    public void update(Teacher teacher) throws ExceptionPersistence {
        try {
            teacherRepository.save(teacher);
        } catch (Exception e) {
            throw new ExceptionPersistence("Could not update Teacher");
        }
    }

}
