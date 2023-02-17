package com.java.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/professors")
@Controller
public class ProfessorController {

    @RequestMapping({"", "/", "/list", "/list.html"})
    public String professorList(){
        return "professors/list";
    }
}
