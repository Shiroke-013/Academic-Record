package com.java.controller;

import com.java.dto.CourseDto;
import com.java.dto.GradeDto;
import com.java.dto.SubjectDto;
import com.java.dto.TeacherDto;
import com.java.service.CourseService;
import com.java.service.ExceptionService;
import com.java.service.SubjectService;
import com.java.service.TeacherService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
class CourseControllerTest {

    private static final String COURSE_CONTROLLER_PATH = "/course/";
    private static final String ERROR_MESSAGE = "An error occurred: ";
    private static final String NO_COURSE_FOUND = "No course found with id: ";
    private static final String NOT_TEACHERS_FOUND = "No teachers found in the course";
    private MockMvc mockMvc;

    @InjectMocks
    private CourseController courseController;

    @Mock
    private CourseService courseService;

    @Mock
    private TeacherService teacherService;

    @Mock
    private SubjectService subjectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @DisplayName("Create Course")
    @Test
    void save_ValidCourseDto_ReturnsCreatedResponse() throws Exception {

        doNothing().when(courseService).save(ArgumentMatchers.any(CourseDto.class));
        JSONObject courseJson = new JSONObject()
                .put("courseName","Computer Science")
                .put("numberOfStudents",0)
                .put("courseStart","2023-01-13")
                .put("courseEnd","2023-05-13");


        MvcResult mvcResult = mockMvc.perform(post(COURSE_CONTROLLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(courseJson)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Course was successfully created."))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
        assertEquals("Course was successfully created.", mvcResult.getResponse().getContentAsString());
        verify(courseService, times(1)).save(ArgumentMatchers.any(CourseDto.class));
    }

    @DisplayName("Create Course Exception")
    @Test
    void save_ExceptionThrown_ReturnsInternalServerError() throws Exception{

        doThrow(new ExceptionService("Database connection error")).when(courseService).save(ArgumentMatchers.any(CourseDto.class));
        JSONObject courseJson = new JSONObject();

        MvcResult mvcResult = mockMvc.perform(post(COURSE_CONTROLLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(courseJson)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(ERROR_MESSAGE + "Unable to create course"))
                .andReturn();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus());
        assertEquals(ERROR_MESSAGE + "Unable to create course", mvcResult.getResponse().getContentAsString());
        verify(courseService, times(1)).save(ArgumentMatchers.any(CourseDto.class));
    }

    @DisplayName("Find Course by Id")
    @Test
    void findById_ExistingCourse_ReturnsOkResponse() throws Exception {
        Integer courseId = 1;
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");
        courseDto.setNumberOfStudents(0);
        courseDto.setCourseStart(LocalDate.parse("2023-01-13"));
        courseDto.setCourseEnd(LocalDate.parse("2023-05-13"));

        when(courseService.findById(courseId)).thenReturn(courseDto);

        mockMvc.perform(get(COURSE_CONTROLLER_PATH + courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Computer Science"))
                .andExpect(jsonPath("$.numberOfStudents").value(0));
        ResponseEntity<Object> response = courseController.findById(courseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courseDto, response.getBody());
        verify(courseService, times(2)).findById(courseId);
    }

    @DisplayName("Find Course with Nonexistent Id")
    @Test
    void findById_NonExistingId_ReturnsNotFoundResponse() throws Exception {
        Integer courseId = 12;
        when(courseService.findById(courseId)).thenReturn(null);

        mockMvc.perform(get(COURSE_CONTROLLER_PATH + courseId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No course found"));
        ResponseEntity<Object> response = courseController.findById(courseId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No course found", response.getBody());
        verify(courseService, times(2)).findById(courseId);
    }

    @DisplayName("Find Course Exception")
    @Test
    void findById_ExceptionThrown_ReturnsInternalServerErrorResponse() throws Exception {
        Integer courseId = 19;
        when(courseService.findById(courseId)).thenThrow(new ExceptionService("Database connection error"));

        mockMvc.perform(get(COURSE_CONTROLLER_PATH + courseId))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ERROR_MESSAGE + "Unable to find the course with id: " + courseId));
        ResponseEntity<Object> response = courseController.findById(courseId);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Unable to find the course with id: " + courseId, response.getBody());
        verify(courseService, times(2)).findById(courseId);
    }

    @DisplayName("Find All Courses")
    @Test
    void findAll_ReturnsListOfCoursesAndOkResponse() throws Exception {
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");
        courseDto.setNumberOfStudents(0);
        courseDto.setCourseStart(LocalDate.parse("2023-01-13"));
        courseDto.setCourseEnd(LocalDate.parse("2023-05-13"));
        CourseDto courseDto2 = new CourseDto();
        courseDto2.setCourseName("Chemistry");
        courseDto2.setNumberOfStudents(17);
        courseDto2.setCourseStart(LocalDate.parse("2023-01-20"));
        courseDto2.setCourseEnd(LocalDate.parse("2023-06-01"));

        List<CourseDto> courseDtos = new ArrayList<>();
        courseDtos.add(courseDto);
        courseDtos.add(courseDto2);
        when(courseService.findAll()).thenReturn(courseDtos);

        mockMvc.perform(get(COURSE_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseName").value("Computer Science"))
                .andExpect(jsonPath("$[0].numberOfStudents").value(0))
                .andExpect(jsonPath("$[1].courseName").value("Chemistry"))
                .andExpect(jsonPath("$[1].numberOfStudents").value(17))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(courseService, times(1)).findAll();
    }

    @DisplayName("Not Courses Found")
    @Test
    void findAll_ReturnsNotFoundResponse() throws Exception {
        List<GradeDto> courseDtos = new ArrayList<>();
        when(courseService.findAll()).thenReturn(courseDtos);

        mockMvc.perform(get(COURSE_CONTROLLER_PATH))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No courses found"));

        ResponseEntity<Object> response = courseController.findAll();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No courses found", response.getBody());
        verify(courseService, times(2)).findAll();
    }

    @DisplayName("Find All Courses Exception")
    @Test
    void findAll_ReturnsInternalServerErrorResponse() throws Exception {
        when(courseService.findAll()).thenThrow(new ExceptionService("Database connection error."));

        mockMvc.perform(get(COURSE_CONTROLLER_PATH))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ERROR_MESSAGE + "Unable to find all courses"));

        verify(courseService, times(1)).findAll();
    }

    @DisplayName("Delete Course by Id")
    @Test
    void delete_ExistingCourse_ReturnsOkResponse() throws Exception {
        Integer courseId = 1;
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");
        courseDto.setNumberOfStudents(0);
        courseDto.setCourseStart(LocalDate.parse("2023-01-13"));
        courseDto.setCourseEnd(LocalDate.parse("2023-05-13"));

        when(courseService.findById(courseId)).thenReturn(courseDto);
        doNothing().when(courseService).deleteById(courseId);

        mockMvc.perform(delete(COURSE_CONTROLLER_PATH + courseId))
                .andExpect(status().isOk())
                .andExpect(content().string("Course deleted with success"));

        ResponseEntity<Object> response = courseController.delete(courseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Course deleted with success", response.getBody());
        verify(courseService, times(2)).findById(courseId);
    }

    @DisplayName("Delete Course with Nonexistent Id")
    @Test
    void delete_NonexistentCourse_ReturnsNotFoundResponse() throws Exception {
        Integer courseId = 1;

        when(courseService.findById(courseId)).thenReturn(null);
        doNothing().when(courseService).deleteById(courseId);

        mockMvc.perform(delete(COURSE_CONTROLLER_PATH + courseId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(NO_COURSE_FOUND + courseId));

        ResponseEntity<Object> response = courseController.delete(courseId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(NO_COURSE_FOUND + courseId, response.getBody());
        verify(courseService, times(2)).findById(courseId);
    }

    @DisplayName("Delete Course by Id Exception")
    @Test
    void delete_ExceptionThrown_ReturnsUnprocessableEntityResponse() throws Exception {
        Integer courseId = 1;
        CourseDto courseDto = new CourseDto();
        when(courseService.findById(courseId)).thenReturn(courseDto);
        doThrow(new ExceptionService("Database connection error")).when(courseService).deleteById(courseId);

        mockMvc.perform(delete(COURSE_CONTROLLER_PATH + courseId))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ERROR_MESSAGE + "Unable to delete course with id: " + courseId));

        ResponseEntity<Object> response = courseController.delete(courseId);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Unable to delete course with id: " + courseId, response.getBody());
        verify(courseService, times(2)).findById(courseId);
    }

    @DisplayName("Delete All Courses")
    @Test
    void deleteAll_ReturnsAcceptedResponse() throws Exception {
        doNothing().when(courseService).deleteAll();

        mockMvc.perform(delete(COURSE_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string("Courses deleted with success"));

        ResponseEntity<Object> response = courseController.deleteAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Courses deleted with success", response.getBody());
        verify(courseService, times(2)).deleteAll();
    }

    @DisplayName("Delete All Courses")
    @Test
    void deleteAll_ExceptionThrown_ReturnsUnprocessableEntityResponse() throws Exception {
        doThrow(new ExceptionService("Database connection error")).when(courseService).deleteAll();

        mockMvc.perform(delete(COURSE_CONTROLLER_PATH))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ERROR_MESSAGE + "Unable to delete all courses"));

        ResponseEntity<Object> response = courseController.deleteAll();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Unable to delete all courses", response.getBody());
        verify(courseService, times(2)).deleteAll();
    }

    @DisplayName("Update Course")
    @Test
    void update_ReturnsOkResponse() throws Exception {
        Integer id = 1;
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");
        JSONObject courseJson = new JSONObject()
                .put("courseName","Computer Science");
        when(courseService.update(courseDto, id)).thenReturn(true);

        ResponseEntity<Object> response = courseController.update(courseDto, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Course updated with success", response.getBody());
        verify(courseService, times(1)).update(courseDto, id);
    }

    @DisplayName("Update No Course Found")
    @Test
    void update_NonexistentCourse_ReturnsNotFoundResponse() throws Exception {
        Integer id = 90;
        CourseDto courseDto = new CourseDto();
        courseDto.setNumberOfStudents(13);
        JSONObject courseJson = new JSONObject()
                .put("numberOfStudents",13);
        when(courseService.update(courseDto, id)).thenReturn(false);

        mockMvc.perform(patch(COURSE_CONTROLLER_PATH + id)
                        .content(String.valueOf(courseJson))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(NO_COURSE_FOUND + id));

        ResponseEntity<Object> response = courseController.update(courseDto, id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(NO_COURSE_FOUND + id, response.getBody());
        verify(courseService, times(1)).update(courseDto, id);
    }

    @DisplayName("Update Course Exception")
    @Test
    void update_ExceptionThrown_ReturnsUnprocessableEntityResponse() throws Exception {
        Integer id = 1;
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");
        JSONObject courseJson = new JSONObject()
                .put("courseName","Computer Science");
        when(courseService.update(courseDto, id)).thenThrow(new ExceptionService("Database connection error"));

        ResponseEntity<Object> response = courseController.update(courseDto, id);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Unable to update course", response.getBody());
        verify(courseService, times(1)).update(courseDto, id);
    }

    @DisplayName("Find Course Teachers")
    @Test
    void findTeacher_ExistingTeachers_ReturnsOkResponse() throws Exception {
        Integer courseId = 1;
        List<String> teacherNames = new ArrayList<>();
        teacherNames.add("Teacher One");
        teacherNames.add("Teacher Two");

        when(courseService.findTeachers(courseId)).thenReturn(teacherNames);

        mockMvc.perform(get(COURSE_CONTROLLER_PATH + courseId + "/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Teacher One"))
                .andExpect(jsonPath("$[1]").value("Teacher Two"))
                .andReturn();

        ResponseEntity<Object> response = courseController.findTeachers(courseId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teacherNames, response.getBody());
        verify(courseService, times(2)).findTeachers(courseId);
    }

    @DisplayName("Not Teachers Found in Course")
    @Test
    void findTeacher_NonExistentTeachers_ReturnsNotFoundResponse() throws Exception {
        Integer courseId = 1;
        List<String> teacherNames = new ArrayList<>();

        when(courseService.findTeachers(courseId)).thenReturn(teacherNames);

        mockMvc.perform(get(COURSE_CONTROLLER_PATH + courseId + "/teachers"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(NOT_TEACHERS_FOUND))
                .andReturn();

        ResponseEntity<Object> response = courseController.findTeachers(courseId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(NOT_TEACHERS_FOUND, response.getBody());
        verify(courseService, times(2)).findTeachers(courseId);
    }

    @DisplayName("Find Course Teachers Exception")
    @Test
    void findTeacher_ExceptionThrown_ReturnsUnprocessableEntityResponse() throws Exception {
        Integer courseId = 1;
        List<String> teacherNames = new ArrayList<>();

        when(courseService.findTeachers(courseId)).thenThrow(new ExceptionService("Database connection error"));

        mockMvc.perform(get(COURSE_CONTROLLER_PATH + courseId + "/teachers"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ERROR_MESSAGE + "Unable to find teachers from course"))
                .andReturn();

        ResponseEntity<Object> response = courseController.findTeachers(courseId);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Unable to find teachers from course", response.getBody());
        verify(courseService, times(2)).findTeachers(courseId);
    }

    @DisplayName("Find Course Students")
    @Test
    void findStudents_ExistingStudents_ReturnsOkResponse() throws Exception {
        Integer courseId = 1;
        List<String> studentsNames = new ArrayList<>();
        studentsNames.add("Student One");
        studentsNames.add("Student Two");

        when(courseService.findStudents(courseId)).thenReturn(studentsNames);

        mockMvc.perform(get(COURSE_CONTROLLER_PATH + courseId + "/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Student One"))
                .andExpect(jsonPath("$[1]").value("Student Two"))
                .andReturn();

        ResponseEntity<Object> response = courseController.findStudents(courseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentsNames, response.getBody());
        verify(courseService, times(2)).findStudents(courseId);
    }

    @DisplayName("Not Students Found in Course")
    @Test
    void findStudents_NonExistentStudents_ReturnsNotFoundResponse() throws Exception {
        Integer courseId = 1;
        List<String> studentsNames = new ArrayList<>();

        when(courseService.findStudents(courseId)).thenReturn(studentsNames);

        mockMvc.perform(get(COURSE_CONTROLLER_PATH + courseId + "/students"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(NOT_TEACHERS_FOUND))
                .andReturn();

        ResponseEntity<Object> response = courseController.findStudents(courseId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(NOT_TEACHERS_FOUND, response.getBody());
        verify(courseService, times(2)).findStudents(courseId);
    }

    @DisplayName("Find Course Students Exception")
    @Test
    void findStudents_ExceptionThrown_ReturnsUnprocessableEntityResponse() throws Exception {
        Integer courseId = 1;

        when(courseService.findStudents(courseId)).thenThrow(new ExceptionService("Database connection error"));

        mockMvc.perform(get(COURSE_CONTROLLER_PATH + courseId + "/students"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ERROR_MESSAGE + "Unable to find students from course"))
                .andReturn();

        ResponseEntity<Object> response = courseController.findStudents(courseId);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Unable to find students from course", response.getBody());
        verify(courseService, times(2)).findStudents(courseId);
    }

    @DisplayName("Find Course Subjects")
    @Test
    void findSubjects_ExistingSubjects_ReturnsOkResponse() throws Exception {
        Integer courseId = 1;
        List<String> subjectNames = new ArrayList<>();
        subjectNames.add("Subject One");
        subjectNames.add("Subject Two");

        when(courseService.findSubjects(courseId)).thenReturn(subjectNames);

        mockMvc.perform(get(COURSE_CONTROLLER_PATH + courseId + "/subjects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Subject One"))
                .andExpect(jsonPath("$[1]").value("Subject Two"))
                .andReturn();

        ResponseEntity<Object> response = courseController.findSubjects(courseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subjectNames, response.getBody());
        verify(courseService, times(2)).findSubjects(courseId);
    }

    @DisplayName("Subjects Not Found in Course")
    @Test
    void findSubjects_NonExistentSubjects_ReturnsNotFoundResponse() throws Exception {
        Integer courseId = 1;
        List<String> subjectNames = new ArrayList<>();

        when(courseService.findSubjects(courseId)).thenReturn(subjectNames);

        mockMvc.perform(get(COURSE_CONTROLLER_PATH + courseId + "/subjects"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No subjects found in course"))
                .andReturn();

        ResponseEntity<Object> response = courseController.findSubjects(courseId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No subjects found in course", response.getBody());
        verify(courseService, times(2)).findSubjects(courseId);
    }

    @DisplayName("Find Course Subjects Exception")
    @Test
    void findSubjects_ExceptionThrown_ReturnsUnprocessableEntityResponse() throws Exception {
        Integer courseId = 1;

        when(courseService.findSubjects(courseId)).thenThrow(new ExceptionService("Database connection error"));

        mockMvc.perform(get(COURSE_CONTROLLER_PATH + courseId + "/subjects"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ERROR_MESSAGE + "Unable to find subjects from course"))
                .andReturn();

        ResponseEntity<Object> response = courseController.findSubjects(courseId);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Unable to find subjects from course", response.getBody());
        verify(courseService, times(2)).findSubjects(courseId);
    }

    @DisplayName("Delete Teacher from Course")
    @Test
    void deleteTeacherFromCourse_ReturnsOkResponse() throws Exception {
        Integer courseId = 1;
        Integer teacherId = 1;
        when(courseService.deleteTeacherFromCourse(courseId, teacherId)).thenReturn(true);

        mockMvc.perform(delete(COURSE_CONTROLLER_PATH + courseId + "/teacher/" + teacherId))
                .andExpect(status().isOk())
                .andExpect(content().string("Teacher deleted with success from course"))
                .andReturn();

        ResponseEntity<Object> response = courseController.deleteTeacherFromCourse(courseId, teacherId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Teacher deleted with success from course", response.getBody());
        verify(courseService, times(2)).deleteTeacherFromCourse(courseId, teacherId);
    }

    @DisplayName("No Teacher or Course Found when deleting Teacher from Course")
    @Test
    void deleteTeacherFromCourse_NoTeacherOrCourseFound_ReturnsNotFoundResponse() throws Exception {
        Integer courseId = 1;
        Integer teacherId = 1;
        when(courseService.deleteTeacherFromCourse(courseId, teacherId)).thenReturn(false);

        mockMvc.perform(delete(COURSE_CONTROLLER_PATH + courseId + "/teacher/" + teacherId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Not course or teacher found"))
                .andReturn();

        ResponseEntity<Object> response = courseController.deleteTeacherFromCourse(courseId, teacherId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not course or teacher found", response.getBody());
        verify(courseService, times(2)).deleteTeacherFromCourse(courseId, teacherId);
    }

    @DisplayName("Delete Teacher from Course Exception")
    @Test
    void deleteTeacherFromCourse_ExceptionThrown_ReturnsUnprocessableEntityResponse() throws Exception {
        Integer courseId = 1;
        Integer teacherId = 1;
        when(courseService.deleteTeacherFromCourse(courseId, teacherId)).thenThrow(new ExceptionService("Database connection error"));

        mockMvc.perform(delete(COURSE_CONTROLLER_PATH + courseId + "/teacher/" + teacherId))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ERROR_MESSAGE + "Unable to delete teacher(id: " + teacherId + ")  from course."))
                .andReturn();

        ResponseEntity<Object> response = courseController.deleteTeacherFromCourse(courseId, teacherId);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Unable to delete teacher(id: " + teacherId + ")  from course.", response.getBody());
        verify(courseService, times(2)).deleteTeacherFromCourse(courseId, teacherId);
    }

    @DisplayName("Add teacher to Course")
    @Test
    void addTeacher_ExistingCourseAndTeacher_ReturnsOkResponse() throws Exception {
        Integer courseId = 1;
        Integer teacherId = 1;
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");
        courseDto.setNumberOfStudents(0);
        courseDto.setCourseStart(LocalDate.parse("2023-01-13"));
        courseDto.setCourseEnd(LocalDate.parse("2023-05-13"));
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setFirstName("Pedro");
        teacherDto.setLastName("Quintero");
        teacherDto.setEmail("pq@ar.com");
        teacherDto.setPassword("!TestPedroEcho3");
        when(courseService.findById(courseId)).thenReturn(courseDto);
        when(teacherService.findById(teacherId)).thenReturn(teacherDto);
        doNothing().when(courseService).addTeacher(courseId, teacherId);

        mockMvc.perform(patch(COURSE_CONTROLLER_PATH + courseId + "/add/teacher/" + teacherId))
                .andExpect(status().isOk())
                .andExpect(content().string("Teacher added to course with success"))
                .andReturn();
        ResponseEntity<Object> response = courseController.addTeacher(courseId, teacherId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Teacher added to course with success", response.getBody());
        verify(courseService, times(2)).addTeacher(courseId, teacherId);
    }

    @DisplayName("Add teacher to Course - Nonexistent Course")
    @Test
    void addTeacher_NonExistingCourse_ReturnsNotFoundResponse() throws Exception {
        Integer courseId = 1;
        Integer teacherId = 1;
        when(courseService.findById(courseId)).thenReturn(null);

        mockMvc.perform(patch(COURSE_CONTROLLER_PATH + courseId + "/add/teacher/" + teacherId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(NO_COURSE_FOUND + courseId))
                .andReturn();
        ResponseEntity<Object> response = courseController.addTeacher(courseId, teacherId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(NO_COURSE_FOUND + courseId, response.getBody());
        verify(courseService, times(2)).findById(courseId);
    }

    @DisplayName("Add teacher to Course - Nonexistent Teacher")
    @Test
    void addTeacher_NonExistingTeacher_ReturnsNotFoundResponse() throws Exception {
        Integer courseId = 1;
        Integer teacherId = 1;
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");
        courseDto.setNumberOfStudents(0);
        courseDto.setCourseStart(LocalDate.parse("2023-01-13"));
        courseDto.setCourseEnd(LocalDate.parse("2023-05-13"));

        when(courseService.findById(courseId)).thenReturn(courseDto);
        when(teacherService.findById(teacherId)).thenReturn(null);

        mockMvc.perform(patch(COURSE_CONTROLLER_PATH + courseId + "/add/teacher/" + teacherId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No teacher found with id: " + teacherId))
                .andReturn();
        ResponseEntity<Object> response = courseController.addTeacher(courseId, teacherId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No teacher found with id: " + teacherId, response.getBody());
        verify(teacherService, times(2)).findById(teacherId);
    }

    @DisplayName("Add teacher to Course Exception")
    @Test
    void addTeacher_ExceptionThrown_ReturnsUnprocessableEntityResponse() throws Exception {
        Integer courseId = 1;
        Integer teacherId = 1;
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");
        courseDto.setNumberOfStudents(0);
        courseDto.setCourseStart(LocalDate.parse("2023-01-13"));
        courseDto.setCourseEnd(LocalDate.parse("2023-05-13"));
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setFirstName("Pedro");
        teacherDto.setLastName("Quintero");
        teacherDto.setEmail("pq@ar.com");
        teacherDto.setPassword("!TestPedroEcho3");
        when(courseService.findById(courseId)).thenReturn(courseDto);
        when(teacherService.findById(teacherId)).thenReturn(teacherDto);
        doThrow(new ExceptionService("Database connection error")).when(courseService).addTeacher(courseId, teacherId);

        mockMvc.perform(patch(COURSE_CONTROLLER_PATH + courseId + "/add/teacher/" + teacherId))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ERROR_MESSAGE + "Unable to add teacher with id: " + teacherId + " to course."))
                .andReturn();
        ResponseEntity<Object> response = courseController.addTeacher(courseId, teacherId);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Unable to add teacher with id: " + teacherId + " to course.", response.getBody());
        verify(courseService, times(2)).findById(courseId);
        verify(teacherService, times(2)).findById(teacherId);
        verify(courseService, times(2)).addTeacher(courseId, teacherId);
    }

    @DisplayName("Add Subject to Course")
    @Test
    void addSubject_ExistingCourseAndSubject_ReturnsOkResponse() throws Exception {
        Integer courseId = 1;
        Integer subjectId = 1;
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");
        courseDto.setNumberOfStudents(0);
        courseDto.setCourseStart(LocalDate.parse("2023-01-13"));
        courseDto.setCourseEnd(LocalDate.parse("2023-05-13"));
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSubjectName("Subject1");
        subjectDto.setCapacity(30);
        subjectDto.setDuration(80);
        when(courseService.findById(courseId)).thenReturn(courseDto);
        when(subjectService.findById(subjectId)).thenReturn(subjectDto);
        doNothing().when(courseService).addSubject(courseId, subjectId);

        mockMvc.perform(patch(COURSE_CONTROLLER_PATH + courseId + "/add/subject/" + subjectId))
                .andExpect(status().isOk())
                .andExpect(content().string("Subject added to course with success"))
                .andReturn();
        ResponseEntity<Object> response = courseController.addSubject(courseId, subjectId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Subject added to course with success", response.getBody());
        verify(courseService, times(2)).addSubject(courseId, subjectId);
    }

    @DisplayName("Add Subject to Course - Nonexistent Course")
    @Test
    void addSubject_NonExistingCourse_ReturnsNotFoundResponse() throws Exception {
        Integer courseId = 1;
        Integer subjectId = 1;
        when(courseService.findById(courseId)).thenReturn(null);

        mockMvc.perform(patch(COURSE_CONTROLLER_PATH + courseId + "/add/subject/" + subjectId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(NO_COURSE_FOUND + courseId))
                .andReturn();
        ResponseEntity<Object> response = courseController.addSubject(courseId, subjectId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(NO_COURSE_FOUND + courseId, response.getBody());
        verify(courseService, times(2)).findById(courseId);
    }

    @DisplayName("Add Subject to Course - Nonexistent Subject")
    @Test
    void addSubject_NonExistingSubject_ReturnsNotFoundResponse() throws Exception {
        Integer courseId = 1;
        Integer subjectId = 1;
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");
        courseDto.setNumberOfStudents(0);
        courseDto.setCourseStart(LocalDate.parse("2023-01-13"));
        courseDto.setCourseEnd(LocalDate.parse("2023-05-13"));

        when(courseService.findById(courseId)).thenReturn(courseDto);
        when(subjectService.findById(subjectId)).thenReturn(null);

        mockMvc.perform(patch(COURSE_CONTROLLER_PATH + courseId + "/add/subject/" + subjectId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No subject found with id: " + subjectId))
                .andReturn();
        ResponseEntity<Object> response = courseController.addSubject(courseId, subjectId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No subject found with id: " + subjectId, response.getBody());
        verify(subjectService, times(2)).findById(subjectId);
    }

    @DisplayName("Add Subject to Course Exception")
    @Test
    void addSubject_ExceptionThrown_ReturnsUnprocessableEntityResponse() throws Exception {
        Integer courseId = 1;
        Integer subjectId = 1;
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");
        courseDto.setNumberOfStudents(0);
        courseDto.setCourseStart(LocalDate.parse("2023-01-13"));
        courseDto.setCourseEnd(LocalDate.parse("2023-05-13"));
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSubjectName("Subject1");
        subjectDto.setCapacity(30);
        subjectDto.setDuration(80);
        when(courseService.findById(courseId)).thenReturn(courseDto);
        when(subjectService.findById(subjectId)).thenReturn(subjectDto);
        doThrow(new ExceptionService("Database connection error")).when(courseService).addSubject(courseId, subjectId);

        mockMvc.perform(patch(COURSE_CONTROLLER_PATH + courseId + "/add/subject/" + subjectId))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ERROR_MESSAGE + "Unable to add subject with id " + subjectId + " to course."))
                .andReturn();
        ResponseEntity<Object> response = courseController.addSubject(courseId, subjectId);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Unable to add subject with id " + subjectId + " to course.", response.getBody());
        verify(courseService, times(2)).findById(courseId);
        verify(subjectService, times(2)).findById(subjectId);
        verify(courseService, times(2)).addSubject(courseId, subjectId);
    }
}
