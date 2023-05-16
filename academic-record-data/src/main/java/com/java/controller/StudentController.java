package com.java.controller;

import com.java.dto.StudentDto;
import com.java.service.CourseService;
import com.java.service.StudentService;
import com.java.service.SubjectService;
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

@RequestMapping("/student/")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public void save(@Valid @RequestBody StudentDto studentDto) throws Exception {
        try {
            studentService.save(studentDto);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping("{id}")
    public Object findById(@PathVariable Integer id) throws Exception {
        try {
            return studentService.findById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping
    public Collection<StudentDto> findAll() throws Exception {
        try {
            return studentService.findAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable Integer id) throws Exception {
        try {
            studentService.deleteById(id);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping
    void deleteAll() throws Exception {
        try {
            studentService.deleteAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PatchMapping("{id}")
    void update(@Valid @RequestBody StudentDto studentDto, @PathVariable Integer id) throws Exception {
        try {
            studentService.update(studentDto, id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("{studentId}/register/course/{courseId}")
    void registerIntoCourse(@PathVariable Integer studentId, @PathVariable Integer courseId) throws Exception {
        try {
            studentService.registerIntoCourse(studentId, courseId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("{studentId}/register/subject/{subjectId}")
    void registerIntoSubject(@PathVariable Integer studentId, @PathVariable Integer subjectId) throws Exception {
        try {
            studentService.registerIntoSubject(studentId, subjectId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("course/{id}")
    public Object findCourse(@PathVariable Integer id) throws Exception {
        try {
            return studentService.findCourse(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("subjects/{id}")
    public Collection<String> findAllSubjects(@PathVariable Integer id) throws Exception {
        try {
            return studentService.findAllSubjects(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
