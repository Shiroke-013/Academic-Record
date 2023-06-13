package com.java.service;


import com.java.dto.GradeDto;
import com.java.dto.TeacherDto;
import com.java.mappers.TeacherMapper;
import com.java.model.Course;
import com.java.model.Grade;
import com.java.model.Student;
import com.java.model.Subject;
import com.java.model.Teacher;
import com.java.persistence.ExceptionPersistence;
import com.java.persistence.GradePersistence;
import com.java.persistence.StudentPersistence;
import com.java.persistence.SubjectPersistence;
import com.java.persistence.TeacherPersistence;
import com.java.service.impl.TeacherServiceImpl;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class TeacherServiceTest {

    private static final String NOT_WORKING = "Not working";

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Mock
    private TeacherPersistence teacherPersistence;

    @Mock
    private StudentPersistence studentPersistence;

    @Mock
    private SubjectPersistence subjectPersistence;

    @Mock
    private GradePersistence gradePersistence;

    @DisplayName("Save Teacher Dto")
    @Test
    void save_ReturnsSavedTeacher() throws ExceptionPersistence, ExceptionService {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setFirstName("Andres");
        teacherDto.setLastName("Chaves");
        teacherDto.setEmail("andres@kai.com");
        teacherDto.setPassword("1234567890");

        Teacher teacher = TeacherMapper.INSTANCE.dtoToTeacher(teacherDto);
        doNothing().when(teacherPersistence).create(ArgumentMatchers.any(Teacher.class));

        Teacher savedTeacher = teacherService.save(teacherDto);

        assertEquals(teacher.getFirstName(), savedTeacher.getFirstName());
        assertEquals(teacher.getLastName(), savedTeacher.getLastName());
        assertEquals(teacher.getEmail(), savedTeacher.getEmail());
        assertEquals(teacher.getPassword(), savedTeacher.getPassword());
        verify(teacherPersistence, times(1)).create(ArgumentMatchers.any(Teacher.class));
    }

    @DisplayName("Save Teacher Dto Exception")
    @Test
    void save_ExceptionThrown() throws ExceptionPersistence {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setFirstName("Andres");
        teacherDto.setLastName("Chaves");
        teacherDto.setEmail("andres@kai.com");
        teacherDto.setPassword("1234567890");

        doThrow(new RuntimeException(NOT_WORKING)).when(teacherPersistence).create(ArgumentMatchers.any(Teacher.class));

        assertThrows(ExceptionService.class, ()-> teacherService.save(teacherDto));
        verify(teacherPersistence, times(1)).create(ArgumentMatchers.any(Teacher.class));
    }

    @DisplayName("Find All Teachers")
    @Test
    void findAll_ReturnsSetOfTeachersDtos() throws ExceptionPersistence, ExceptionService {
        Teacher teacher1 = new Teacher();
        teacher1.setId(1);
        teacher1.setFirstName("Teacher");
        teacher1.setLastName("First");
        teacher1.setEmail("tf@ar.com");
        teacher1.setPassword("!Test4echo");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2);
        teacher2.setFirstName("Teacher");
        teacher2.setLastName("Second");
        teacher2.setEmail("tf@ar.com");
        teacher2.setPassword("!Test4echo");

        Collection<Teacher> teachers = Arrays.asList(teacher1, teacher2);
        when(teacherPersistence.findAll()).thenReturn(teachers);

        Collection<TeacherDto> teacherDtos = teacherService.findAll();

        assertEquals(2, teacherDtos.size());
        assertTrue(teacherDtos.stream().anyMatch(dto -> Objects.equals(dto.getFirstName(), "Teacher") && dto.getLastName().equals("First")));
        assertTrue(teacherDtos.stream().anyMatch(dto -> Objects.equals(dto.getFirstName(), "Teacher") && dto.getLastName().equals("Second")));
        verify(teacherPersistence, times(1)).findAll();
    }

    @DisplayName("Find All Teachers Exception")
    @Test
    void findAll_ExceptionThrown() throws ExceptionPersistence {
        when(teacherPersistence.findAll()).thenThrow(new PersistenceException("Database connection error"));

        assertThrows(ExceptionService.class, () -> teacherService.findAll());
        verify(teacherPersistence, times(1)).findAll();
    }

    @DisplayName("Find Teacher By Id")
    @Test
    void findById_ExistingTeacherId_ReturnsTeacherDto() throws ExceptionService, ExceptionPersistence {
        int teacherId = 1;
        Teacher teacher1 = new Teacher();
        teacher1.setId(teacherId);
        teacher1.setFirstName("Teacher");
        teacher1.setLastName("First");
        teacher1.setEmail("tf@ar.com");
        teacher1.setPassword("!Test4echo");

        when(teacherPersistence.findById(teacherId)).thenReturn(teacher1);

        TeacherDto teacherDto = teacherService.findById(teacherId);

        assertEquals(teacher1.getFirstName(), teacherDto.getFirstName());
        assertEquals(teacher1.getLastName(), teacherDto.getLastName());
        assertEquals(teacher1.getEmail(), teacherDto.getEmail());
        assertEquals(teacher1.getPassword(), teacherDto.getPassword());
        verify(teacherPersistence, times(1)).findById(teacherId);
    }

    @DisplayName("Find Teacher By Nonexistent Id")
    @Test
    void findById_NonexistentTeacherId_ReturnsTeacherDto() throws ExceptionPersistence {
        int teacherId = 1;
        when(teacherPersistence.findById(teacherId)).thenReturn(null);

        assertThrows(ExceptionService.class, () -> teacherService.findById(teacherId));
        verify(teacherPersistence, times(1)).findById(teacherId);
    }

    @DisplayName("Find Teacher By Id Exception")
    @Test
    void findById_ExceptionThrown() throws ExceptionPersistence {
        int teacherId = 1;
        when(teacherPersistence.findById(teacherId)).thenThrow(new PersistenceException("Database connection error"));

        assertThrows(ExceptionService.class, () -> teacherService.findById(teacherId));
        verify(teacherPersistence, times(1)).findById(teacherId);
    }

    @DisplayName("Delete")
    @Test
    void delete_SuccessfulDeletion() throws ExceptionService, ExceptionPersistence {
        doNothing().when(teacherPersistence).delete();

        teacherService.delete();

        verify(teacherPersistence, times(1)).delete();
    }

    @DisplayName("Delete Exception")
    @Test
    void delete_ExceptionThrown() throws ExceptionPersistence {
        doThrow(new PersistenceException("Database connection error")).when(teacherPersistence).delete();

        assertThrows(ExceptionService.class, () -> teacherService.delete());
        verify(teacherPersistence, times(1)).delete();
    }

    @DisplayName("Delete By Id")
    @Test
    void deleteById_SuccessfulDeletion() throws ExceptionService, ExceptionPersistence {
        Integer id = 1;
        doNothing().when(teacherPersistence).deleteById(id);

        teacherService.deleteById(id);

        verify(teacherPersistence, times(1)).deleteById(id);
    }

    @DisplayName("Delete By Id Exception")
    @Test
    void deleteById_ExceptionThrown() throws ExceptionPersistence {
        Integer id = 1;
        doThrow(new PersistenceException("Database connection error")).when(teacherPersistence).deleteById(id);

        assertThrows(ExceptionService.class, () -> teacherService.deleteById(id));
        verify(teacherPersistence, times(1)).deleteById(id);
    }

    @DisplayName("Update Teacher")
    @Test
    void update_SuccessfulUpdate() throws ExceptionService, ExceptionPersistence {
        Integer id = 1;
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setFirstName("Joel");
        teacherDto.setLastName("Last");
        teacherDto.setEmail("jl@ar.com");
        teacherDto.setPassword("!Test7Echo");

        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setFirstName("Joel");
        teacher.setLastName("Hogan");
        teacher.setEmail("jh@ar.com");
        teacher.setPassword("oldpassword");

        when(teacherPersistence.findById(id)).thenReturn(teacher);
        doNothing().when(teacherPersistence).update(teacher);

        teacherService.update(teacherDto, id);

        verify(teacherPersistence, times(1)).findById(id);
        verify(teacherPersistence, times(1)).update(teacher);
        assertEquals(teacherDto.getFirstName(), teacher.getFirstName());
        assertEquals(teacherDto.getLastName(), teacher.getLastName());
        assertEquals(teacherDto.getEmail(), teacher.getEmail());
        assertEquals(teacherDto.getPassword(), teacher.getPassword());
    }

    @DisplayName("Update Exception")
    @Test
    void update_ExceptionThrown() throws ExceptionPersistence {
        Integer id = 1;
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setFirstName("Joel");
        teacherDto.setLastName("Last");
        teacherDto.setEmail("jl@ar.com");
        teacherDto.setPassword("!Test7Echo");
        when(teacherPersistence.findById(id)).thenThrow(new ExceptionPersistence("Database connection error"));

        assertThrows(ExceptionService.class, () -> teacherService.update(teacherDto, id));
        verify(teacherPersistence, times(1)).findById(id);
        verify(teacherPersistence, never()).update(any());
    }

    @DisplayName("Find Course for Teacher")
    @Test
    void findCourse_ReturnsCourseName() throws ExceptionService, ExceptionPersistence {
        Integer id = 1;
        Teacher teacher = new Teacher();
        teacher.setId(id);
        Course course = new Course();
        course.setCourseName("Computer Science");
        teacher.setCourse(course);
        when(teacherPersistence.findById(id)).thenReturn(teacher);

        String courseName = teacherService.findCourse(id);

        verify(teacherPersistence, times(1)).findById(id);
        assertEquals(course.getCourseName(), courseName);
    }

    @DisplayName("Find Course for Teacher")
    @Test
    void findCourse_ExceptionThrown() throws ExceptionPersistence {
        Integer id = 1;
        when(teacherPersistence.findById(id)).thenThrow(new ExceptionPersistence("Database connection error"));

        assertThrows(ExceptionService.class, () -> teacherService.findCourse(id));
        verify(teacherPersistence, times(1)).findById(id);
    }

    @DisplayName("Find Subjects")
    @Test
    void findSubjects_TeacherExists_ReturnsListOfSubjectNames() throws ExceptionService, ExceptionPersistence {
        Integer id = 1;
        Teacher teacher = new Teacher();
        teacher.setId(id);
        Set<Subject> subjects = new HashSet<>();
        Subject subject1 = new Subject();
        subject1.setSubjectName("Math");
        subjects.add(subject1);
        Subject subject2 = new Subject();
        subject2.setSubjectName("Science");
        subjects.add(subject2);
        teacher.setSubjects(subjects);

        when(teacherPersistence.findById(id)).thenReturn(teacher);

        Object result = teacherService.findSubjects(id);

        verify(teacherPersistence, times(1)).findById(id);
        assertNotNull(result);
        assertTrue(result instanceof Set);
        Set<String> subjectNames = (Set<String>) result;
        assertEquals(2, subjectNames.size());
        assertTrue(subjectNames.contains("Math"));
        assertTrue(subjectNames.contains("Science"));
    }

    @DisplayName("Find Subjects Exception")
    @Test
    void findSubjects_TeacherDoesNotExist_ThrowsExceptionService() throws ExceptionPersistence {
        Integer id = 1;
        when(teacherPersistence.findById(id)).thenReturn(null);

        assertThrows(ExceptionService.class, () -> teacherService.findSubjects(id));
        verify(teacherPersistence, times(1)).findById(id);
    }

    @DisplayName("Teacher adds Grade")
    @Test
    void addGrade_CreatesGradeAndUpdatesAssociations() throws ExceptionService, ExceptionPersistence {
        Integer studentId = 1;
        Integer subjectId = 2;
        GradeDto gradeDto = new GradeDto();
        gradeDto.setMark(5.0);
        Grade grade = new Grade();
        grade.setId(1);
        grade.setMark(gradeDto.getMark());
        Student student = new Student();
        student.setId(studentId);
        Subject subject = new Subject();
        subject.setId(subjectId);

        when(studentPersistence.findById(studentId)).thenReturn(Optional.of(student));
        when(subjectPersistence.findById(subjectId)).thenReturn(subject);
        doNothing().when(gradePersistence).create(any(Grade.class));

        teacherService.addGrade(gradeDto, subjectId, studentId);

        verify(studentPersistence, times(1)).findById(studentId);
        verify(subjectPersistence, times(1)).findById(subjectId);
        verify(gradePersistence, times(1)).create(any(Grade.class));

        assertEquals(grade.getMark(), student.getGrades().iterator().next().getMark());
        assertEquals(grade.getMark(), subject.getGrades().iterator().next().getMark());
    }

    @DisplayName("Add Grade Exception")
    @Test
    void addGrade_ExceptionThrown() throws ExceptionPersistence {
        Integer studentId = 1;
        Integer subjectId = 2;
        GradeDto gradeDto = new GradeDto();

        when(studentPersistence.findById(studentId)).thenThrow(new ExceptionPersistence("Database connection error"));

        assertThrows(ExceptionService.class, () -> teacherService.addGrade(gradeDto, subjectId, studentId));
        verify(studentPersistence, times(1)).findById(studentId);
        verify(subjectPersistence, never()).findById(anyInt());
        verify(gradePersistence, never()).create(any(Grade.class));
    }
}