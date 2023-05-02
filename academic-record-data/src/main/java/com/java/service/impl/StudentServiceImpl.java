package com.java.service.impl;

import com.java.dto.StudentDto;
import com.java.model.Student;
import com.java.persistence.StudentPersistence;
import com.java.service.ExceptionService;
import com.java.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentPersistence studentPersistence;


    @Override
    public void save(StudentDto studentDto) throws ExceptionService {
        try {
            Student student = new Student();
            studentPersistence.create(student);
        } catch (Exception e) {
            throw new ExceptionService("Could not create the student");
        }
    }

    @Override
    public Object findById(Integer id) throws ExceptionService {
        try {
            return studentPersistence.findById(id);
        } catch (Exception e){
            throw new ExceptionService("Could not find the student");
        }
    }

    @Override
    public Object findByLastName(String lastname) throws ExceptionService {
        try {
            return studentPersistence.findByLastName(lastname);
        } catch (Exception e) {
            throw new ExceptionService("Could not find the student");
        }
    }

    @Override
    public Collection findAll(Integer id) throws ExceptionService {
        try {
            return studentPersistence.findAllSubjects(id);
        } catch (Exception e){
            throw new ExceptionService("Could not find the student subjects");
        }
    }

    @Override
    public void deleteById(Integer id) throws ExceptionService {
        try {
            studentPersistence.delete(id);
        } catch (Exception e){
            throw new ExceptionService("Could not find the student subjects");
        }
    }

    @Override
    public void update(StudentDto studentDto, Integer id) throws ExceptionService {

    }

    @Override
    public Object calculateAverage() throws ExceptionService {
        return null;
    }

    @Override
    public Object howMuchToGetInFinalExam() throws ExceptionService {
        return null;
    }

    @Override
    public void registerIntoCourse() throws ExceptionService {

    }

    @Override
    public void registerIntoSubject() throws ExceptionService {

    }
}
