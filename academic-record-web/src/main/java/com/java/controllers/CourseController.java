package com.java.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CourseController {

    @RequestMapping({"/courses", "/courses/list", "/courses/list.html"})
    public String courseList(){
        return "courses/list";
    }
}
