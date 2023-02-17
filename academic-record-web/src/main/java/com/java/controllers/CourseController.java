package com.java.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/courses")
@Controller
public class CourseController {

    @RequestMapping({"", "/", "/list", "/list.html"})
    public String courseList(){
        return "courses/list";
    }
}
