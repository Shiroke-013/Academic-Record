package com.java.service.impl;

import com.java.dto.CourseDto;
import com.java.mappers.CourseMapper;
import com.java.model.Course;
import com.java.persistence.CoursePersistence;
import com.java.service.CourseService;
import com.java.service.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class CourseServiceImpl implements CourseService {

    @Autowired
    CoursePersistence coursePersistence;

    @Override
    public void save(CourseDto courseDto) throws Exception {

        Course course = CourseMapper.INSTANCE.dtoToCourse(courseDto);
        coursePersistence.create(course);
    }

    @Override
    public Object findById(Integer id) throws ExceptionService {
        try {
            return coursePersistence.findById(id);
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Collection<Course> findAll() throws ExceptionService {
        try {
            return coursePersistence.findAll();
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void deleteAll() throws ExceptionService {
        try {
            coursePersistence.deleteAll();
        } catch (Exception e){
            throw new ExceptionService("Could not delete all courses");
        }
    }

    @Override
    public void deleteById(Integer id) throws ExceptionService {
        try {
            coursePersistence.delete(id);
        } catch (Exception e){
            throw new ExceptionService("Could not delete student with id: " + id);
        }
    }

    @Override
    public void update(CourseDto courseDto, Integer id) throws ExceptionService {
        //Not yet implemented
    }
}
