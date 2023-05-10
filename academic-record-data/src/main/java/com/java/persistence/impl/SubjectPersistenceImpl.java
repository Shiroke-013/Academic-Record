package com.java.persistence.impl;

import com.java.dto.SubjectDto;
import com.java.model.Subject;
import com.java.persistence.ExceptionPersistence;
import com.java.persistence.SubjectPersistence;
import com.java.persistence.repository.SubjectRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class SubjectPersistenceImpl implements SubjectPersistence {

    @Autowired
    SubjectRepository subjectRepository;

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public void create(Subject subject) throws ExceptionPersistence {

    }

    @Override
    public Set<Subject> findAll() throws ExceptionPersistence {
        return null;
    }

    @Override
    public Subject findById(Integer id) throws ExceptionPersistence {
        return null;
    }

    @Override
    public void delete(Integer id) throws ExceptionPersistence {

    }

    @Override
    public void deleteAll() throws ExceptionPersistence {

    }

    @Override
    public void update(SubjectDto subjectDto, Integer id) throws ExceptionPersistence {

    }
}
