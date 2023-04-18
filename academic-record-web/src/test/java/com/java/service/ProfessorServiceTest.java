package com.java.service;


import com.java.dto.ProfessorDto;
import com.java.model.Professor;
import com.java.persistence.ProfessorPersistence;
import com.java.service.impl.ProfessorServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class ProfessorServiceTest {

    private static final String SOMETHING_HAPPENED = "Something happened...";

    @InjectMocks
    private ProfessorServiceImpl professorServiceImpl;

    @Mock
    private ProfessorPersistence professorPersistence;

    @DisplayName("Creating Professor")
    @Test
    void createProfessorServiceTest() throws Exception {

        ProfessorDto professorDto = new ProfessorDto();
        professorDto.setFirstName("Daniel");
        professorDto.setLastName("Montoya");
        professorDto.setEmail("damon@kai.com");
        professorDto.setPassword("test4echo!");

        doNothing().when(professorPersistence).create(any(Professor.class));

        Professor result = professorServiceImpl.save(professorDto);

        verify(professorPersistence).create(any(Professor.class));

        assertNotNull(result);
        assertEquals("Daniel", result.getFirstName());
        assertEquals("Montoya", result.getLastName());
        assertEquals("damon@kai.com", result.getEmail());
        assertEquals("test4echo!", result.getPassword());
    }

    /*
    @DisplayName("Creating Professor Exception")
    @Test
    void createProfessorServiceExceptionTest() throws ExceptionPersistence, ExceptionService {

        ProfessorDto professorDto = new ProfessorDto();

        when(professorPersistence.create(any(Professor.class))).thenThrow(new ExceptionService(SOMETHING_HAPPENED));

        assertThrows(ExceptionService.class, (Executable) professorServiceImpl.save(professorDto));

    }*/

    @DisplayName("Get Professor by Id")
    @Test
    void getProfessorByIdServiceTest() throws Exception {

        Professor professor1 = new Professor();
        professor1.setId(1);
        professor1.setFirstName("John");
        professor1.setLastName("Doe");
        professor1.setEmail("jodo@kai.com");
        professor1.setPassword("1234567890");

        when(professorPersistence.findById(1)).thenReturn(professor1);

        Professor result = professorServiceImpl.findById(1);
        assertEquals(professor1, result);

    }


    @DisplayName("Get All Professors")
    @Test
    void getAllProfessorServiceTest() throws Exception {

        Professor professor1 = new Professor();
        professor1.setId(1);
        professor1.setFirstName("John");
        professor1.setLastName("Doe");
        professor1.setEmail("jodo@kai.com");
        professor1.setPassword("1234567890");

        Professor professor2 = new Professor();
        professor2.setId(2);
        professor2.setFirstName("Jane");
        professor2.setLastName("Doe");
        professor2.setEmail("jado@kai.com");
        professor2.setPassword("098765321");

        List<Professor> professors = new ArrayList<>();
        professors.add(professor1);
        professors.add(professor2);

        when(professorPersistence.getAll()).thenReturn(professors);

        Collection<Professor> result = professorServiceImpl.findAll();
        assertEquals(professors, result);

    }

    @DisplayName("Delete Professor By Id")
    @Test
    void deleteProfessorByIdServiceTest() throws Exception{

        Professor professor1 = new Professor();
        professor1.setId(1);
        professor1.setFirstName("John");
        professor1.setLastName("Doe");
        professor1.setEmail("jodo@kai.com");
        professor1.setPassword("1234567890");

        doNothing().when(professorPersistence).deleteById(1);

        assertDoesNotThrow(() -> professorServiceImpl.deleteById(1));

    }

    @DisplayName("Delete All Professors")
    @Test
    void deleteAllProfessorServiceTest() throws Exception{

        doNothing().when(professorPersistence).delete();

        assertDoesNotThrow(() -> professorServiceImpl.delete());

    }

    /*
    @DisplayName("Update Professor")
    @Test
    void updateProfessorServiceTest() throws Exception{

        Integer id = 1;
        ProfessorDto professorDto = new ProfessorDto();
        professorDto.setPassword("test4echo!");

        Professor professor1 = new Professor();
        professor1.setId(1);
        professor1.setFirstName("John");
        professor1.setLastName("Doe");
        professor1.setEmail("jodo@kai.com");
        professor1.setPassword("1234567890");

        doNothing().when(professorPersistence.update(professor1, id));

        Professor result = professorServiceImpl.findById(id);
        assertNotNull(result);
        assertEquals("test4echo!", result.getPassword());
    }*/

}
