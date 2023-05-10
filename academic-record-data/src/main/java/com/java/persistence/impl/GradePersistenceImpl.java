package com.java.persistence.impl;

import com.java.dto.GradeDto;
import com.java.model.Grade;
import com.java.persistence.ExceptionPersistence;
import com.java.persistence.GradePersistence;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class GradePersistenceImpl implements GradePersistence {
    @Override
    public void create(Grade grade) throws ExceptionPersistence {

    }

    @Override
    public Set<Grade> findAll() throws ExceptionPersistence {
        return null;
    }

    @Override
    public Grade findById(Integer id) throws ExceptionPersistence {
        return null;
    }

    @Override
    public void delete(Integer id) throws ExceptionPersistence {

    }

    @Override
    public void deleteAll() throws ExceptionPersistence {

    }

    @Override
    public void update(GradeDto gradeDto, Integer id) throws ExceptionPersistence {

    }
}
