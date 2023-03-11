package com.java.persistence;

import com.java.dto.CourseDto;
import com.java.model.Course;

import java.util.Set;

public interface CoursePersistence {

    void createCourse(Course course);

    Set<Course> getAllCourse();

    Course findCourseById(Integer id);

    void deleteCourse(Integer id);

    void deleteAllCourse();

    void updateCourse(CourseDto courseDto, Integer id);
}
