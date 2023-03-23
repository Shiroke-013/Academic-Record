package com.java.persistence;

import com.java.dto.CourseDto;
import com.java.model.Course;

import java.util.Set;

public interface CoursePersistence {

    void create(Course course);

    Set<Course> getAll();

    Course findById(Integer id);

    void delete(Integer id);

    void deleteAll();

    void update(CourseDto courseDto, Integer id);
}
