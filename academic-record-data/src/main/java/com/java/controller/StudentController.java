package com.java.controller;

import com.java.dto.StudentDto;
import com.java.service.StudentService;
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

    @GetMapping("subjects/{id}")
    public Object findAllSubjects(@PathVariable Integer id) {
        try {
            return studentService.findAllSubjects(id);
        } catch (Exception e){
            return new Exception(e);
        }
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable Integer id) throws Exception {
        try {
            studentService.deleteById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @DeleteMapping
    void deleteAll() throws Exception {
        try {
            studentService.deleteAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PatchMapping("{id}")
    void update(@Valid @RequestBody StudentDto studentDto, @PathVariable Integer id) throws Exception {
        try {
            studentService.update(studentDto, id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PostMapping("register/{course}")
    void registerIntoCourse(@PathVariable String course) throws Exception {
        try {
            studentService.registerIntoCourse(course);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

}
