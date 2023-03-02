package com.java.controllers;

import com.java.model.Professor;
import com.java.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequestMapping("/professor")
@RestController
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public Set<Professor> findAll(Model model){
        return professorService.findAll();
    }
}
