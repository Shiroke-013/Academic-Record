package com.java.controller;

import com.java.dto.GradeDto;
import com.java.service.ExceptionService;
import com.java.service.GradeService;
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
class GradeControllerTest {

    private static final String GRADE_CONTROLLER_PATH = "/grade/";
    private static final String ERROR_MESSAGE = "An error occurred: ";
    private MockMvc mockMvc;

    @InjectMocks
    private GradeController gradeController;

    @Mock
    private GradeService gradeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gradeController).build();
    }

    @DisplayName("Create Grade")
    @Test
    void save_ValidGradeDto_ReturnsCreatedResponse() throws Exception {

        doNothing().when(gradeService).save(ArgumentMatchers.any(GradeDto.class));
        JSONObject gradeJson = new JSONObject()
                .put("mark",4.5);

        MvcResult mvcResult = mockMvc.perform(post(GRADE_CONTROLLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(gradeJson)))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
        assertEquals("All good, great created", mvcResult.getResponse().getContentAsString());
        verify(gradeService, times(1)).save(ArgumentMatchers.any(GradeDto.class));
    }

    @DisplayName("Create Grade Exception")
    @Test
    void save_ExceptionThrown_ReturnsInternalServerError() throws Exception{

        doThrow(new ExceptionService("Database connection error")).when(gradeService).save(ArgumentMatchers.any(GradeDto.class));
        JSONObject gradeJson = new JSONObject();

        MvcResult mvcResult = mockMvc.perform(post(GRADE_CONTROLLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(gradeJson)))
                .andExpect(status().isInternalServerError())
                .andReturn();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus());
        assertEquals(ERROR_MESSAGE +"Unable to create grade", mvcResult.getResponse().getContentAsString());
        verify(gradeService, times(1)).save(ArgumentMatchers.any(GradeDto.class));
    }

    @DisplayName("Find Grade by Id")
    @Test
    void findById_ExistingGrade_ReturnsOkResponse() throws Exception {
        Integer gradeId = 1;
        GradeDto gradeDto = new GradeDto();
        gradeDto.setMark(4.9);
        gradeDto.setStudentId(1);
        gradeDto.setSubjectId(1);

        when(gradeService.findById(gradeId)).thenReturn(gradeDto);

        mockMvc.perform(get(GRADE_CONTROLLER_PATH + gradeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mark").value(4.9))
                .andExpect(jsonPath("$.studentId").value(1))
                .andExpect(jsonPath("$.subjectId").value(1));

        ResponseEntity<Object> response = gradeController.findById(gradeId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gradeDto, response.getBody());
        verify(gradeService, times(2)).findById(gradeId);
    }

    @DisplayName("Find Grade with Nonexistent Id")
    @Test
    void findById_NonExistingId_ReturnsNotFoundResponse() throws Exception {
        Integer gradeId = 12;
        when(gradeService.findById(gradeId)).thenReturn(null);

        mockMvc.perform(get(GRADE_CONTROLLER_PATH + gradeId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No grade found"));

        verify(gradeService, times(1)).findById(gradeId);
    }

    @DisplayName("Find Grade Exception")
    @Test
    void findById_ExceptionThrown_ReturnsInternalServerErrorResponse() throws Exception {
        Integer gradeId = 19;
        when(gradeService.findById(gradeId)).thenThrow(new ExceptionService("Database connection error"));

        mockMvc.perform(get(GRADE_CONTROLLER_PATH + gradeId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(ERROR_MESSAGE + "Unable to find grade with id: " + gradeId));

        verify(gradeService, times(1)).findById(gradeId);
    }

    @DisplayName("Find All Grades")
    @Test
    void findAll_ReturnsListOfGradesAndOkResponse() throws Exception {
        GradeDto gradeDto = new GradeDto();
        gradeDto.setMark(4.9);
        gradeDto.setStudentId(1);
        gradeDto.setSubjectId(1);
        GradeDto gradeDto2 = new GradeDto();
        gradeDto2.setMark(3.5);
        gradeDto2.setStudentId(2);
        gradeDto2.setSubjectId(1);

        List<GradeDto> gradeDtos = new ArrayList<>();
        gradeDtos.add(gradeDto);
        gradeDtos.add(gradeDto2);
        when(gradeService.findAll()).thenReturn(gradeDtos);

        mockMvc.perform(get(GRADE_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mark", is(4.9)))
                .andExpect(jsonPath("$[0].studentId", is(1)))
                .andExpect(jsonPath("$[0].subjectId", is(1)))
                .andExpect(jsonPath("$[1].mark", is(3.5)))
                .andExpect(jsonPath("$[1].studentId", is(2)))
                .andExpect(jsonPath("$[1].subjectId", is(1)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(gradeService, times(1)).findAll();
    }

    @DisplayName("Not Grades Found")
    @Test
    void findAll_ReturnsNotFoundResponse() throws Exception {
        List<GradeDto> gradeDtos = new ArrayList<>();
        when(gradeService.findAll()).thenReturn(gradeDtos);

        mockMvc.perform(get(GRADE_CONTROLLER_PATH))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No grades found"));

        ResponseEntity<Object> response = gradeController.findAll();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No grades found", response.getBody());
        verify(gradeService, times(2)).findAll();
    }

    @DisplayName("Find All Grades Exception")
    @Test
    void findAll_ReturnsInternalServerErrorResponse() throws Exception {
        when(gradeService.findAll()).thenThrow(new ExceptionService("Database connection error."));

        mockMvc.perform(get(GRADE_CONTROLLER_PATH))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(ERROR_MESSAGE + "Not able to found all grades"));

        verify(gradeService, times(1)).findAll();
    }

    @DisplayName("Delete Grade with Existing Id")
    @Test
    void delete_ExistingId_ReturnsOkResponse() throws Exception {
        Integer existingId = 1;
        GradeDto gradeDto = new GradeDto();
        gradeDto.setMark(4.9);
        gradeDto.setStudentId(1);
        gradeDto.setSubjectId(1);

        doNothing().when(gradeService).deleteById(existingId);
        when(gradeService.findById(existingId)).thenReturn(gradeDto);

        mockMvc.perform(delete(GRADE_CONTROLLER_PATH + existingId))
                .andExpect(status().isOk())
                .andExpect(content().string("Grade with id: " + existingId + " deleted."));

        ResponseEntity<Object> response = gradeController.delete(existingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Grade with id: " + existingId + " deleted.", response.getBody());
        verify(gradeService, times(2)).deleteById(existingId);
    }

    @DisplayName("Delete Grade with Nonexistent Id")
    @Test
    void delete_NonExistingId_ReturnsNotFoundResponse() throws Exception {
        Integer nonexistentId = 1;

        when(gradeService.findById(nonexistentId)).thenReturn(null);

        mockMvc.perform(delete(GRADE_CONTROLLER_PATH + nonexistentId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Grade does not exist"));

        verify(gradeService, times(1)).findById(nonexistentId);
    }

    @DisplayName("Delete Grade with Id Exception")
    @Test
    void delete_ExceptionThrown_ReturnsInternalServerErrorResponse() throws Exception {
        Integer id = 19;
        GradeDto gradeDto = new GradeDto();
        gradeDto.setMark(4.9);
        gradeDto.setStudentId(1);
        gradeDto.setSubjectId(1);

        when(gradeService.findById(id)).thenReturn(gradeDto);
        doThrow(new ExceptionService("Database connection error")).when(gradeService).deleteById(id);

        mockMvc.perform(delete(GRADE_CONTROLLER_PATH + id))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(ERROR_MESSAGE + "Not able to delete grade with id: " + id));

        ResponseEntity<Object> response = gradeController.delete(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Not able to delete grade with id: " + id, response.getBody());
        verify(gradeService, times(2)).findById(id);
        verify(gradeService, times(2)).deleteById(id);
    }

    @DisplayName("Delete All Grades")
    @Test
    void deleteAll_ReturnsAcceptedResponse() throws Exception {
        doNothing().when(gradeService).deleteAll();

        mockMvc.perform(delete(GRADE_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string("All grades were deleted."));

        ResponseEntity<Object> response = gradeController.deleteAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("All grades were deleted.", response.getBody());
        verify(gradeService, times(2)).deleteAll();
    }

    @DisplayName("Delete All Grades Exception")
    @Test
    void deleteAll_ExceptionThrown_ReturnsUnprocessableEntity() throws Exception {
        doThrow(new ExceptionService("Database connection error.")).when(gradeService).deleteAll();

        mockMvc.perform(delete(GRADE_CONTROLLER_PATH))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string( ERROR_MESSAGE + "Unable to delete all grades"));

        ResponseEntity<Object> response = gradeController.deleteAll();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals( ERROR_MESSAGE + "Unable to delete all grades", response.getBody());
        verify(gradeService, times(2)).deleteAll();
    }

    @DisplayName("Update Grade")
    @Test
    void update_ReturnsOkResponse() throws Exception {
        Integer id = 1;
        GradeDto gradeDto = new GradeDto();
        gradeDto.setMark(5.0);
        JSONObject gradeJson = new JSONObject()
                .put("mark",5.0);
        doNothing().when(gradeService).update(gradeDto, id);

        mockMvc.perform(patch(GRADE_CONTROLLER_PATH + id)
                        .content(String.valueOf(gradeJson))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Grade updated"));

        ResponseEntity<Object> response = gradeController.update(gradeDto, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Grade updated", response.getBody());
        verify(gradeService, times(1)).update(gradeDto, id);
    }

    @DisplayName("Update Grade Exception")
    @Test
    void update_ExceptionThrown_ReturnsInternalServerError() throws Exception {
        Integer id = 90;
        GradeDto gradeDto = new GradeDto();
        doThrow(new ExceptionService("Database connection error")).when(gradeService).update(gradeDto, id);

        ResponseEntity<Object> response = gradeController.update(gradeDto, id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ERROR_MESSAGE + "Not possible to update grade.", response.getBody());
        verify(gradeService, times(1)).update(gradeDto, id);
    }

}
