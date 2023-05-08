package com.java.controller;

import com.java.dto.CourseDto;
import com.java.dto.StudentDto;
import com.java.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RequestMapping("/course/")
@RestController
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping
    public void save(@Valid @RequestBody CourseDto courseDto) throws Exception {
        try {
            courseService.save(courseDto);
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
    void update(@Valid @RequestBody CourseDto courseDto, @PathVariable Integer id) throws Exception {
        try {
            courseService.update(courseDto, id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
