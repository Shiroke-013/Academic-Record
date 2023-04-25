package com.java.controller;


import com.java.dto.StudentDto;
import com.java.model.Student;
import com.java.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/student/")
@RestController
public class StudentController {

    private static final String ERROR_OCCURRED = "An error occurred: ";

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

    @GetMapping("{lastName}")
    public Object findByLastName(@PathVariable String lastName) throws Exception {
        try {
            return studentService.findByLastName(lastName);
        } catch (Exception e){
            throw  new Exception(e);
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

    @PatchMapping("{id}")
    void update(@Valid @RequestBody StudentDto studentDto, @PathVariable Integer id) throws Exception {
        try {
            studentService.update(studentDto, id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

}
