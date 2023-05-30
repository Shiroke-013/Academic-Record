package com.java.persistence.impl;

import com.java.model.Student;
import com.java.persistence.ExceptionPersistence;
import com.java.persistence.StudentPersistence;
import com.java.persistence.repository.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class StudentPersistenceImpl implements StudentPersistence {

    @Autowired
    StudentRepository studentRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void create(Student student) throws ExceptionPersistence {
        try {
            studentRepository.save(student);
        } catch (Exception e){
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public Optional<Student> findById(Integer id) throws ExceptionPersistence {
        try {
            return studentRepository.findById(id);
        } catch (Exception e){
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public Collection<Student> findAll() throws ExceptionPersistence {
        try {
            return studentRepository.findAll();
        } catch (Exception e) {
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) throws ExceptionPersistence {
        try {
            Optional<Student> student = studentRepository.findById(id);
            student.ifPresent(value -> studentRepository.delete(value));
        } catch (Exception e){
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public void deleteAll() throws ExceptionPersistence {
        try {
            studentRepository.deleteAll();
        } catch (Exception e){
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public void update(Student student) throws ExceptionPersistence {
        try {
            studentRepository.save(student);
        } catch (Exception e){
            throw new ExceptionPersistence(e.getMessage());
        }
    }

}
