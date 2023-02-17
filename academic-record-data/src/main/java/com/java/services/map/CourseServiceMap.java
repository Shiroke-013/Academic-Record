package com.java.services.map;

import com.java.model.Course;
import com.java.services.CourseService;

import java.util.Set;

public class CourseServiceMap extends AbstractMapService<Course, Integer> implements CourseService {

    @Override
    public Set<Course> findAll() {
        return super.findAll();
    }

    @Override
    public Course findById(Integer id) {
        return super.findById(id);
    }

    @Override
    public Course save(Course object) {
        return super.save(object.getId(), object);
    }

    @Override
    public void delete(Course object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Integer id) {
        super.deleteById(id);
    }


}
