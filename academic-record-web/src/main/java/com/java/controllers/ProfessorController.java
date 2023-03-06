package com.java.controllers;

import com.java.model.Professor;
import com.java.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/professor")
@RestController
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("getAll")
    public Set<Professor> findAll(Model model){
        return professorService.findAll();
    }

    @GetMapping("get/{id}")
    public Professor findById(@PathVariable Integer id){
        return professorService.findById(id);
    }

    @PostMapping("/create")
    public String save(@RequestBody Professor professor){
        professorService.save(professor);
        return "Success";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        try {
            professorService.findById(id);
            professorService.deleteById(id);
            return new ResponseEntity<>("Professor with id " + id + " delete.", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("Professor with id " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@RequestBody Professor professor, @PathVariable Integer id){
        try {
            professorService.update(professor, id);
            return new ResponseEntity<>("Professor " + id + " updated.", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("Professor with id " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
}
