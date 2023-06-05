package com.java.controller;

import com.java.dto.SubjectDto;
import com.java.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RequestMapping("/subject/")
@RestController
public class SubjectController {

    private static final String ERROR_MESSAGE = "An error occurred: ";

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    ResponseEntity<Object> save(@Valid @RequestBody SubjectDto subjectDto) {
        try {
            subjectService.save(subjectDto);
            return new ResponseEntity<>("Subject created", HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Not able to create subject";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    ResponseEntity<Object> findById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(subjectService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Not able to find subject with id: " + id;
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    ResponseEntity<Object> findAll() {
        try {
            return new ResponseEntity<>(subjectService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Not able to get all subjects";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    ResponseEntity<Object> delete(@PathVariable Integer id) {
        try {
            subjectService.deleteById(id);
            return new ResponseEntity<>("Subject was deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Can't delete subject with id: " + id;
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping
    ResponseEntity<Object> deleteAll() {
        try {
            subjectService.deleteAll();
            return new ResponseEntity<>("All subjects deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Not able to delete all subjects", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}")
    ResponseEntity<Object> update(@Valid @RequestBody SubjectDto subjectDto, @PathVariable Integer id) {
        try {
            subjectService.update(subjectDto, id);
            return new ResponseEntity<>("Subject updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to update subject", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{subjectId}/add/teacher/{teacherId}")
    ResponseEntity<Object> addTeacher(@PathVariable Integer subjectId, @PathVariable Integer teacherId) {
        try {
            subjectService.addTeacher(subjectId, teacherId);
            return new ResponseEntity<>("Teacher assigned to subject",HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Not possible to assign teacher to subject";
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("{id}/students")
    ResponseEntity<Object> findStudents(@PathVariable Integer id) {
        try {
            Collection students = subjectService.findStudents(id);
            if (students.isEmpty()){
                return new ResponseEntity<>("No students found for this subject", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(students, HttpStatus.OK);
            }
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Not possible to get all students from subject";
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("{subjectId}/teacher/{teacherId}")
    ResponseEntity<Object> deleteTeacherFromSubject(@PathVariable Integer subjectId, @PathVariable Integer teacherId) {
        try {
            subjectService.deleteTeacherFromSubject(subjectId, teacherId);
            return new ResponseEntity<>("Teacher deleted from subject with success", HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Not possible to unassigned teacher from subject";
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}

