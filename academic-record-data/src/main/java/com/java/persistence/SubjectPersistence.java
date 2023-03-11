package com.java.persistence;

import com.java.dto.SubjectDto;
import com.java.model.Subject;

import java.util.Set;

public interface SubjectPersistence {

    void createSubject(Subject subject);

    Set<Subject> getAllSubject();

    Subject findSubjectById(Integer id);

    void deleteSubject(Integer id);

    void deleteAllSubject();

    void updateSubject(SubjectDto subjectDto, Integer id);

}
