package com.java.controllers;

import com.java.services.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/courses")
@Controller
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping({"", "/", "/list", "/list.html"})
    public String courseList(Model model){

        model.addAttribute("courses", courseService.findAll());

        return "courses/list";
    }
}
