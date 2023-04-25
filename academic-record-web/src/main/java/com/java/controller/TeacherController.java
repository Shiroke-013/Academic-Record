package com.java.controller;

import com.java.dto.TeacherDto;
import com.java.service.TeacherService;
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

@RequestMapping("/teacher/")
@RestController
public class TeacherController {

    private static final String ERROR_MESSAGE = "An error occurred: ";

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        try {
            return new ResponseEntity<>(teacherService.findAll(), HttpStatus.OK);
        } catch (Exception e){
            String errorMessage = ERROR_MESSAGE + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findById(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(teacherService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody TeacherDto teacherDto){
        try {
            teacherService.save(teacherDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            String errorMessage = ERROR_MESSAGE + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        try {
            teacherService.findById(id);
            teacherService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(){
        try {
            teacherService.delete();
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e){
            String errorMessage = ERROR_MESSAGE + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<Object> update(@RequestBody TeacherDto teacherDto, @PathVariable Integer id){
        try {
            teacherService.update(teacherDto, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
