package com.java.service.map;

import com.java.model.Course;
import com.java.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
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
        return super.save(object);
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
