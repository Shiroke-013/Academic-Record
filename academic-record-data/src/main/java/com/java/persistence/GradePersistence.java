package com.java.persistence;

import com.java.dto.GradeDto;
import com.java.model.Grade;

import java.util.Set;

public interface GradePersistence {

    void createGrade(Grade grade);

    Set<Grade> getAllGrade();

    Grade findGradeById(Integer id);

    void deleteGrade(Integer id);

    void deleteAllGrade();

    void updateGrade(GradeDto gradeDto, Integer id);
}
