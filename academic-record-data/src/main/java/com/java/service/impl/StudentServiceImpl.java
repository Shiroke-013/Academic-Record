package com.java.service.impl;

import com.java.dto.StudentDto;
import com.java.mappers.StudentMapper;
import com.java.model.Student;
import com.java.model.Subject;
import com.java.persistence.StudentPersistence;
import com.java.service.ExceptionService;
import com.java.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentPersistence studentPersistence;


    @Override
    public void save(StudentDto studentDto) throws ExceptionService {
        try {
            Student student = StudentMapper.INSTANCE.dtoToStudent(studentDto);
            studentPersistence.create(student);
        } catch (Exception e) {
            throw new ExceptionService("Could not create the student");
        }
    }

    @Override
    public Object findById(Integer id) throws ExceptionService {
        try {
            Student student = studentPersistence.findById(id);
            return student;
        } catch (Exception e){
            throw new ExceptionService("Could not find the student");
        }
    }

    /*@Override
    public Object findByLastName(String lastName) throws ExceptionService {
        try {
            return studentPersistence.findByLastName(lastName);
        } catch (Exception e) {
            throw new ExceptionService("Could not find the student");
        }
    }*/

    @Override
    public Collection<StudentDto> findAll() throws ExceptionService {
        try {
            Collection<Student> allStudents = studentPersistence.findAll();
            List<StudentDto> filterStudents = new ArrayList<>();
            for (Student student : allStudents){

                StudentDto studentDto = new StudentDto(student.getId(), student.getFirstName(), student.getLastName(),
                        student.getEmail(), student.getPassword());

                filterStudents.add(studentDto);
            }

            return filterStudents;

        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Collection<Subject> findAllSubjects(Integer id) throws ExceptionService {
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
            throw new ExceptionService("Could not delete student with id: " + id);
        }
    }

    @Override
    public void deleteAll() throws ExceptionService {
        try {
            studentPersistence.deleteAll();
        } catch (Exception e){
            throw new ExceptionService("Could delete all students");
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
    public void registerIntoCourse(String course) throws ExceptionService {

    }

    @Override
    public void registerIntoSubject() throws ExceptionService {

    }
}
