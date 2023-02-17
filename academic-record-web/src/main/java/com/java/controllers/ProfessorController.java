package com.java.controllers;

import com.java.services.ProfessorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/professors")
@Controller
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @RequestMapping({"", "/", "/list", "/list.html"})
    public String professorList(Model model){

        model.addAttribute("professors", professorService.findAll());

        return "professors/list";
    }
}
