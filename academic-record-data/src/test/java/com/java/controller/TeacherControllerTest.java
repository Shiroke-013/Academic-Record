package com.java.controller;


import com.java.dto.TeacherDto;
import com.java.model.Teacher;
import com.java.service.ExceptionService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
class TeacherControllerTest {

    private static final String TEACHER_CONTROLLER_PATH = "/teacher/";
    private static final String SOMETHING_HAPPENED = "Something happened";

    @InjectMocks
    private TeacherController teacherController;

    @Mock
    private TeacherService teacherService;

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

    /*
    @DisplayName("Creating Teacher Exception")
    @Test
    void saveTeacherExceptionTest() throws Exception {

        TeacherDto teacherDto = new TeacherDto();

        when(teacherService.save(teacherDto)).thenThrow(new ExceptionService(SOMETHING_HAPPENED));

        JSONObject teacherJSON = new JSONObject();

        mockMvc.perform(MockMvcRequestBuilders.post(TEACHER_CONTROLLER_PATH)
                        .content(String.valueOf(teacherJSON))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        verify(teacherService, atLeastOnce()).save(teacherDto);
    }*/

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
}