package com.java.controller;

import com.java.dto.GradeDto;
import com.java.dto.SubjectDto;
import com.java.model.Grade;
import com.java.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;

@RequestMapping("/grade/")
@RestController
public class GradeController {

    private static final String ERROR_MESSAGE = "An error occurred: ";

    @Autowired
    private GradeService gradeService;

    @PostMapping
    ResponseEntity<Object> save(@Valid @RequestBody GradeDto gradeDto) {
        try {
            gradeService.save(gradeDto);
            return new ResponseEntity<>("All good, great created", HttpStatus.CREATED);
        } catch (Exception e){
            String errorMessage = ERROR_MESSAGE + "Unable to create grade";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    ResponseEntity<Object> findById(@PathVariable Integer id) throws Exception {
        try {
            GradeDto gradeDto = (GradeDto) gradeService.findById(id);
            if (gradeDto != null){
                return new ResponseEntity<>(gradeDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No grade found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            String errorMessage = ERROR_MESSAGE + "Unable to find grade with id: " + id;
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    ResponseEntity<Object> findAll() {
        try {
            Collection grades = gradeService.findAll();
            if (grades.isEmpty()) {
                return new ResponseEntity<>("No grades found", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(grades, HttpStatus.OK);
            }
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Not able to found all grades";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    ResponseEntity<Object> delete(@PathVariable Integer id) {
        try {
            if (gradeService.findById(id) != null) {
                gradeService.deleteById(id);
                return new ResponseEntity<>("Grade with id: " + id + " deleted.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Grade does not exist" , HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            String errorMessage = ERROR_MESSAGE + "Not able to delete grade with id: " + id;
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    ResponseEntity<Object> deleteAll() {
        try {
            gradeService.deleteAll();
            return new ResponseEntity<>("All grades were deleted.", HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Unable to delete all grades";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}")
    ResponseEntity<Object> update(@Valid @RequestBody GradeDto gradeDto, @PathVariable Integer id) {
        try {
            gradeService.update(gradeDto, id);
            return new ResponseEntity<>("Grade updated", HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Not possible to update grade.";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
