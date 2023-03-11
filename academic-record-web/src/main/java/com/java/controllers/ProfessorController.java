package com.java.controllers;

import com.java.dto.ProfessorDto;
import com.java.model.Professor;
import com.java.service.ExceptionService;
import com.java.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;


import java.util.Set;

@RequestMapping("/professor/")
@RestController
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public ResponseEntity<Set<Professor>> findAll(Model model) throws ExceptionService {
        try {
            return new ResponseEntity<>(professorService.findAll(), HttpStatus.ACCEPTED);
        } catch (Exception e){
            throw new ExceptionService("Not Found");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(professorService.findById(id), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("Professor with id: " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ProfessorDto professorDto){
        try {
            professorService.save(professorDto);
            return new ResponseEntity<>("Professor created.", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("Professor could not be created.", HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        String message = "Professor with id: ";
        try {
            professorService.findById(id);
            professorService.deleteById(id);
            return new ResponseEntity<>(message + id + " delete.", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(message + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("{id}")


    @PutMapping("{id}")
    public ResponseEntity<String> update(@RequestBody Professor professor, @PathVariable Integer id){
        String message = "Professor with id";
        try {
            professorService.update(professor, id);
            return new ResponseEntity<>(message + id + " updated.", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(message + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
}
