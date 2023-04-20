package com.java.controller;

import com.java.dto.ProfessorDto;
import com.java.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;


import javax.validation.Valid;

@RequestMapping("/professor/")
@RestController
public class ProfessorController {

    private static final String ERROR_OCCURRED = "An error occurred: ";

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        try {
            return new ResponseEntity<>(professorService.findAll(), HttpStatus.OK);
        } catch (Exception e){
            String errorMessage = ERROR_OCCURRED + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findById(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(professorService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = ERROR_OCCURRED + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody ProfessorDto professorDto){
        try {
            professorService.save(professorDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            String errorMessage = ERROR_OCCURRED + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        try {
            professorService.findById(id);
            professorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            String errorMessage = ERROR_OCCURRED + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(){
        try {
            professorService.delete();
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e){
            String errorMessage = ERROR_OCCURRED + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<Object> update(@RequestBody ProfessorDto professorDto, @PathVariable Integer id){
        try {
            professorService.update(professorDto, id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            String errorMessage = ERROR_OCCURRED + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
