package com.java.controller;


import com.java.dto.*;
import com.java.model.Teacher;
import com.java.service.ExceptionService;
import com.java.service.StudentService;
import com.java.service.SubjectService;
import com.java.service.TeacherService;
import org.hibernate.service.spi.ServiceException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
class TeacherControllerTest {

    private static final String TEACHER_CONTROLLER_PATH = "/teacher/";
    private static final String SOMETHING_HAPPENED = "Something happened";
    private static final String ERROR_MESSAGE = "An error occurred: ";

    @InjectMocks
    private TeacherController teacherController;

    @Mock
    private TeacherService teacherService;

    @Mock
    private StudentService studentService;

    @Mock
    private SubjectService subjectService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(teacherController).build();
    }

    @DisplayName("Get All Teachers")
    @Test
    void  getAll_shouldBringAllTeachers() throws Exception {

        Teacher teacher1 = new Teacher();
        teacher1.setId(1);
        teacher1.setFirstName("Andres");
        teacher1.setLastName("Chaves");
        teacher1.setEmail("andres@kai.com");
        teacher1.setPassword("1234567890");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2);
        teacher2.setFirstName("David");
        teacher2.setLastName("Chaves");
        teacher2.setEmail("david@kai.com");
        teacher2.setPassword("12345678901");

        List<Teacher> teachers = new ArrayList<>();

        teachers.add(teacher1);
        teachers.add(teacher2);

        when(teacherService.findAll()).thenReturn(teachers);

        mockMvc.perform(MockMvcRequestBuilders.get(TEACHER_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andReturn();

        verify(teacherService, atLeastOnce()).findAll();
    }

    @DisplayName("Get All Teacher Exception")
    @Test
    void getAllTeachersExceptionTest() throws Exception {

        when(teacherService.findAll()).thenThrow(new ExceptionService(SOMETHING_HAPPENED));

        mockMvc.perform(MockMvcRequestBuilders.get(TEACHER_CONTROLLER_PATH))
                .andExpect(status().isInternalServerError());

        verify(teacherService, atLeastOnce()).findAll();
    }

    @DisplayName("Get Teacher by Id")
    @Test
    void findByIdTeacherTest() throws Exception {

        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setFirstName("Andres");
        teacher.setLastName("Chaves");
        teacher.setEmail("andres@kai.com");
        teacher.setPassword("1234567890");


        when(teacherService.findById(1)).thenReturn(teacher);

        mockMvc.perform(MockMvcRequestBuilders.get(TEACHER_CONTROLLER_PATH + "1"))
                .andExpect(status().isOk());

        verify(teacherService, atLeastOnce()).findById(1);
    }

    @DisplayName("Get Teacher by Id Exception")
    @Test
    void findByIdTeacherExceptionTest() throws Exception {

        when(teacherService.findById(1)).thenThrow(new ExceptionService(SOMETHING_HAPPENED));

        mockMvc.perform(MockMvcRequestBuilders.get(TEACHER_CONTROLLER_PATH + "1"))
                .andExpect(status().isUnprocessableEntity());

        verify(teacherService, atLeastOnce()).findById(1);
    }

    @DisplayName("Creating Teacher")
    @Test
    void saveTeacherTest() throws Exception {

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setFirstName("Andres");
        teacherDto.setLastName("Chaves");
        teacherDto.setEmail("andres@kai.com");
        teacherDto.setPassword("1234567890");

        when(teacherService.save(teacherDto)).thenReturn(new ResponseEntity<HttpStatus>(HttpStatus.CREATED));
        assertEquals(new ResponseEntity<HttpStatus>(HttpStatus.CREATED), teacherService.save(teacherDto));

        JSONObject teacherJSON = new JSONObject()
                .put("id",1)
                .put("firstName", "Andres")
                .put("lastName", "Chaves")
                .put("email", "andres@kai.com")
                .put("password", "1234567890");


        mockMvc.perform(MockMvcRequestBuilders.post(TEACHER_CONTROLLER_PATH)
                        .content(String.valueOf(teacherJSON))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(teacherService, atLeastOnce()).save(teacherDto);
    }

    @DisplayName("Creating Teacher Exception")
    @Test
    void save_ExceptionThrown_ReturnsInternalServerError() throws Exception {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setFirstName("Andres");
        teacherDto.setLastName("Chaves");
        teacherDto.setEmail("andres@kai.com");
        teacherDto.setPassword("1234567890");

        when(teacherService.save(teacherDto)).thenThrow(new ExceptionService("Database connection error"));

        ResponseEntity<Object> response = teacherController.save(teacherDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "We are unable to create the teacher", response.getBody());
        verify(teacherService, times(1)).save(teacherDto);

        verify(teacherService, atLeastOnce()).save(teacherDto);
    }

    @DisplayName("Delete Teacher By Id")
    @Test
    void deleteTeacherByIdTest() throws Exception {

        doNothing().when(teacherService).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete(TEACHER_CONTROLLER_PATH + "1"))
                .andExpect(status().isAccepted());

        verify(teacherService, atLeastOnce()).deleteById(1);
    }

    @DisplayName("Delete Teacher By Id Exception")
    @Test
    void deleteTeacherByIdExceptionTest() throws Exception {

        doThrow(new SecurityException(SOMETHING_HAPPENED)).when(teacherService).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete(TEACHER_CONTROLLER_PATH + "1"))
                .andExpect(status().isUnprocessableEntity());

        verify(teacherService, atLeastOnce()).deleteById(1);
    }

    @DisplayName("Delete All Teachers")
    @Test
    void deleteAllTeacherTest() throws Exception {

        doNothing().when(teacherService).delete();

        mockMvc.perform(MockMvcRequestBuilders.delete(TEACHER_CONTROLLER_PATH))
                .andExpect(status().isAccepted());

        verify(teacherService, atLeastOnce()).delete();
    }

    @DisplayName("Delete All Teachers Exception")
    @Test
    void deleteAllTeacherExceptionTest() throws Exception {

        doThrow(new ServiceException(SOMETHING_HAPPENED)).when(teacherService).delete();

        mockMvc.perform(MockMvcRequestBuilders.delete(TEACHER_CONTROLLER_PATH))
                .andExpect(status().isInternalServerError());

        verify(teacherService, atLeastOnce()).delete();
    }

    @DisplayName("Update Teacher")
    @Test
    void update_ReturnsOkResponse() throws Exception {
        Integer teacherId = 1;
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setPassword("!WH-xm4100");
        JSONObject teacherJson = new JSONObject().put("password","!WH-xm4100");
        doNothing().when(teacherService).update(teacherDto, teacherId);

        mockMvc.perform(MockMvcRequestBuilders.patch(TEACHER_CONTROLLER_PATH + teacherId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(teacherJson)))
                .andExpect(status().isOk())
                .andExpect(content().string("Teacher was updated successfully"))
                .andReturn();

        ResponseEntity<Object> response = teacherController.update(teacherDto, teacherId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Teacher was updated successfully", response.getBody());
        verify(teacherService, times(1)).update(teacherDto, teacherId);
    }

    @DisplayName("Update Teacher Exception")
    @Test
    void update_ExceptionThrown_ReturnsUnprocessableEntityResponse() throws Exception {
        Integer teacherId = 1;
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setPassword("!WH-xm4100");
        doThrow(new ExceptionService("Database connection error")).when(teacherService).update(teacherDto, teacherId);

        ResponseEntity<Object> response = teacherController.update(teacherDto, teacherId);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Could not update teacher", response.getBody());
        verify(teacherService, times(1)).update(teacherDto, teacherId);
    }

    @DisplayName("Find Teacher Course")
    @Test
    void findCourse_ReturnsOkResponse() throws Exception {
        Integer teacherId = 1;
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");
        courseDto.setNumberOfStudents(300);
        when(teacherService.findCourse(teacherId)).thenReturn(courseDto);

        mockMvc.perform(get(TEACHER_CONTROLLER_PATH + "course/" + teacherId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Computer Science"))
                .andExpect(jsonPath("$.numberOfStudents").value(300))
                .andReturn();
        ResponseEntity<Object> response = teacherController.findCourse(teacherId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courseDto, response.getBody());
        verify(teacherService, times(2)).findCourse(teacherId);
    }

    @DisplayName("Find Teacher Course Exception")
    @Test
    void findCourse_ExceptionThrown_ReturnsOkResponse() throws Exception {
        Integer teacherId = 1;
        when(teacherService.findCourse(teacherId)).thenThrow(new ExceptionService("Database connection error"));

        mockMvc.perform(get(TEACHER_CONTROLLER_PATH + "course/" + teacherId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(ERROR_MESSAGE + "Unable to find course/s for teacher with id: " + teacherId))
                .andReturn();
        ResponseEntity<Object> response = teacherController.findCourse(teacherId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Unable to find course/s for teacher with id: " + teacherId, response.getBody());
        verify(teacherService, times(2)).findCourse(teacherId);
    }

    @DisplayName("Find Teacher Subjects")
    @Test
    void findSubjects_ExistingSubjects_ReturnsOkResponse() throws Exception {
        Integer teacherId = 1;
        List<String> subjectsNames = new ArrayList<>();
        subjectsNames.add("Subject 1");
        subjectsNames.add("Subject 2");
        when(teacherService.findSubjects(teacherId)).thenReturn(subjectsNames);

        mockMvc.perform(get(TEACHER_CONTROLLER_PATH + "subjects/" + teacherId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value("Subject 1"))
                .andExpect(jsonPath("$.[1]").value("Subject 2"))
                .andReturn();
        ResponseEntity<Object> response = teacherController.findSubjects(teacherId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subjectsNames, response.getBody());
        verify(teacherService, times(2)).findSubjects(teacherId);
    }

    @DisplayName("Find Teacher Subjects - Nonexistent")
    @Test
    void findSubjects_NonexistentSubjects_ReturnsOkResponse() throws Exception {
        Integer teacherId = 1;
        Set<String> subjectsNames = new HashSet<>();
        when(teacherService.findSubjects(teacherId)).thenReturn(subjectsNames);

        mockMvc.perform(get(TEACHER_CONTROLLER_PATH + "subjects/" + teacherId))
                .andExpect(status().isOk())
                .andExpect(content().string("No subjects found for this teacher"))
                .andReturn();
        ResponseEntity<Object> response = teacherController.findSubjects(teacherId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No subjects found for this teacher", response.getBody());
        verify(teacherService, times(2)).findSubjects(teacherId);
    }

    @DisplayName("Find Teacher Subjects Exception")
    @Test
    void findSubjects_ExceptionThrown_ReturnsNotFoundResponse() throws Exception {
        Integer teacherId = 1;
        List<String> subjectsNames = new ArrayList<>();
        when(teacherService.findSubjects(teacherId)).thenThrow(new ExceptionService("Database connection error"));

        mockMvc.perform(get(TEACHER_CONTROLLER_PATH + "subjects/" + teacherId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(ERROR_MESSAGE + "Unable to find subject/s for teacher with id: " + teacherId))
                .andReturn();
        ResponseEntity<Object> response = teacherController.findSubjects(teacherId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Unable to find subject/s for teacher with id: " + teacherId, response.getBody());
        verify(teacherService, times(2)).findSubjects(teacherId);
    }

    @DisplayName("Teacher adds Grade to Student")
    @Test
    void addGrade_ReturnsCreatedResponse() throws Exception {
        Integer studentId = 1;
        Integer subjectId = 1;
        StudentDto studentDto = new StudentDto();
        SubjectDto subjectDto = new SubjectDto();
        GradeDto gradeDto = new GradeDto();
        gradeDto.setMark(4.9);
        JSONObject gradeJson = new JSONObject()
                .put("mark",4.9);

        when(studentService.findById(studentId)).thenReturn(studentDto);
        when(subjectService.findById(subjectId)).thenReturn(subjectDto);
        doNothing().when(teacherService).addGrade(gradeDto, studentId, subjectId);

        mockMvc.perform(post(TEACHER_CONTROLLER_PATH + "add/grade/" + studentId +"/"+subjectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(gradeJson)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Grade added to student: " + subjectId))
                .andReturn();

        ResponseEntity<Object> response = teacherController.addGrade(gradeDto, studentId, subjectId);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Grade added to student: " + subjectId, response.getBody());
        verify(teacherService, times(1)).addGrade(gradeDto, studentId, subjectId);
    }

    @DisplayName("Teacher adds Grade to Student - No Student Found")
    @Test
    void addGrade_NoStudentFound_ReturnsOkResponse() throws Exception {
        Integer studentId = 1;
        Integer subjectId = 1;
        GradeDto gradeDto = new GradeDto();
        gradeDto.setMark(4.9);
        JSONObject gradeJson = new JSONObject()
                .put("mark",4.9);

        when(studentService.findById(studentId)).thenReturn(null);

        mockMvc.perform(post(TEACHER_CONTROLLER_PATH + "add/grade/" + studentId +"/"+subjectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(gradeJson)))
                .andExpect(status().isOk())
                .andExpect(content().string("Student does not exist"))
                .andReturn();

        ResponseEntity<Object> response = teacherController.addGrade(gradeDto, studentId, subjectId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Student does not exist", response.getBody());
        verify(studentService, times(2)).findById(studentId);
    }

    @DisplayName("Teacher adds Grade to Student - No Subject Found")
    @Test
    void addGrade_NoSubjectFound_ReturnsOkResponse() throws Exception {
        Integer studentId = 1;
        Integer subjectId = 1;
        StudentDto studentDto = new StudentDto();
        GradeDto gradeDto = new GradeDto();
        gradeDto.setMark(4.9);
        JSONObject gradeJson = new JSONObject()
                .put("mark",4.9);

        when(studentService.findById(studentId)).thenReturn(studentDto);
        when(subjectService.findById(subjectId)).thenReturn(null);

        mockMvc.perform(post(TEACHER_CONTROLLER_PATH + "add/grade/" + studentId +"/"+subjectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(gradeJson)))
                .andExpect(status().isOk())
                .andExpect(content().string("Subject with id" + subjectId + "does not exist"))
                .andReturn();

        ResponseEntity<Object> response = teacherController.addGrade(gradeDto, studentId, subjectId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Subject with id" + subjectId + "does not exist", response.getBody());
        verify(subjectService, times(2)).findById(subjectId);
    }

    @DisplayName("Teacher adds Grade to Student Exception")
    @Test
    void addGrade_ExceptionThrown_ReturnsInternalServerErrorResponse() throws Exception {
        Integer studentId = 1;
        Integer subjectId = 1;
        StudentDto studentDto = new StudentDto();
        SubjectDto subjectDto = new SubjectDto();
        GradeDto gradeDto = new GradeDto();
        gradeDto.setMark(4.9);

        when(studentService.findById(studentId)).thenReturn(studentDto);
        when(subjectService.findById(subjectId)).thenReturn(subjectDto);
        doThrow(new ExceptionService("Database connection error")).when(teacherService).addGrade(gradeDto, studentId, subjectId);

        ResponseEntity<Object> response = teacherController.addGrade(gradeDto, studentId, subjectId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Not possible to add grade to student.", response.getBody());
        verify(teacherService, times(1)).addGrade(gradeDto, studentId, subjectId);
    }
}