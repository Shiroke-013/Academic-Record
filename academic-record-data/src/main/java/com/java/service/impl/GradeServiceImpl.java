package com.java.service.impl;


import com.java.dto.GradeDto;
import com.java.mappers.GradeMapper;
import com.java.model.Grade;
import com.java.persistence.GradePersistence;
import com.java.service.ExceptionService;
import com.java.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
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
            Optional<Grade> grade = Optional.ofNullable(gradePersistence.findById(id));
            return GradeMapper.INSTANCE.gradeToDto(grade.get());
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Collection<GradeDto> findAll() throws ExceptionService {
        try {
            Collection<Grade> grades = gradePersistence.findAll();
            Set<GradeDto> gradeDtos = new HashSet<>();

            for (Grade grade : grades) {
                gradeDtos.add(GradeMapper.INSTANCE.gradeToDto(grade));
            }
            return gradeDtos;
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
        try {
            Grade grade = gradePersistence.findById(id);
            if (gradeDto.getMark() != null){
                grade.setMark(gradeDto.getMark());
            }
            gradePersistence.create(grade);
        } catch (Exception e) {
            throw new ExceptionService("Could not update");
        }
    }
}
