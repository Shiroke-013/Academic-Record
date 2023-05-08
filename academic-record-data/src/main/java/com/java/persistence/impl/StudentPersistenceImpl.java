package com.java.persistence.impl;

import com.java.model.Student;
import com.java.model.Subject;
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
            throw new ExceptionPersistence("Failed to create student");
        }
    }

    @Override
    public Student findById(Integer id) throws ExceptionPersistence {
        try {
            Optional<Student> student = studentRepository.findById(id);
            if (student.isPresent()){
                return student.get();
            }
        } catch (Exception e){
            throw new ExceptionPersistence("Failed to find student with id: " + id);
        }
        return new Student();
    }

    @Override
    public Collection<Student> findAll() throws ExceptionPersistence {
        try {
            return studentRepository.findAll();
        } catch (Exception e) {
            throw new ExceptionPersistence("Failed to get all students");
        }
    }

    @Override
    public void delete(Integer id) throws ExceptionPersistence {
        try {
            Optional<Student> student = studentRepository.findById(id);
            student.ifPresent(value -> studentRepository.delete(value));
        } catch (Exception e){
            throw new ExceptionPersistence("Failed to delete Student with id: " + id);
        }
    }

    @Override
    public void deleteAll() throws ExceptionPersistence {
        try {
            studentRepository.deleteAll();
        } catch (Exception e){
            throw new ExceptionPersistence("Failed to delete all Students");
        }
    }

    @Override
    public void update(Student student) throws ExceptionPersistence {
        try {
            studentRepository.save(student);
        } catch (Exception e){
            throw new ExceptionPersistence("Failed to update student with id: " + student.getId());
        }
    }

    @Override
    public Collection<Subject> findAllSubjects(Integer id) {
        return null;
    }
}
