package com.java.service;

import com.java.dto.GradeDto;
import com.java.model.Grade;
import com.java.persistence.ExceptionPersistence;
import com.java.persistence.GradePersistence;
import com.java.service.impl.GradeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class GradeServiceTest {

    private static final String DB_ERROR = "Database connection error";

    @InjectMocks
    private GradeServiceImpl gradeService;

    @Mock
    private GradePersistence gradePersistence;

    @DisplayName("Save Grade Dto")
    @Test
    void save_ValidGradeDto_CreatesGrade() throws ExceptionService, ExceptionPersistence {
        GradeDto gradeDto = new GradeDto();
        gradeDto.setMark(5.0);
        Grade grade = new Grade();
        grade.setId(1);
        grade.setMark(gradeDto.getMark());

        doNothing().when(gradePersistence).create(any(Grade.class));

        gradeService.save(gradeDto);

        verify(gradePersistence, times(1)).create(any(Grade.class));
    }

    @DisplayName("Save Grade Dto Exception")
    @Test
    void save_ExceptionThrown() throws ExceptionPersistence {
        GradeDto gradeDto = new GradeDto();
        gradeDto.setMark(5.0);
        Grade grade = new Grade();
        grade.setId(1);
        grade.setMark(gradeDto.getMark());
        doThrow(new ExceptionPersistence(DB_ERROR)).when(gradePersistence).create(any(Grade.class));

        assertThrows(ExceptionService.class, () -> gradeService.save(gradeDto));
        verify(gradePersistence, times(1)).create(any(Grade.class));
    }

    @DisplayName("Find Grade By Id")
    @Test
    void findById_ExistingId_ReturnsGradeDto() throws ExceptionService, ExceptionPersistence {
        Integer id = 1;
        Grade grade = new Grade();
        grade.setId(id);
        grade.setMark(3.0);

        when(gradePersistence.findById(id)).thenReturn(grade);

        Object result = gradeService.findById(id);

        assertTrue(result instanceof GradeDto);
        assertEquals(grade.getMark(), ((GradeDto) result).getMark());
        verify(gradePersistence, times(1)).findById(id);
    }

    @DisplayName("Find Grade By Id Exception")
    @Test
    void findById_ExceptionThrown() throws ExceptionPersistence {
        Integer id = 1;

        when(gradePersistence.findById(id)).thenThrow(new ExceptionPersistence(DB_ERROR));

        assertThrows(ExceptionService.class, () -> gradeService.findById(id));
        verify(gradePersistence, times(1)).findById(id);
    }

    @DisplayName("Find All Grades")
    @Test
    void findAll_ReturnsCollectionOfGradeDto() throws ExceptionService, ExceptionPersistence {
        List<Grade> grades = Arrays.asList(new Grade(), new Grade());

        when(gradePersistence.findAll()).thenReturn(grades);

        Collection<GradeDto> result = gradeService.findAll();

        assertEquals(grades.size(), result.size());
        assertTrue(result.stream().allMatch(Objects::nonNull));
        verify(gradePersistence, times(1)).findAll();
    }

    @DisplayName("Find All Grades Exception")
    @Test
    void findAll_ExceptionThrown() throws ExceptionPersistence {
        when(gradePersistence.findAll()).thenThrow(new ExceptionPersistence(DB_ERROR));

        assertThrows(ExceptionService.class, () -> gradeService.findAll());
        verify(gradePersistence, times(1)).findAll();
    }

    @DisplayName("Delete All Grades")
    @Test
    void deleteAll_ReturnsNothingAndDeletesAllGrades() throws ExceptionService, ExceptionPersistence {
        gradeService.deleteAll();
        verify(gradePersistence, times(1)).deleteAll();
    }

    @DisplayName("Delete All Grades Exception")
    @Test
    void deleteAll_ExceptionThrown() throws ExceptionPersistence {
        doThrow(new ExceptionPersistence(DB_ERROR)).when(gradePersistence).deleteAll();
        assertThrows(ExceptionService.class, () -> gradeService.deleteAll());
    }

    @DisplayName("Delete Grade By Id")
    @Test
    void deleteById_ReturnsNothingAndDeletesGrade() throws ExceptionService, ExceptionPersistence {
        Integer id = 1;
        gradeService.deleteById(id);
        verify(gradePersistence, times(1)).delete(id);
    }

    @DisplayName("Delete Grade By Id Exception")
    @Test
    void deleteById_ExceptionThrown() throws ExceptionPersistence {
        Integer id = 1;
        doThrow(new ExceptionPersistence(DB_ERROR)).when(gradePersistence).delete(id);
        assertThrows(ExceptionService.class, () -> gradeService.deleteById(id));
    }

    @DisplayName("Update Grade")
    @Test
    void update_ValidDtoAndId_UpdatesGrade() throws ExceptionService, ExceptionPersistence {
        Integer id = 1;
        GradeDto gradeDto = new GradeDto();
        gradeDto.setMark(1.7);
        Grade grade = new Grade();
        grade.setId(id);

        when(gradePersistence.findById(id)).thenReturn(grade);
        doNothing().when(gradePersistence).create(grade);

        gradeService.update(gradeDto, id);

        verify(gradePersistence, times(1)).findById(id);
        verify(gradePersistence, times(1)).create(grade);
        assertEquals(gradeDto.getMark(), grade.getMark());
    }

    @DisplayName("Update Grade Exception")
    @Test
    void update_ExceptionThrown() throws ExceptionPersistence {
        Integer id = 1;
        GradeDto gradeDto = new GradeDto();
        gradeDto.setMark(1.7);
        Grade grade = new Grade();
        grade.setId(id);

        when(gradePersistence.findById(id)).thenReturn(grade);
        doThrow(ExceptionPersistence.class).when(gradePersistence).create(any(Grade.class));

        assertThrows(ExceptionService.class, () -> gradeService.update(gradeDto, id));
        verify(gradePersistence, times(1)).findById(id);
        verify(gradePersistence, times(1)).create(grade);
    }
}
