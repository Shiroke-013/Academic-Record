package com.java.service.impl;


import com.java.dto.GradeDto;
import com.java.dto.SubjectDto;
import com.java.mappers.GradeMapper;
import com.java.mappers.SubjectMapper;
import com.java.model.Grade;
import com.java.model.Subject;
import com.java.persistence.GradePersistence;
import com.java.persistence.SubjectPersistence;
import com.java.service.ExceptionService;
import com.java.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;


@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradePersistence gradePersistence;

    @Override
    public void save(GradeDto gradeDto) throws ExceptionService {
        try {
            Grade grade = GradeMapper.INSTANCE.dtoToGrade(gradeDto);
            gradePersistence.create(grade);
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Object findById(Integer id) throws ExceptionService {
        try {
            return gradePersistence.findById(id);
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Set<Grade> findAll() throws ExceptionService {
        try {
            return gradePersistence.findAll();
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void deleteAll() throws ExceptionService {
        try {
            gradePersistence.deleteAll();
        } catch (Exception e){
            throw new ExceptionService("Could not delete all subjects");
        }
    }

    @Override
    public void deleteById(Integer id) throws ExceptionService {
        try {
            gradePersistence.delete(id);
        } catch (Exception e){
            throw new ExceptionService("Could not delete student with id: " + id);
        }
    }

    @Override
    public void update(GradeDto gradeDto, Integer id) throws ExceptionService {
        //Not yet implemented
    }
}
