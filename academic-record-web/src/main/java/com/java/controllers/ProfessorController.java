package com.java.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfessorController {

    @RequestMapping({"/professors", "/professors/list", "/professors/list.html"})
    public String professorList(){
        return "professors/list";
    }
}
