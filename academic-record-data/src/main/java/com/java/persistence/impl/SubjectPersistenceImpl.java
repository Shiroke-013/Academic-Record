package com.java.persistence.impl;

import com.java.model.Subject;
import com.java.persistence.ExceptionPersistence;
import com.java.persistence.SubjectPersistence;
import com.java.persistence.repository.SubjectRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class SubjectPersistenceImpl implements SubjectPersistence {

    @Autowired
    SubjectRepository subjectRepository;

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public void create(Subject subject) throws ExceptionPersistence {
        try {
            subjectRepository.save(subject);
        } catch (Exception e) {
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public Collection<Subject> findAll() throws ExceptionPersistence {
        try {
            return subjectRepository.findAll();
        } catch (Exception e) {
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public Subject findById(Integer id) throws ExceptionPersistence {
        Optional<Subject> subject = subjectRepository.findById(id);
        if (subject.isPresent()) {
            return subject.get();
        } else {
            throw new ExceptionPersistence("Could not find Teacher with id: " + id);
        }
    }

    @Override
    public void delete(Integer id) throws ExceptionPersistence {
        try {
            subjectRepository.deleteById(id);
        } catch (Exception e) {
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public void deleteAll() throws ExceptionPersistence {
        try {
            if (subjectRepository.count() != 0) {
                subjectRepository.deleteAll();
            }
        } catch (Exception e) {
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public void update(Subject subject) throws ExceptionPersistence {
        try {
            subjectRepository.save(subject);
        } catch (Exception e) {
            throw new ExceptionPersistence(e.getMessage());
        }
    }
}
