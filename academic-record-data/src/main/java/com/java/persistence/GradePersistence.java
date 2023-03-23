package com.java.persistence;

import com.java.dto.GradeDto;
import com.java.model.Grade;

import java.util.Set;

public interface GradePersistence {

    void create(Grade grade);

    Set<Grade> getAll();

    Grade findById(Integer id);

    void delete(Integer id);

    void deleteAll();

    void update(GradeDto gradeDto, Integer id);
}
