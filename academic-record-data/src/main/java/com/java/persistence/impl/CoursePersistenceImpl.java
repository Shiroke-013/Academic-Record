package com.java.persistence.impl;

import com.java.model.Course;
import com.java.persistence.CoursePersistence;
import com.java.persistence.ExceptionPersistence;
import com.java.persistence.repository.CourseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class CoursePersistenceImpl implements CoursePersistence {

    @Autowired
    CourseRepository courseRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void create(Course course) throws ExceptionPersistence {
        try {
            courseRepository.save(course);
        } catch (Exception e) {
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public Set<Course> findAll() throws ExceptionPersistence {
        try {
            List<Course> list = courseRepository.findAll();
            return new HashSet<>(list);
        } catch (Exception e) {
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public Course findById(Integer id) throws ExceptionPersistence {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new ExceptionPersistence("Course with id: " + id + " not found");
        }
    }

    @Override
    public void delete(Integer id) throws ExceptionPersistence {
        try {
            Optional<Course> course = courseRepository.findById(id);
            course.ifPresent(value -> courseRepository.delete(value));
        } catch (Exception e) {
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public void deleteAll() throws ExceptionPersistence {
        try {
            courseRepository.deleteAll();
        } catch (Exception e){
            throw new ExceptionPersistence(e.getMessage());
        }
    }

    @Override
    public void update(Course course) throws ExceptionPersistence {
        try {
            courseRepository.save(course);
        } catch (Exception e){
            throw new ExceptionPersistence("Failed to update student with id: " + course.getId());
        }
    }
}
