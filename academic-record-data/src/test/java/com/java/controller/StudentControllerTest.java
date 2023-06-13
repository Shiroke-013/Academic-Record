package com.java.controller;


import com.java.dto.CourseDto;
import com.java.dto.StudentDto;
import com.java.dto.SubjectDto;
import com.java.service.ExceptionService;
import com.java.service.StudentService;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
class StudentControllerTest {

    private static final String STUDENT_CONTROLLER_PATH = "/student/";
    private static final String ERROR_MESSAGE = "An error occurred: ";
    private MockMvc mockMvc;

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @DisplayName("Create Student")
    @Test
    void save_ValidStudentDto_ReturnsCreatedResponse() throws Exception {

        doNothing().when(studentService).save(ArgumentMatchers.any(StudentDto.class));
        JSONObject studentJson = new JSONObject()
                .put("firstName","Student")
                .put("lastName", "One")
                .put("email", "so@ar.com")
                .put("password", "!test1echo");

        MvcResult mvcResult = mockMvc.perform(post(STUDENT_CONTROLLER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(studentJson)))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
        assertEquals("The student was successfully created.", mvcResult.getResponse().getContentAsString());
        verify(studentService, times(1)).save(ArgumentMatchers.any(StudentDto.class));
    }

    @DisplayName("Create Student Exception")
    @Test
    void save_InvalidStudentDto_ReturnsInternalServerErrorResponse() throws Exception {

        doThrow(new ExceptionService("Null values")).when(studentService).save(ArgumentMatchers.any(StudentDto.class));
        JSONObject studentJson = new JSONObject();

        MvcResult mvcResult = mockMvc.perform(post(STUDENT_CONTROLLER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(studentJson)))
                .andExpect(status().isInternalServerError())
                .andReturn();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus());
        assertEquals(ERROR_MESSAGE + "Not possible to create student", mvcResult.getResponse().getContentAsString());
        verify(studentService, times(1)).save(ArgumentMatchers.any(StudentDto.class));
    }

    @DisplayName("Find Student By Id")
    @Test
    void findById_ExistingId_ReturnsStudentDtoWithOkResponse() throws Exception {
        String studentId = "1";
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("Student");
        studentDto.setLastName("One");
        studentDto.setEmail("so@ar.com");
        studentDto.setPassword("!Test4Echo");
        when(studentService.findById(Integer.parseInt(studentId))).thenReturn(studentDto);

        mockMvc.perform(get(STUDENT_CONTROLLER_PATH + studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Student")))
                .andExpect(jsonPath("$.lastName", is("One")))
                .andExpect(jsonPath("$.email", is("so@ar.com")))
                .andExpect(jsonPath("$.password", is("!Test4Echo")));

        ResponseEntity<Object> response = studentController.findById(studentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentDto, response.getBody());
        verify(studentService, times(2)).findById(Integer.parseInt(studentId));
    }

    @DisplayName("Find Student with Nonexistent Id")
    @Test
    void findById_NonExistingId_ReturnsNotFoundResponse() throws Exception {
        String studentId = "12";
        when(studentService.findById(Integer.parseInt(studentId))).thenReturn(null);

        mockMvc.perform(get(STUDENT_CONTROLLER_PATH + studentId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Student with id: " + studentId + " does not exits."));

        verify(studentService, times(1)).findById(Integer.parseInt(studentId));
    }

    @DisplayName("Find Student with Invalid Id")
    @Test
    void findById_InvalidId_ReturnsUnprocessableEntityResponse() throws Exception {
        mockMvc.perform(get(STUDENT_CONTROLLER_PATH + "invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ERROR_MESSAGE + "Please give a valid identifier (integer)"));

        verifyNoInteractions(studentService);
    }

    @DisplayName("Find Student Exception")
    @Test
    void findById_ExceptionThrown_ReturnsInternalServerError() throws Exception {
        String studentId = "1";
        when(studentService.findById(Integer.parseInt(studentId))).thenThrow(new ExceptionService("Database connection error."));

        mockMvc.perform(get(STUDENT_CONTROLLER_PATH + studentId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(ERROR_MESSAGE + "while processing the request"))
                .andReturn();

        verify(studentService, times(1)).findById(Integer.parseInt(studentId));
    }

    @DisplayName("Find All Students")
    @Test
    void findAll_ReturnsListOfStudentsAndOkResponse() throws Exception {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("Student");
        studentDto.setLastName("One");
        studentDto.setEmail("so@ar.com");
        studentDto.setPassword("!Test4Echo");

        StudentDto studentDto2 = new StudentDto();
        studentDto2.setFirstName("Student");
        studentDto2.setLastName("Two");
        studentDto2.setEmail("st@ar.com");
        studentDto2.setPassword("!Test2Echo");

        List<StudentDto> studentsDto = new ArrayList<>();
        studentsDto.add(studentDto);
        studentsDto.add(studentDto2);
        when(studentService.findAll()).thenReturn(studentsDto);

        MvcResult mvcResult = mockMvc.perform(get(STUDENT_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Student")))
                .andExpect(jsonPath("$[0].lastName", is("One")))
                .andExpect(jsonPath("$[0].email", is("so@ar.com")))
                .andExpect(jsonPath("$[0].password", is("!Test4Echo")))
                .andExpect(jsonPath("$[1].firstName", is("Student")))
                .andExpect(jsonPath("$[1].lastName", is("Two")))
                .andExpect(jsonPath("$[1].email", is("st@ar.com")))
                .andExpect(jsonPath("$[1].password", is("!Test2Echo")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(studentService, times(1)).findAll();
    }

    @DisplayName("Find All Students Exception")
    @Test
    void findAll_ReturnsInternalServerErrorResponse() throws Exception {
        when(studentService.findAll()).thenThrow(new ExceptionService("Database connection error."));

        mockMvc.perform(get(STUDENT_CONTROLLER_PATH))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(ERROR_MESSAGE + "Not able to show all students"));

        verify(studentService, times(1)).findAll();
    }

    @DisplayName("Delete with Id")
    @Test
    void delete_ExistingId_ReturnsOkResponse() throws Exception {
        Integer existingId = 1;
        when(studentService.deleteById(existingId)).thenReturn(true);

        mockMvc.perform(delete(STUDENT_CONTROLLER_PATH + existingId))
                .andExpect(status().isOk())
                .andExpect(content().string("Student was deleted with success"));

        ResponseEntity<Object> response = studentController.delete(existingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Student was deleted with success", response.getBody());
        verify(studentService, times(2)).deleteById(existingId);
    }

    @DisplayName("Delete with Nonexistent Id")
    @Test
    void delete_NonExistingId_ReturnsNotFoundResponse() throws Exception {
        Integer nonExistingId = 1234567890;
        when(studentService.deleteById(nonExistingId)).thenReturn(false);

        mockMvc.perform(delete(STUDENT_CONTROLLER_PATH + nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Student does not exist"));
        ResponseEntity<Object> response = studentController.delete(nonExistingId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Student does not exist", response.getBody());
        verify(studentService, times(2)).deleteById(nonExistingId);
    }

    @DisplayName("Delete with Id Exception")
    @Test
    void delete_ExceptionThrown_ReturnsUnprocessableEntity() throws Exception {
        Integer id = 3;
        when(studentService.deleteById(id)).thenThrow(new ExceptionService("Database connection error"));

        mockMvc.perform(delete(STUDENT_CONTROLLER_PATH + id))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ERROR_MESSAGE + "Can't delete student with id" + id));
        ResponseEntity<Object> response = studentController.delete(id);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Can't delete student with id" + id, response.getBody());
        verify(studentService, times(2)).deleteById(id);
    }

    @DisplayName("Delete All Students")
    @Test
    void deleteAll_ReturnsAcceptedResponse() throws Exception {
        doNothing().when(studentService).deleteAll();

        mockMvc.perform(delete(STUDENT_CONTROLLER_PATH))
                .andExpect(status().isAccepted())
                .andExpect(content().string("All students were deleted."));

        ResponseEntity<Object> response = studentController.deleteAll();

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("All students were deleted.", response.getBody());
        verify(studentService, times(2)).deleteAll();
    }

    @DisplayName("Delete All Students Exception")
    @Test
    void deleteAll_ExceptionThrown_ReturnsUnprocessableEntity() throws Exception {
        doThrow(new ExceptionService("Database connection error")).when(studentService).deleteAll();

        mockMvc.perform(delete(STUDENT_CONTROLLER_PATH))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(ERROR_MESSAGE + "Can not delete all students"));

        ResponseEntity<Object> response = studentController.deleteAll();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Can not delete all students", response.getBody());
        verify(studentService, times(2)).deleteAll();
    }

    @DisplayName("Update Student")
    @Test
    void update_ReturnsOkResponse() throws ExceptionService {
        Integer id = 1;
        StudentDto studentDto = new StudentDto();
        studentDto.setPassword("!Test4Echo");
        doNothing().when(studentService).update(studentDto, id);

        ResponseEntity<Object> response = studentController.update(studentDto, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Student was updated successfully", response.getBody());
        verify(studentService, times(1)).update(studentDto, id);
    }

    @DisplayName("Update Student Exception")
    @Test
    void update_ExceptionThrown_ReturnsUnprocessableEntity() throws ExceptionService {
        Integer id = 12;
        StudentDto studentDto = new StudentDto();
        doThrow(new ExceptionService("Can't update Student")).when(studentService).update(studentDto, id);

        ResponseEntity<Object> response = studentController.update(studentDto, id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Could not update student", response.getBody());
        verify(studentService, times(1)).update(studentDto, id);
    }

    @DisplayName("Register Student into Course")
    @Test
    void registerIntoCourse_ReturnsCreatedResponse() throws ExceptionService {
        Integer studentId = 1;
        Integer courseId = 1;
        doNothing().when(studentService).registerIntoCourse(studentId, courseId);

        ResponseEntity<Object> response = studentController.registerIntoCourse(studentId, courseId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Student was successfully registered in course with id: " + courseId, response.getBody());
        verify(studentService, times(1)).registerIntoCourse(studentId, courseId);
    }

    @DisplayName("Register Student into Course Exception")
    @Test
    void registerIntoCourse_ExceptionThrown_ReturnsInternalServerErrorResponse() throws ExceptionService {
        Integer studentId = 12;
        Integer courseId = 12;
        doThrow(new ExceptionService("Can't register student into course")).when(studentService).registerIntoCourse(studentId, courseId);

        ResponseEntity<Object> response = studentController.registerIntoCourse(studentId, courseId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Not possible to register student into course.", response.getBody());
        verify(studentService, times(1)).registerIntoCourse(studentId, courseId);
    }

    @DisplayName("Register Student into Subject")
    @Test
    void registerIntoSubject_ReturnsCreatedResponse() throws ExceptionService {
        Integer studentId = 1;
        Integer subjectId = 1;
        doNothing().when(studentService).registerIntoSubject(studentId, subjectId);

        ResponseEntity<Object> response = studentController.registerIntoSubject(studentId, subjectId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Student was successfully registered in subject with id: " + subjectId, response.getBody());
        verify(studentService, times(1)).registerIntoSubject(studentId, subjectId);
    }

    @DisplayName("Register Student into Subject Exception")
    @Test
    void registerIntoSubject_ExceptionThrown_ReturnsInternalServerErrorResponse() throws ExceptionService {
        Integer studentId = 12;
        Integer subjectId = 12;
        doThrow(new ExceptionService("Can't register student into subject")).when(studentService).registerIntoSubject(studentId, subjectId);

        ResponseEntity<Object> response = studentController.registerIntoSubject(studentId, subjectId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Not possible to register student into subject.", response.getBody());
        verify(studentService, times(1)).registerIntoSubject(studentId, subjectId);
    }

    @DisplayName("Find Course of Student")
    @Test
    void findCourse_ReturnsOkResponse() throws ExceptionService {
        Integer courseId = 1;
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");
        when(studentService.findCourse(courseId)).thenReturn(courseDto);

        ResponseEntity<Object> response = studentController.findCourse(courseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courseDto, response.getBody());
        verify(studentService, times(1)).findCourse(courseId);
    }

    @DisplayName("Find Course of Student Exception")
    @Test
    void findCourse_ExceptionThrown_ReturnsInternalServerErrorResponse() throws ExceptionService {
        Integer courseId = 12;
        when(studentService.findCourse(courseId)).thenThrow(new ExceptionService("Can't continue with the search"));

        ResponseEntity<Object> response = studentController.findCourse(courseId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Not possible to find course for student with id: " + courseId, response.getBody());
        verify(studentService, times(1)).findCourse(courseId);
    }

    @DisplayName("Find All Subjects of Student")
    @Test
    void findAllSubjects_ReturnsOkResponse() throws ExceptionService {
        Integer studentId = 1;
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSubjectName("Programming Fundamentals");
        SubjectDto subjectDto2 = new SubjectDto();
        subjectDto2.setSubjectName("Databases");
        HashSet<SubjectDto> subjectList = new HashSet<>();
        subjectList.add(subjectDto);
        subjectList.add(subjectDto2);

        when(studentService.findAllSubjects(studentId)).thenReturn(subjectList);

        ResponseEntity<Object> response = studentController.findAllSubjects(studentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subjectList, response.getBody());
        verify(studentService, times(1)).findAllSubjects(studentId);
    }

    @DisplayName("Find All Subjects of Student Exception")
    @Test
    void findAllSubjects_ExceptionThrown_ReturnsInternalServerErrorResponse() throws ExceptionService {
        Integer studentId = 12;
        when(studentService.findAllSubjects(studentId)).thenThrow(new ExceptionService("Something happened"));

        ResponseEntity<Object> response = studentController.findAllSubjects(studentId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Not possible to find subjects for student with id: " + studentId, response.getBody());
        verify(studentService, times(1)).findAllSubjects(studentId);
    }
}
