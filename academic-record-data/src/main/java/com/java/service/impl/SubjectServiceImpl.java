package com.java.service.impl;

import com.java.dto.SubjectDto;
import com.java.mappers.SubjectMapper;
import com.java.model.Subject;
import com.java.persistence.SubjectPersistence;
import com.java.service.ExceptionService;
import com.java.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectPersistence subjectPersistence;

    @Override
    public void save(SubjectDto subjectDto) throws ExceptionService {
        try {
            Subject subject = SubjectMapper.INSTANCE.dtoToSubject(subjectDto);
            subjectPersistence.create(subject);
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Object findById(Integer id) throws ExceptionService {
        try {
            return subjectPersistence.findById(id);
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Set<Subject> findAll() throws ExceptionService {
        try {
            return subjectPersistence.findAll();
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void deleteAll() throws ExceptionService {
        try {
            subjectPersistence.deleteAll();
        } catch (Exception e){
            throw new ExceptionService("Could not delete all subjects");
        }
    }

    @Override
    public void deleteById(Integer id) throws ExceptionService {
        try {
            subjectPersistence.delete(id);
        } catch (Exception e){
            throw new ExceptionService("Could not delete student with id: " + id);
        }
    }

    @Override
    public void update(SubjectDto subjectDto, Integer id) throws ExceptionService {
        //Not yet implemented
    }

}
