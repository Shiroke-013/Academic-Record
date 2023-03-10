package com.java.controllers;

import com.java.model.Course;
import com.java.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequestMapping("/course")
@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public Set<Course> courseList(Model model){
        return courseService.findAll();
    }
}
