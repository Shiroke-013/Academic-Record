package com.java.controller;

import com.java.dto.GradeDto;
import com.java.dto.TeacherDto;
import com.java.service.StudentService;
import com.java.service.SubjectService;
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
import java.util.Set;

@RequestMapping("/teacher/")
@RestController
public class TeacherController {

    private static final String ERROR_MESSAGE = "An error occurred: ";

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody TeacherDto teacherDto){
        try {
            teacherService.save(teacherDto);
            return new ResponseEntity<>("The teacher was successfully created.",HttpStatus.CREATED);
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
            String errorMessage = ERROR_MESSAGE + "Unable to find the teacher";
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        try {
            return new ResponseEntity<>(teacherService.findAll(), HttpStatus.OK);
        } catch (Exception e){
            String errorMessage = ERROR_MESSAGE + "Not able to show all the teachers";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        try {
            teacherService.findById(id);
            teacherService.deleteById(id);
            return new ResponseEntity<>("Teacher was deleted with success",HttpStatus.ACCEPTED);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Can't delete teacher with id " + id;
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(){
        try {
            teacherService.delete();
            return new ResponseEntity<>("All teachers were deleted.",HttpStatus.ACCEPTED);
        } catch (Exception e){
            String errorMessage = ERROR_MESSAGE + "Can not delete all teachers";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<Object> update(@RequestBody TeacherDto teacherDto, @PathVariable Integer id){
        try {
            teacherService.update(teacherDto, id);
            return new ResponseEntity<>("Teacher was updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Could not update teacher";
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("course/{id}")
    public ResponseEntity<Object> findCourse(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(teacherService.findCourse(id), HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Unable to find course/s for teacher with id: " + id;
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("subjects/{id}")
    public ResponseEntity<Object> findSubjects(@PathVariable Integer id) {
        try {
            Set<String> subjects = (Set<String>) teacherService.findSubjects(id);
            if (subjects.isEmpty()){
                return new ResponseEntity<>("No subjects found for this teacher",HttpStatus.OK);
            } else {
                return new ResponseEntity<>(teacherService.findSubjects(id), HttpStatus.OK);
            }
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Unable to find subject/s for teacher with id: " + id;
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add/grade/{studentId}/{subjectId}")
    public ResponseEntity<Object> addGrade(@RequestBody GradeDto gradeDto, @PathVariable Integer studentId, @PathVariable Integer subjectId) {
        try {
            if (studentService.findById(studentId) == null) {
                return new ResponseEntity<>("Student does not exist", HttpStatus.NOT_FOUND);
            } else if (subjectService.findById(subjectId) == null) {
                return new ResponseEntity<>("Subject with id" + subjectId + "does not exist", HttpStatus.NOT_FOUND);
            } else {
                teacherService.addGrade(gradeDto, studentId, subjectId);
                return new ResponseEntity<>("Grade added to student: " + subjectId, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Not possible to add grade to student.";
            return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
