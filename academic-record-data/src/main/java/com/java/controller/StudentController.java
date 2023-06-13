package com.java.controller;

import com.java.dto.StudentDto;
import com.java.service.CourseService;
import com.java.service.StudentService;
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

@RequestMapping("/student/")
@RestController
public class StudentController {

    private static final String ERROR_MESSAGE = "An error occurred: ";

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody StudentDto studentDto) {
        try {
            studentService.save(studentDto);
            return new ResponseEntity<>("The student was successfully created.", HttpStatus.CREATED);
        } catch (Exception e){
            String errorMessage = ERROR_MESSAGE + "Not possible to create student";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        try {
            Integer studentId = Integer.parseInt(id);
            StudentDto studentDto = (StudentDto) studentService.findById(studentId);
            if (studentDto != null) {
                return new ResponseEntity<>(studentDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Student with id: " + id + " does not exits.", HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            String errorMessage = ERROR_MESSAGE + "Please give a valid identifier (integer)";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            String errorMessage = ERROR_MESSAGE + "while processing the request";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        try {
            return new ResponseEntity<>(studentService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Not able to show all students";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        try {
            if (studentService.deleteById(id)){
                return new ResponseEntity<>("Student was deleted with success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Student does not exist", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            String errorMessage = ERROR_MESSAGE + "Can't delete student with id" + id;
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAll() {
        try {
            studentService.deleteAll();
            return new ResponseEntity<>("All students were deleted.", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Can not delete all students";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody StudentDto studentDto, @PathVariable Integer id) {
        try {
            studentService.update(studentDto, id);
            return new ResponseEntity<>("Student was updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Could not update student";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("{studentId}/register/course/{courseId}")
    public ResponseEntity<Object> registerIntoCourse(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        try {
            studentService.registerIntoCourse(studentId, courseId);
            return new ResponseEntity<>("Student was successfully registered in course with id: " + courseId, HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Not possible to register student into course.";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{studentId}/register/subject/{subjectId}")
    public ResponseEntity<Object> registerIntoSubject(@PathVariable Integer studentId, @PathVariable Integer subjectId) {
        try {
            studentService.registerIntoSubject(studentId, subjectId);
            return new ResponseEntity<>("Student was successfully registered in subject with id: " + subjectId, HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Not possible to register student into subject.";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("course/{id}")
    public ResponseEntity<Object> findCourse(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(studentService.findCourse(id), HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Not possible to find course for student with id: " + id;
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("subjects/{id}")
    public ResponseEntity<Object> findAllSubjects(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(studentService.findAllSubjects(id), HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Not possible to find subjects for student with id: " + id;
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
