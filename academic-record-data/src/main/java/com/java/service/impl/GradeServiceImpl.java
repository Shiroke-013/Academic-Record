package com.java.service.impl;


import com.java.dto.GradeDto;
import com.java.model.Grade;
import com.java.persistence.GradePersistence;
import com.java.service.ExceptionService;
import com.java.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;


@Service
public class GradeServiceImpl implements GradeService {


    @Override
    public Grade save(GradeDto gradeDto) throws ExceptionService{
        return new Grade();
    }

    @Override
    public Grade findById(Integer id) {
        return null;
    }

    @Override
    public Set<Grade> findAll() throws ExceptionService {
        try {
            return Collections.<Grade>emptySet();
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void delete() throws ExceptionService {
        //Not yet
    }

    @Override
    public void deleteById(Integer id) {
        //Not yet
    }

    @Override
    public void update(GradeDto gradeDto, Integer id) throws ExceptionService {
        //Not yet
    }
}
