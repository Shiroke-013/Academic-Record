package com.java.service;

import com.java.dto.SubjectDto;
import com.java.model.Course;
import com.java.model.Student;
import com.java.model.Subject;
import com.java.model.Teacher;
import com.java.persistence.ExceptionPersistence;
import com.java.persistence.SubjectPersistence;
import com.java.persistence.TeacherPersistence;
import com.java.service.impl.SubjectServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class SubjectServiceTest {

    private static final String DB_ERROR = "Database connection error";

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Mock
    private SubjectPersistence subjectPersistence;

    @Mock
    private TeacherPersistence teacherPersistence;

    @DisplayName("Save SubjectDto")
    @Test
    void save_ValidSubjectDto_CreatesSubject() throws ExceptionService, ExceptionPersistence {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSubjectName("Math");
        subjectDto.setDuration(80);
        subjectDto.setCapacity(20);
        subjectDto.setStartDate(LocalDate.parse("2023-01-30"));
        subjectDto.setEndDate(LocalDate.parse("2023-05-17"));

        doNothing().when(subjectPersistence).create(any(Subject.class));

        subjectService.save(subjectDto);

        verify(subjectPersistence, times(1)).create(any(Subject.class));
    }

    @DisplayName("Save SubjectDto Exception")
    @Test
    void save_ValidSubjectDto_ExceptionThrown() throws ExceptionPersistence {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSubjectName("Math");
        subjectDto.setDuration(80);
        subjectDto.setCapacity(20);
        subjectDto.setStartDate(LocalDate.parse("2023-01-30"));
        subjectDto.setEndDate(LocalDate.parse("2023-05-17"));

        doThrow(new ExceptionPersistence(DB_ERROR)).when(subjectPersistence).create(any(Subject.class));

        assertThrows(ExceptionService.class, () -> subjectService.save(subjectDto));
        verify(subjectPersistence, times(1)).create(any(Subject.class));
    }

    @DisplayName("Find Subject By Id")
    @Test
    void findById_ExistingSubjectId_ReturnsSubjectDto() throws ExceptionService, ExceptionPersistence {
        int subjectId = 1;
        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setSubjectName("Math");
        subject.setDuration(80);
        subject.setCapacity(20);
        subject.setStartDate(LocalDate.parse("2023-01-30"));
        subject.setEndDate(LocalDate.parse("2023-05-17"));

        when(subjectPersistence.findById(subjectId)).thenReturn(subject);

        SubjectDto result = subjectService.findById(subjectId);

        assertNotNull(result);
        assertEquals(subject.getSubjectName(), result.getSubjectName());
        assertEquals(subject.getDuration(), result.getDuration());
        assertEquals(subject.getCapacity(), result.getCapacity());
        assertEquals(subject.getStartDate(), result.getStartDate());
        assertEquals(subject.getEndDate(), result.getEndDate());

        verify(subjectPersistence, times(1)).findById(subjectId);
    }

    @DisplayName("Find Subject By Id Exception")
    @Test
    void findById_ExistingSubjectId_ExceptionThrown() throws ExceptionPersistence {
        int subjectId = 1;

        when(subjectPersistence.findById(subjectId)).thenThrow(new ExceptionPersistence(DB_ERROR));

        assertThrows(ExceptionService.class, () -> subjectService.findById(subjectId));
        verify(subjectPersistence, times(1)).findById(subjectId);
    }

    @DisplayName("Find All Subjects")
    @Test
    void findAll_ReturnsCollectionOfSubjectDto() throws ExceptionPersistence, ExceptionService {
        List<Subject> subjects = Arrays.asList(new Subject(), new Subject());

        when(subjectPersistence.findAll()).thenReturn(subjects);

        Collection<SubjectDto> result = subjectService.findAll();

        assertEquals(subjects.size(), result.size());
        assertTrue(result.stream().allMatch(Objects::nonNull));
        verify(subjectPersistence, times(1)).findAll();
    }

    @DisplayName("Find All Subjects Exception")
    @Test
    void findAll_ExceptionThrown() throws ExceptionPersistence {
        when(subjectPersistence.findAll()).thenThrow(new ExceptionPersistence(DB_ERROR));

        assertThrows(ExceptionService.class, () -> subjectService.findAll());
        verify(subjectPersistence, times(1)).findAll();
    }

    @DisplayName("Delete All Subjects")
    @Test
    void deleteAll_ReturnsNothingAndDeleteAllSubjects() throws ExceptionService, ExceptionPersistence {
        doNothing().when(subjectService).deleteAll();
        verify(subjectPersistence, times(1)).deleteAll();
    }

    @DisplayName("Delete All Subjects Exception")
    @Test
    void deleteAll_ExceptionThrown() throws ExceptionPersistence {
        doThrow(new ExceptionPersistence(DB_ERROR)).when(subjectPersistence).deleteAll();
        assertThrows(ExceptionService.class, () -> subjectService.deleteAll());
    }

    @DisplayName("Delete Subject By Id")
    @Test
    void deleteById_ReturnsNothingAndDeletesSubject() throws ExceptionService, ExceptionPersistence {
        Integer id = 1;
        subjectService.deleteById(id);
        verify(subjectPersistence, times(1)).delete(id);
    }

    @DisplayName("Delete Subject By Id Exception")
    @Test
    void deleteById_ExceptionThrown() throws ExceptionPersistence {
        Integer id = 1;
        doThrow(new ExceptionPersistence(DB_ERROR)).when(subjectPersistence).delete(id);
        assertThrows(ExceptionService.class, () -> subjectService.deleteById(id));
    }

    @DisplayName("Update Subject")
    @Test
    void update_ValidSubjectDto_UpdatesSubject() throws ExceptionService, ExceptionPersistence {
        int subjectId = 1;
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSubjectName("Math");
        subjectDto.setDuration(65);
        subjectDto.setCapacity(25);

        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setSubjectName("Physics");
        subjectDto.setDuration(80);
        subjectDto.setCapacity(20);
        subjectDto.setStartDate(LocalDate.parse("2023-01-30"));
        subjectDto.setEndDate(LocalDate.parse("2023-05-17"));

        when(subjectPersistence.findById(subjectId)).thenReturn(subject);
        subjectService.update(subjectDto, subjectId);

        verify(subjectPersistence, times(1)).update(subject);
        assertEquals(subjectDto.getSubjectName(), subject.getSubjectName());
        assertEquals(subjectDto.getDuration(), subject.getDuration());
        assertEquals(subjectDto.getCapacity(), subject.getCapacity());
        assertEquals(subjectDto.getStartDate(), subject.getStartDate());
        assertEquals(subjectDto.getEndDate(), subject.getEndDate());
    }

    @DisplayName("Add Teacher to Subject")
    @Test
    void addTeacher_ValidSubjectAndTeacher_TeacherAddedToSubject() throws ExceptionService, ExceptionPersistence {
        int subjectId = 1;
        int teacherId = 1;

        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setSubjectName("Math");

        HashSet <Course> courses = new HashSet<>();
        Course course = new Course();
        course.setCourseName("Computer Science");
        courses.add(course);
        subject.setCourses(courses);

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setFirstName("Teacher");
        teacher.setLastName("One");
        teacher.setCourse(course);

        when(subjectPersistence.findById(subjectId)).thenReturn(subject);
        when(teacherPersistence.findById(teacherId)).thenReturn(teacher);

        subjectService.addTeacher(subjectId, teacherId);

        verify(subjectPersistence, times(1)).create(subject);
        verify(teacherPersistence, times(1)).create(teacher);
        assertEquals(teacher, subject.getTeacher());
        assertTrue(teacher.getSubjects().contains(subject));
    }

    @DisplayName("Add Teacher to Subject - Teacher not in Course")
    @Test
    void addTeacher_TeacherNotInCourse_ExceptionThrown() throws ExceptionPersistence {
        int subjectId = 1;
        int teacherId = 1;

        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setSubjectName("Math");

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setFirstName("Teacher");
        teacher.setLastName("One");

        when(subjectPersistence.findById(subjectId)).thenReturn(subject);
        when(teacherPersistence.findById(teacherId)).thenReturn(teacher);

        ExceptionService exception = assertThrows(ExceptionService.class, () -> subjectService.addTeacher(subjectId, teacherId));
        assertEquals("Teacher is not registered in the subject course.", exception.getMessage());

        verify(subjectPersistence, never()).create(any());
        verify(teacherPersistence, never()).create(any());
    }

    @DisplayName("Add Teacher to Subject Exception")
    @Test
    void addTeacher_TeacherNotRegisteredInSubjectCourse_ThrowsException() throws ExceptionPersistence {
        int subjectId = 1;
        int teacherId = 1;

        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setSubjectName("Math");

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        when(subjectPersistence.findById(subjectId)).thenReturn(subject);
        when(teacherPersistence.findById(teacherId)).thenReturn(teacher);
        doThrow(new ExceptionPersistence(DB_ERROR)).when(subjectPersistence).create(subject);

        assertThrows(ExceptionService.class, () -> subjectService.addTeacher(subjectId, teacherId));
        verify(teacherPersistence, never()).create(any());
    }

    @DisplayName("Find Subject Students")
    @Test
    void findStudents_ValidSubject_ReturnsStudentNames() throws ExceptionService, ExceptionPersistence {
        int subjectId = 1;

        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setSubjectName("Math");

        Student student1 = new Student();
        student1.setId(1);
        student1.setFirstName("Student");
        student1.setLastName("One");

        Student student2 = new Student();
        student2.setId(2);
        student2.setFirstName("Student");
        student2.setLastName("Two");

        subject.getStudents().add(student1);
        subject.getStudents().add(student2);

        when(subjectPersistence.findById(subjectId)).thenReturn(subject);
        Collection<String> studentNames = subjectService.findStudents(subjectId);

        verify(subjectPersistence, times(1)).findById(subjectId);
        assertEquals(2, studentNames.size());
        assertTrue(studentNames.contains("Student One"));
        assertTrue(studentNames.contains("Student Two"));
    }

    @DisplayName("Find Subject Students Exception")
    @Test
    void findStudents_ExceptionThrown() throws ExceptionPersistence {
        int subjectId = 1;
        when(subjectPersistence.findById(subjectId)).thenThrow(new ExceptionPersistence(DB_ERROR));

        assertThrows(ExceptionService.class, () -> subjectService.findStudents(subjectId));
        verify(subjectPersistence, times(1)).findById(subjectId);
    }

    @DisplayName("Delete Teacher From Subject")
    @Test
    void deleteTeacherFromSubject_ValidData_DeletesTeacherFromSubject() throws ExceptionService, ExceptionPersistence {
        int subjectId = 1;
        int teacherId = 1;

        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setSubjectName("Math");

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setFirstName("Teacher");
        teacher.setLastName("One");

        subject.setTeacher(teacher);
        teacher.getSubjects().add(subject);

        when(subjectPersistence.findById(subjectId)).thenReturn(subject);
        when(teacherPersistence.findById(teacherId)).thenReturn(teacher);

        subjectService.deleteTeacherFromSubject(subjectId, teacherId);

        verify(subjectPersistence, times(1)).findById(subjectId);
        verify(teacherPersistence, times(1)).findById(teacherId);
        verify(teacherPersistence, times(1)).create(teacher);
        verify(subjectPersistence, times(1)).create(subject);
        assertNull(subject.getTeacher());
        assertTrue(teacher.getSubjects().isEmpty());
    }

    @DisplayName("Delete Teacher From Subject - Nonexistent Teacher")
    @Test
    void deleteTeacherFromSubject_NonExistingTeacher_ThrowsException() throws ExceptionPersistence {
        int subjectId = 1;
        int teacherId = 1;

        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setSubjectName("Math");

        when(subjectPersistence.findById(subjectId)).thenReturn(subject);
        when(teacherPersistence.findById(teacherId)).thenReturn(null);

        ExceptionService exception = assertThrows(ExceptionService.class, () -> subjectService.deleteTeacherFromSubject(subjectId, teacherId));
        assertEquals("The teacher is not registered in that subject", exception.getMessage());
        verify(subjectPersistence, times(1)).findById(subjectId);
        verify(teacherPersistence, times(1)).findById(teacherId);
        verify(teacherPersistence, never()).create(any(Teacher.class));
        verify(subjectPersistence, never()).create(any(Subject.class));
    }

    @DisplayName("Delete Teacher From Subject Exception Thrown")
    @Test
    void deleteTeacherFromSubject_ExceptionThrown() throws ExceptionPersistence {
        int subjectId = 1;
        int teacherId = 1;

        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setSubjectName("Math");

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setFirstName("Teacher");
        teacher.setLastName("One");

        subject.setTeacher(teacher);
        teacher.getSubjects().add(subject);

        when(subjectPersistence.findById(subjectId)).thenReturn(subject);
        when(teacherPersistence.findById(teacherId)).thenReturn(teacher);
        doThrow(new ExceptionPersistence(DB_ERROR)).when(subjectPersistence).create(subject);

        assertThrows(ExceptionService.class, () -> subjectService.deleteTeacherFromSubject(subjectId, teacherId));
        verify(subjectPersistence, times(1)).findById(subjectId);
        verify(teacherPersistence, times(1)).findById(teacherId);
    }
}
