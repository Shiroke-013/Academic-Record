package com.java.persistence;

import com.java.dto.SubjectDto;
import com.java.model.Subject;

import java.util.Set;

public interface SubjectPersistence {

    void create(Subject subject) throws ExceptionPersistence;

    Set<Subject> findAll() throws ExceptionPersistence;

    Subject findById(Integer id) throws ExceptionPersistence;

    void delete(Integer id) throws ExceptionPersistence;

    void deleteAll() throws ExceptionPersistence;

    void update(SubjectDto subjectDto, Integer id) throws ExceptionPersistence;

}
