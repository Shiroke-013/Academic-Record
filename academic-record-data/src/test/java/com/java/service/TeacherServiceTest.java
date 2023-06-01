package com.java.service;


import com.java.dto.TeacherDto;
import com.java.mappers.TeacherMapper;
import com.java.model.Teacher;
import com.java.persistence.TeacherPersistence;
import com.java.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class TeacherServiceTest {

    @InjectMocks
    private TeacherServiceImpl teacherServiceImpl;

    @Mock
    private TeacherPersistence teacherPersistence;

    @DisplayName("Creating Teacher")
    @Test
    void createTeacherServiceTest() throws Exception {

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setFirstName("Daniel");
        teacherDto.setLastName("Montoya");
        teacherDto.setEmail("damon@kai.com");
        teacherDto.setPassword("test4echo!");

        doNothing().when(teacherPersistence).create(any(Teacher.class));

        Teacher result = teacherServiceImpl.save(teacherDto);

        verify(teacherPersistence).create(any(Teacher.class));

        assertNotNull(result);
        assertEquals("Daniel", result.getFirstName());
        assertEquals("Montoya", result.getLastName());
        assertEquals("damon@kai.com", result.getEmail());
        assertEquals("test4echo!", result.getPassword());
    }

    /*
    @DisplayName("Creating Teacher Exception")
    @Test
    void createTeacherServiceExceptionTest() throws ExceptionPersistence, ExceptionService {

        TeacherDto teacherDto = new TeacherDto();

        when(teacherPersistence.create(any(Teacher.class))).thenThrow(new ExceptionService(SOMETHING_HAPPENED));

        assertThrows(ExceptionService.class, (Executable) teacherServiceImpl.save(teacherDto));

    }*/

    @DisplayName("Get Teacher by Id")
    @Test
    void getTeacherByIdServiceTest() throws Exception {

        Teacher teacher1 = new Teacher();
        teacher1.setId(1);
        teacher1.setFirstName("John");
        teacher1.setLastName("Doe");
        teacher1.setEmail("jodo@kai.com");
        teacher1.setPassword("1234567890");

        when(teacherPersistence.findById(1)).thenReturn(teacher1);

        TeacherDto teacherDto = teacherServiceImpl.findById(1);
        Teacher result = TeacherMapper.INSTANCE.dtoToTeacher(teacherDto);
        assertEquals(teacher1, result);

    }


    @DisplayName("Get All Teachers")
    @Test
    void getAllTeacherServiceTest() throws Exception {

        Teacher teacher1 = new Teacher();
        teacher1.setId(1);
        teacher1.setFirstName("John");
        teacher1.setLastName("Doe");
        teacher1.setEmail("jodo@kai.com");
        teacher1.setPassword("1234567890");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2);
        teacher2.setFirstName("Jane");
        teacher2.setLastName("Doe");
        teacher2.setEmail("jado@kai.com");
        teacher2.setPassword("098765321");

        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher1);
        teachers.add(teacher2);

        when(teacherPersistence.findAll()).thenReturn(teachers);

        Collection<TeacherDto> teacherDtoSet = teacherServiceImpl.findAll();
        Collection<Teacher> result = null;

        for (TeacherDto teacherDto : teacherDtoSet) {
            result.add(TeacherMapper.INSTANCE.dtoToTeacher(teacherDto));
        }

        assertEquals(teachers, result);

    }

    @DisplayName("Delete Teacher By Id")
    @Test
    void deleteTeacherByIdServiceTest() throws Exception{

        Teacher teacher1 = new Teacher();
        teacher1.setId(1);
        teacher1.setFirstName("John");
        teacher1.setLastName("Doe");
        teacher1.setEmail("jodo@kai.com");
        teacher1.setPassword("1234567890");

        doNothing().when(teacherPersistence).deleteById(1);

        assertDoesNotThrow(() -> teacherServiceImpl.deleteById(1));

    }

    @DisplayName("Delete All Teachers")
    @Test
    void deleteAllTeacherServiceTest() throws Exception{

        doNothing().when(teacherPersistence).delete();

        assertDoesNotThrow(() -> teacherServiceImpl.delete());

    }

    /*
    @DisplayName("Update Teacher")
    @Test
    void updateTeacherServiceTest() throws Exception{

        Integer id = 1;
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setPassword("test4echo!");

        Teacher teacher1 = new Teacher();
        teacher1.setId(1);
        teacher1.setFirstName("John");
        teacher1.setLastName("Doe");
        teacher1.setEmail("jodo@kai.com");
        teacher1.setPassword("1234567890");

        doNothing().when(teacherPersistence.update(teacher1, id));

        Teacher result = teacherServiceImpl.findById(id);
        assertNotNull(result);
        assertEquals("test4echo!", result.getPassword());
    }*/

}