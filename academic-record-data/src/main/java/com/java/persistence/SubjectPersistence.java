package com.java.persistence;

import com.java.dto.SubjectDto;
import com.java.model.Subject;

import java.util.Set;

public interface SubjectPersistence {

    void create(Subject subject);

    Set<Subject> getAll();

    Subject findById(Integer id);

    void delete(Integer id);

    void deleteAll();

    void update(SubjectDto subjectDto, Integer id);

}
