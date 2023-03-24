package com.java.persistence;

import com.java.dto.GradeDto;
import com.java.model.Grade;

import java.util.Set;

public interface GradePersistence {

    void create(Grade grade) throws ExceptionPersistence;

    Set<Grade> getAll() throws ExceptionPersistence;

    Grade findById(Integer id) throws ExceptionPersistence;

    void delete(Integer id) throws ExceptionPersistence;

    void deleteAll() throws ExceptionPersistence;

    void update(GradeDto gradeDto, Integer id) throws ExceptionPersistence;
}
