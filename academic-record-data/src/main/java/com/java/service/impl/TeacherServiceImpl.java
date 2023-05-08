package com.java.service.impl;

import com.java.dto.TeacherDto;
import com.java.mappers.TeacherMapper;
import com.java.model.Teacher;
import com.java.persistence.TeacherPersistence;
import com.java.service.ExceptionService;
import com.java.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherPersistence teacherPersistence;

    @Override
    public Teacher save(TeacherDto teacherDto) throws ExceptionService {
        try {
            Teacher teacher = TeacherMapper.INSTANCE.dtoToTeacher(teacherDto);
            teacherPersistence.create(teacher);
            return teacher;
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Collection<Teacher> findAll() throws ExceptionService {
        try{
            return teacherPersistence.findAll();
        }catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Teacher findById(Integer id) throws ExceptionService {
        try{
            return teacherPersistence.findById(id);
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void delete() throws ExceptionService {
        try {
            teacherPersistence.delete();
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) throws ExceptionService {
        try {
            teacherPersistence.deleteById(id);
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void update(TeacherDto teacherDto, Integer id) throws ExceptionService {
        try {
            Teacher teacher = findById(id);
            if (teacher != null){
                if (teacherDto.getFirstName() != null){
                    teacher.setFirstName(teacherDto.getFirstName());
                }
                if (teacherDto.getLastName() != null){
                    teacher.setLastName(teacherDto.getLastName());
                }
                if (teacherDto.getEmail() != null){
                    teacher.setEmail(teacherDto.getEmail());
                }
                if (teacherDto.getPassword() != null){
                teacher.setPassword(teacherDto.getPassword());
                }
                teacherPersistence.update(teacher);
            }
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }
}
