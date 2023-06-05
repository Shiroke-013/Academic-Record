package com.java.controller;

import com.java.dto.CourseDto;
import com.java.service.CourseService;
import com.java.service.SubjectService;
import com.java.service.TeacherService;
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

@RequestMapping("/course/")
@RestController
public class CourseController {

    private static final String ERROR_MESSAGE = "An error occurred: ";
    private static final String NOT_TEACHERS_FOUND = "No teachers found in the course";
    private static final String NO_COURSE_FOUND = "No course found with id: ";

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    ResponseEntity<Object> save(@Valid @RequestBody CourseDto subjectDto) {
        try {
            courseService.save(subjectDto);
            return new ResponseEntity<>("Course was successfully created.",HttpStatus.CREATED);
        } catch (Exception e){
            String errorMessage = ERROR_MESSAGE + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    ResponseEntity<Object> findById(@PathVariable Integer id) {
        try {
            CourseDto courseDto = (CourseDto) courseService.findById(id);
            if (courseDto != null) {
                return new ResponseEntity<>(courseDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No course found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            String errorMessage = ERROR_MESSAGE + "Unable to find the course with id: " + id;
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping
    ResponseEntity<Object> findAll() {
        try {
            Collection courses = courseService.findAll();
            if (courses.isEmpty()){
                return new ResponseEntity<>("No courses found", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(courses, HttpStatus.OK);
            }
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Unable to find all courses";
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("{id}")
    ResponseEntity<Object> delete(@PathVariable Integer id) {
        try {
            CourseDto courseDto = (CourseDto) courseService.findById(id);
            if (courseDto != null) {
                courseService.deleteById(id);
                return new ResponseEntity<>("Course deleted with success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(NO_COURSE_FOUND + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            String errorMessage = ERROR_MESSAGE + "Unable to delete course with id: " + id;
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping
    ResponseEntity<Object> deleteAll() {
        try {
            courseService.deleteAll();
            return new ResponseEntity<>("Courses deleted with success", HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Unable to delete all courses";
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PatchMapping("{id}")
    ResponseEntity<Object> update(@Valid @RequestBody CourseDto subjectDto, @PathVariable Integer id) {
        try {
            if (courseService.update(subjectDto, id)){
                return new ResponseEntity<>("Course updated with success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(NO_COURSE_FOUND + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Unable to update course";
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("{id}/teachers")
    ResponseEntity<Object> findTeachers(@PathVariable Integer id) {
        try {
            Collection teachers = courseService.findTeachers(id);
            if (teachers.isEmpty()){
                return new ResponseEntity<>(NOT_TEACHERS_FOUND, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(teachers, HttpStatus.OK);
            }
        } catch (Exception e) {
            java.lang.String errorMessage = ERROR_MESSAGE + "Unable to find teachers from course";
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("{id}/students")
    ResponseEntity<Object> findStudents(@PathVariable Integer id) {
        try {
            Collection students = courseService.findStudents(id);
            if (students.isEmpty()){
                return new ResponseEntity<>(NOT_TEACHERS_FOUND, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(students, HttpStatus.OK);
            }
        } catch (Exception e) {
            java.lang.String errorMessage = ERROR_MESSAGE + "Unable to find students from course";
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("{id}/subjects")
    ResponseEntity<Object> findSubjects(@PathVariable Integer id) {
        try {
            Collection courses = courseService.findSubjects(id);
            if (courses.isEmpty()){
                return new ResponseEntity<>("No subjects found in course", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(courses, HttpStatus.OK);
            }
        } catch (Exception e) {
            java.lang.String errorMessage = ERROR_MESSAGE + "Unable to find subjects from course";
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("{courseId}/teacher/{teacherId}")
    ResponseEntity<Object> deleteTeacherFromCourse(@PathVariable Integer courseId, @PathVariable Integer teacherId) {
        try {
            if (courseService.deleteTeacherFromCourse(courseId, teacherId)) {
                return new ResponseEntity<>("Teacher deleted with success from course", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Not course or teacher found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Unable to delete teacher(id: " + teacherId + ")  from course.";
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PatchMapping("{courseId}/add/teacher/{teacherId}")
    ResponseEntity<Object> addTeacher(@PathVariable Integer courseId, @PathVariable Integer teacherId) {
        try {
            if (courseService.findById(courseId) == null) {
                return new ResponseEntity<>(NO_COURSE_FOUND + courseId, HttpStatus.NOT_FOUND);
            } else if (teacherService.findById(teacherId) == null) {
                return new ResponseEntity<>("No teacher found with id: " + teacherId, HttpStatus.NOT_FOUND);
            } else {
                courseService.addTeacher(courseId, teacherId);
                return new ResponseEntity<>("Teacher added to course with success", HttpStatus.OK);
            }
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Unable to add teacher with id: " + teacherId + " to course.";
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PatchMapping("{courseId}/add/subject/{subjectId}")
    ResponseEntity<Object> addSubject(@PathVariable Integer courseId, @PathVariable Integer subjectId) {
        try {
            if (courseService.findById(courseId) == null) {
                return new ResponseEntity<>(NO_COURSE_FOUND + courseId, HttpStatus.NOT_FOUND);
            } else if (subjectService.findById(subjectId) == null) {
                return new ResponseEntity<>("No subject found with id: " + subjectId, HttpStatus.NOT_FOUND);
            } else {
                courseService.addSubject(courseId, subjectId);
                return new ResponseEntity<>("Subject added to course with success", HttpStatus.OK);
            }
        } catch (Exception e) {
            String errorMessage = ERROR_MESSAGE + "Unable to add subject with id " + subjectId + " to course.";
            return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
