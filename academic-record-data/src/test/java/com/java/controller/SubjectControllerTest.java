package com.java.controller;

import com.java.dto.GradeDto;
import com.java.dto.SubjectDto;
import com.java.service.ExceptionService;
import com.java.service.SubjectService;
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

import static org.hamcrest.Matchers.is;
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
class SubjectControllerTest {

    private static final String SUBJECT_CONTROLLER_PATH = "/subject/";
    private static final String ERROR_MESSAGE = "An error occurred: ";
    private MockMvc mockMvc;

    @InjectMocks
    private SubjectController subjectController;

    @Mock
    private SubjectService subjectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subjectController).build();
    }

    @DisplayName("Create Subject")
    @Test
    void save_ReturnsCreatedResponse() throws Exception {

        doNothing().when(subjectService).save(ArgumentMatchers.any(SubjectDto.class));
        JSONObject subjectJson = new JSONObject()
                .put("subjectName","Subject 1")
                .put("duration", 50)
                .put("capacity", 20)
                .put("startDate", "2023-01-30")
                .put("endDate", "2023-05-17");

        MvcResult mvcResult = mockMvc.perform(post(SUBJECT_CONTROLLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(subjectJson)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Subject created"))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
        assertEquals("Subject created", mvcResult.getResponse().getContentAsString());
        verify(subjectService, times(1)).save(ArgumentMatchers.any(SubjectDto.class));
    }

    @DisplayName("Create Subject Exception")
    @Test
    void save_ExceptionThrown_ReturnsInternalServerErrorResponse() throws Exception {

        doThrow(new ExceptionService("Database connection error")).when(subjectService).save(ArgumentMatchers.any(SubjectDto.class));
        JSONObject subjectJson = new JSONObject();

        MvcResult mvcResult = mockMvc.perform(post(SUBJECT_CONTROLLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(subjectJson)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(ERROR_MESSAGE + "Not able to create subject"))
                .andReturn();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus());
        assertEquals(ERROR_MESSAGE + "Not able to create subject", mvcResult.getResponse().getContentAsString());
        verify(subjectService, times(1)).save(ArgumentMatchers.any(SubjectDto.class));
    }

    @DisplayName("Find Subject by Id")
    @Test
    void findById_ReturnsOkResponse() throws Exception {
        Integer subjectId = 1;
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSubjectName("Subject 1");
        subjectDto.setCapacity(30);
        subjectDto.setDuration(80);
        subjectDto.setStartDate(LocalDate.parse("2023-01-30"));
        subjectDto.setEndDate(LocalDate.parse("2023-05-17"));
        when(subjectService.findById(subjectId)).thenReturn(subjectDto);

        mockMvc.perform(get(SUBJECT_CONTROLLER_PATH + subjectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subjectName").value("Subject 1"))
                .andExpect(jsonPath("$.capacity").value(30))
                .andExpect(jsonPath("$.duration").value(80))
                .andReturn();
        ResponseEntity<Object> response = subjectController.findById(subjectId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subjectDto, response.getBody());
        verify(subjectService, times(2)).findById(subjectId);
    }

    @DisplayName("Find Subject by Id Exception")
    @Test
    void findById_ExceptionThrown_ReturnsInternalServerErrorResponse() throws Exception {
        Integer subjectId = 1;
        when(subjectService.findById(subjectId)).thenThrow(new ExceptionService("Database connection error"));

        mockMvc.perform(get(SUBJECT_CONTROLLER_PATH + subjectId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(ERROR_MESSAGE + "Not able to find subject with id: " + subjectId))
                .andReturn();
        ResponseEntity<Object> response = subjectController.findById(subjectId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Not able to find subject with id: " + subjectId, response.getBody());
        verify(subjectService, times(2)).findById(subjectId);
    }

    @DisplayName("Find All Subjects")
    @Test
    void findAll_ReturnsCollectionOfSubjectsAndOkResponse() throws Exception {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSubjectName("Subject 1");
        subjectDto.setCapacity(30);
        subjectDto.setDuration(80);
        subjectDto.setStartDate(LocalDate.parse("2023-01-30"));
        subjectDto.setEndDate(LocalDate.parse("2023-05-17"));
        SubjectDto subjectDto2 = new SubjectDto();
        subjectDto2.setSubjectName("Subject 2");
        subjectDto2.setCapacity(15);
        subjectDto2.setDuration(80);
        subjectDto2.setStartDate(LocalDate.parse("2023-02-01"));
        subjectDto2.setEndDate(LocalDate.parse("2023-05-27"));
        List<SubjectDto> subjectDtos = new ArrayList<>();
        subjectDtos.add(subjectDto);
        subjectDtos.add(subjectDto2);
        when(subjectService.findAll()).thenReturn(subjectDtos);

        mockMvc.perform(get(SUBJECT_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].subjectName").value("Subject 1"))
                .andExpect(jsonPath("$[0].capacity").value(30))
                .andExpect(jsonPath("$[0].duration").value(80))
                .andExpect(jsonPath("$[1].subjectName").value("Subject 2"))
                .andExpect(jsonPath("$[1].capacity").value(15))
                .andExpect(jsonPath("$[0].duration").value(80))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(subjectService, times(1)).findAll();
    }

    @DisplayName("Find All Subjects Exception")
    @Test
    void findAll_ExceptionThrown_ReturnsInternalServerErrorResponse() throws Exception {
        when(subjectService.findAll()).thenThrow(new ExceptionService("Database connection error"));

        mockMvc.perform(get(SUBJECT_CONTROLLER_PATH))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(ERROR_MESSAGE + "Not able to get all subjects"));

        verify(subjectService, times(1)).findAll();
    }

    @DisplayName("Delete Subject By Id")
    @Test
    void delete_ReturnsOkResponse() throws Exception {
        Integer subjectId = 1;

        doNothing().when(subjectService).deleteById(subjectId);
        mockMvc.perform(delete(SUBJECT_CONTROLLER_PATH + subjectId))
                .andExpect(status().isOk())
                .andExpect(content().string("Subject was deleted successfully"))
                .andReturn();
        ResponseEntity<Object> response = subjectController.delete(subjectId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Subject was deleted successfully", response.getBody());
        verify(subjectService, times(2)).deleteById(subjectId);
    }

    @DisplayName("Delete Subject By Id Exception")
    @Test
    void delete_ExceptionThrown_ReturnsOkResponse() throws Exception {
        Integer subjectId = 1;

        doThrow(new ExceptionService("Database connection error")).when(subjectService).deleteById(subjectId);
        mockMvc.perform(delete(SUBJECT_CONTROLLER_PATH + subjectId))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ERROR_MESSAGE + "Can't delete subject with id: " + subjectId))
                .andReturn();
        ResponseEntity<Object> response = subjectController.delete(subjectId);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Can't delete subject with id: " + subjectId, response.getBody());
        verify(subjectService, times(2)).deleteById(subjectId);
    }

    @DisplayName("Delete All Subjects")
    @Test
    void deleteAll_ReturnsAcceptedResponse() throws Exception {
        doNothing().when(subjectService).deleteAll();

        mockMvc.perform(delete(SUBJECT_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string("All subjects deleted"));

        ResponseEntity<Object> response = subjectController.deleteAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("All subjects deleted", response.getBody());
        verify(subjectService, times(2)).deleteAll();
    }

    @DisplayName("Delete All Subjects Exception")
    @Test
    void deleteAll_ExceptionThrown_ReturnsAcceptedResponse() throws Exception {
        doThrow(new ExceptionService("Database connection error")).when(subjectService).deleteAll();

        mockMvc.perform(delete(SUBJECT_CONTROLLER_PATH))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Not able to delete all subjects"));

        ResponseEntity<Object> response = subjectController.deleteAll();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Not able to delete all subjects", response.getBody());
        verify(subjectService, times(2)).deleteAll();
    }

    @DisplayName("Update Subject")
    @Test
    void update_ReturnsOkResponse() throws Exception {
        Integer id = 1;
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSubjectName("Chemistry");
        JSONObject subjectJson = new JSONObject().put("subjectName","Chemistry");
        doNothing().when(subjectService).update(subjectDto, id);

        mockMvc.perform(patch(SUBJECT_CONTROLLER_PATH + id)
                        .content(String.valueOf(subjectJson))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Subject updated"));
        ResponseEntity<Object> response = subjectController.update(subjectDto, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Subject updated", response.getBody());
        verify(subjectService, times(1)).update(subjectDto, id);
    }

    @DisplayName("Update Subject Exception")
    @Test
    void update_ExceptionThrown_ReturnsInternalServerErrorResponse() throws Exception {
        Integer id = 1;
        SubjectDto subjectDto = new SubjectDto();
        doThrow(new ExceptionService("Database connection error")).when(subjectService).update(subjectDto, id);

        ResponseEntity<Object> response = subjectController.update(subjectDto, id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unable to update subject", response.getBody());
        verify(subjectService, times(1)).update(subjectDto, id);
    }

    @DisplayName("Add Teacher to Subject")
    @Test
    void addTeacher_ReturnsOkResponse() throws Exception {
        Integer subjectId = 1;
        Integer teacherId = 1;
        doNothing().when(subjectService).addTeacher(subjectId, teacherId);

        mockMvc.perform(patch(SUBJECT_CONTROLLER_PATH + subjectId + "/add/teacher/" + teacherId))
                .andExpect(status().isCreated())
                .andExpect(content().string("Teacher assigned to subject"));
        ResponseEntity<Object> response = subjectController.addTeacher(subjectId, teacherId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Teacher assigned to subject", response.getBody());
        verify(subjectService, times(2)).addTeacher(subjectId, teacherId);
    }

    @DisplayName("Add Teacher to Subject Exception")
    @Test
    void addTeacher_ExceptionThrown_ReturnsOkResponse() throws Exception {
        Integer subjectId = 1;
        Integer teacherId = 1;
        doThrow(new ExceptionService("Database connection error")).when(subjectService).addTeacher(subjectId, teacherId);

        mockMvc.perform(patch(SUBJECT_CONTROLLER_PATH + subjectId + "/add/teacher/" + teacherId))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ERROR_MESSAGE + "Not possible to assign teacher to subject"));
        ResponseEntity<Object> response = subjectController.addTeacher(subjectId, teacherId);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Not possible to assign teacher to subject", response.getBody());
        verify(subjectService, times(2)).addTeacher(subjectId, teacherId);
    }

    @DisplayName("Find Students from Subject")
    @Test
    void findStudents_ExistingStudents_ReturnsOkResponse() throws Exception {
        Integer subjectId = 1;
        List<String> studentNames = new ArrayList<>();
        studentNames.add("Student One");
        studentNames.add("Student Two");
        when(subjectService.findStudents(subjectId)).thenReturn(studentNames);

        mockMvc.perform(get(SUBJECT_CONTROLLER_PATH + subjectId + "/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Student One"))
                .andExpect(jsonPath("$[1]").value("Student Two"))
                .andReturn();
        ResponseEntity<Object> response = subjectController.findStudents(subjectId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentNames, response.getBody());
        verify(subjectService, times(2)).findStudents(subjectId);
    }

    @DisplayName("Find Nonexistent Students from Subject")
    @Test
    void findStudents_NonExistentStudents_ReturnsNotFoundResponse() throws Exception {
        Integer subjectId = 1;
        List<String> studentNames = new ArrayList<>();
        when(subjectService.findStudents(subjectId)).thenReturn(studentNames);

        mockMvc.perform(get(SUBJECT_CONTROLLER_PATH + subjectId + "/students"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No students found for this subject"))
                .andReturn();
        ResponseEntity<Object> response = subjectController.findStudents(subjectId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No students found for this subject", response.getBody());
        verify(subjectService, times(2)).findStudents(subjectId);
    }

    @DisplayName("Find Students from Subject Exception")
    @Test
    void findStudents_ExceptionThrown_ReturnsUnprocessableEntityResponse() throws Exception {
        Integer subjectId = 1;
        List<String> studentNames = new ArrayList<>();
        when(subjectService.findStudents(subjectId)).thenThrow(new ExceptionService("Database connection error"));

        mockMvc.perform(get(SUBJECT_CONTROLLER_PATH + subjectId + "/students"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ERROR_MESSAGE + "Not possible to get all students from subject"))
                .andReturn();
        ResponseEntity<Object> response = subjectController.findStudents(subjectId);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Not possible to get all students from subject", response.getBody());
        verify(subjectService, times(2)).findStudents(subjectId);
    }

    @DisplayName("Delete Teacher from Subject")
    @Test
    void deleteTeacherFromSubject_ReturnsOkResponse() throws Exception {
        Integer subjectId = 1;
        Integer teacherId = 1;
        doNothing().when(subjectService).deleteTeacherFromSubject(subjectId, teacherId);

        mockMvc.perform(delete(SUBJECT_CONTROLLER_PATH + subjectId + "/teacher/" + teacherId))
                .andExpect(status().isOk())
                .andExpect(content().string("Teacher deleted from subject with success"))
                .andReturn();
        ResponseEntity<Object> response = subjectController.deleteTeacherFromSubject(subjectId, teacherId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Teacher deleted from subject with success", response.getBody());
        verify(subjectService, times(2)).deleteTeacherFromSubject(subjectId, teacherId);
    }

    @DisplayName("Delete Teacher from Subject Exception")
    @Test
    void deleteTeacherFromSubject_ExceptionThrown_ReturnsUnprocessableEntityResponse() throws Exception {
        Integer subjectId = 1;
        Integer teacherId = 1;
        doThrow(new ExceptionService("Database connection error")).when(subjectService).deleteTeacherFromSubject(subjectId, teacherId);

        mockMvc.perform(delete(SUBJECT_CONTROLLER_PATH + subjectId + "/teacher/" + teacherId))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ERROR_MESSAGE + "Not possible to unassigned teacher from subject"))
                .andReturn();
        ResponseEntity<Object> response = subjectController.deleteTeacherFromSubject(subjectId, teacherId);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Not possible to unassigned teacher from subject", response.getBody());
        verify(subjectService, times(2)).deleteTeacherFromSubject(subjectId, teacherId);
    }
}
