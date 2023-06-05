package com.java.persistence.impl;

import com.java.model.Grade;
import com.java.persistence.ExceptionPersistence;
import com.java.persistence.GradePersistence;
import com.java.persistence.repository.GradeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class GradePersistenceImpl implements GradePersistence {

    @Autowired
    GradeRepository gradeRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void create(Grade grade) throws ExceptionPersistence {
        try {
            gradeRepository.save(grade);
        } catch (Exception e) {
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public Collection<Grade> findAll() throws ExceptionPersistence {
        try {
            return gradeRepository.findAll();
        } catch (Exception e) {
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public Grade findById(Integer id) throws ExceptionPersistence {
        Optional<Grade> grade = gradeRepository.findById(id);
        if (grade.isPresent()) {
            return grade.get();
        } else {
            throw new ExceptionPersistence("Could not find Grade with id: " + id);
        }
    }

    @Override
    public void delete(Integer id) throws ExceptionPersistence {
        try {
            gradeRepository.deleteById(id);
        } catch (Exception e) {
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public void deleteAll() throws ExceptionPersistence {
        try {
            if (gradeRepository.count() != 0) {
                gradeRepository.deleteAll();
            }
        } catch (Exception e) {
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public void update(Grade grade) throws ExceptionPersistence {
        try {
            gradeRepository.save(grade);
        } catch (Exception e) {
            throw new ExceptionPersistence(e.getMessage());
        }
    }
}
