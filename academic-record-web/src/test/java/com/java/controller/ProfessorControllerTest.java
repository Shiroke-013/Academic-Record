package com.java.controller;


import com.java.dto.ProfessorDto;
import com.java.model.Professor;
import com.java.service.ExceptionService;
import com.java.service.ProfessorService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProfessorControllerTest {

    private static final String PROFESSOR_CONTROLLER_PATH = "/professor/";
    private static final String SOMETHING_HAPPENED = "Something happened";

    @InjectMocks
    private ProfessorController professorController;

    @Mock
    private ProfessorService professorService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(professorController).build();
    }

    @DisplayName("Get All Professors")
    @Test
    void  getAll_shouldBringAllProfessors() throws Exception {

        Professor professor1 = new Professor();
        professor1.setId(1);
        professor1.setFirstName("Andres");
        professor1.setLastName("Chaves");
        professor1.setEmail("andres@kai.com");
        professor1.setPassword("1234567890");

        Professor professor2 = new Professor();
        professor2.setId(2);
        professor2.setFirstName("David");
        professor2.setLastName("Chaves");
        professor2.setEmail("david@kai.com");
        professor2.setPassword("12345678901");

        List<Professor> professors = new ArrayList<>();

        professors.add(professor1);
        professors.add(professor2);

        when(professorService.findAll()).thenReturn(professors);

        mockMvc.perform(MockMvcRequestBuilders.get(PROFESSOR_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andReturn();

        verify(professorService, atLeastOnce()).findAll();
    }

    @DisplayName("Get All Professor Exception")
    @Test
    void getAllProfessorsExceptionTest() throws Exception {

        when(professorService.findAll()).thenThrow(new ExceptionService(SOMETHING_HAPPENED));

        mockMvc.perform(MockMvcRequestBuilders.get(PROFESSOR_CONTROLLER_PATH))
                .andExpect(status().isInternalServerError());

        verify(professorService, atLeastOnce()).findAll();
    }

    @DisplayName("Get Professor by Id")
    @Test
    void findByIdProfessorTest() throws Exception {

        Professor professor = new Professor();
        professor.setId(1);
        professor.setFirstName("Andres");
        professor.setLastName("Chaves");
        professor.setEmail("andres@kai.com");
        professor.setPassword("1234567890");


        when(professorService.findById(1)).thenReturn(professor);

        mockMvc.perform(MockMvcRequestBuilders.get(PROFESSOR_CONTROLLER_PATH + "1"))
                .andExpect(status().isOk());

        verify(professorService, atLeastOnce()).findById(1);
    }

    @DisplayName("Get Professor by Id Exception")
    @Test
    void findByIdProfessorExceptionTest() throws Exception {

        when(professorService.findById(1)).thenThrow(new ExceptionService(SOMETHING_HAPPENED));

        mockMvc.perform(MockMvcRequestBuilders.get(PROFESSOR_CONTROLLER_PATH + "1"))
                .andExpect(status().isInternalServerError());

        verify(professorService, atLeastOnce()).findById(1);
    }

    @DisplayName("Creating Professor")
    @Test
    void saveProfessorTest() throws Exception {

        ProfessorDto professorDto = new ProfessorDto();
        professorDto.setFirstName("Andres");
        professorDto.setLastName("Chaves");
        professorDto.setEmail("andres@kai.com");
        professorDto.setPassword("1234567890");

        when(professorService.save(professorDto)).thenReturn(new ResponseEntity<HttpStatus>(HttpStatus.CREATED));
        assertEquals(new ResponseEntity<HttpStatus>(HttpStatus.CREATED), professorService.save(professorDto));

        JSONObject professorJSON = new JSONObject()
                .put("id",1)
                .put("firstName", "Andres")
                .put("lastName", "Chaves")
                .put("email", "andres@kai.com")
                .put("password", "1234567890");


        mockMvc.perform(MockMvcRequestBuilders.post(PROFESSOR_CONTROLLER_PATH)
                        .content(String.valueOf(professorJSON))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(professorService, atLeastOnce()).save(professorDto);
    }

    @DisplayName("Creating Professor Exception")
    @Test
    void saveProfessorExceptionTest() throws Exception {

        ProfessorDto professorDto = new ProfessorDto();

        when(professorService.save(professorDto)).thenThrow(new ExceptionService(SOMETHING_HAPPENED));

        JSONObject professorJSON = new JSONObject();

        mockMvc.perform(MockMvcRequestBuilders.post(PROFESSOR_CONTROLLER_PATH)
                        .content(String.valueOf(professorJSON))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        verify(professorService, atLeastOnce()).save(professorDto);
    }

    @DisplayName("Delete Professor By Id")
    @Test
    void deleteProfessorByIdTest() throws Exception {

        doNothing().when(professorService).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete(PROFESSOR_CONTROLLER_PATH + "1"))
                .andExpect(status().isAccepted());

        verify(professorService, atLeastOnce()).deleteById(1);
    }

    @DisplayName("Delete Professor By Id Exception")
    @Test
    void deleteProfessorByIdExceptionTest() throws Exception {

        doThrow(new SecurityException(SOMETHING_HAPPENED)).when(professorService).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete(PROFESSOR_CONTROLLER_PATH + "1"))
                .andExpect(status().isInternalServerError());

        verify(professorService, atLeastOnce()).deleteById(1);
    }

    @DisplayName("Delete All Professors")
    @Test
    void deleteAllProfessorTest() throws Exception {

        doNothing().when(professorService).delete();

        mockMvc.perform(MockMvcRequestBuilders.delete(PROFESSOR_CONTROLLER_PATH))
                .andExpect(status().isAccepted());

        verify(professorService, atLeastOnce()).delete();
    }

    @DisplayName("Delete All Professors Exception")
    @Test
    void deleteAllProfessorExceptionTest() throws Exception {

        doThrow(new ServiceException(SOMETHING_HAPPENED)).when(professorService).delete();

        mockMvc.perform(MockMvcRequestBuilders.delete(PROFESSOR_CONTROLLER_PATH))
                .andExpect(status().isInternalServerError());

        verify(professorService, atLeastOnce()).delete();
    }
}