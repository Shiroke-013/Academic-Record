package com.java.controller;

import com.java.dto.CourseDto;
import com.java.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RequestMapping("/course/")
@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public void save(@Valid @RequestBody CourseDto subjectDto) throws Exception {
        try {
            courseService.save(subjectDto);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping("{id}")
    public Object findById(@PathVariable Integer id) throws Exception {
        try {
            return courseService.findById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping
    public Collection<CourseDto> findAll() throws Exception {
        try {
            return courseService.findAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable Integer id) throws Exception {
        try {
            courseService.deleteById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @DeleteMapping
    void deleteAll() throws Exception {
        try {
            courseService.deleteAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PatchMapping("{id}")
    void update(@Valid @RequestBody CourseDto subjectDto, @PathVariable Integer id) throws Exception {
        try {
            courseService.update(subjectDto, id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @GetMapping("{id}/teachers")
    public Collection<String> findTeachers(@PathVariable Integer id) throws Exception {
        try {
            return courseService.findTeachers(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @GetMapping("{id}/students")
    public Collection<String> findStudents(@PathVariable Integer id) throws Exception {
        try {
            return courseService.findStudents(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @GetMapping("{id}/subjects")
    public Collection<String> findSubjects(@PathVariable Integer id) throws Exception {
        try {
            return courseService.findSubjects(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }



    @DeleteMapping("{courseId}/teacher/{teacherId}")
    void deleteTeacherFromCourse(@PathVariable Integer courseId, @PathVariable Integer teacherId) throws Exception {
        try {
            courseService.deleteTeacherFromCourse(courseId, teacherId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PatchMapping("{courseId}/add/teacher/{teacherId}")
    void addTeacher(@PathVariable Integer courseId, @PathVariable Integer teacherId) throws Exception {
        try {
            courseService.addTeacher(courseId, teacherId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PatchMapping("{courseId}/add/subject/{subjectId}")
    void addSubject(@PathVariable Integer courseId, @PathVariable Integer subjectId) throws Exception {
        try {
            courseService.addSubject(courseId, subjectId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
