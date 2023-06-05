package com.java.service;

import com.java.dto.StudentDto;
import com.java.model.Course;
import com.java.model.Student;
import com.java.model.Subject;
import com.java.persistence.CoursePersistence;
import com.java.persistence.ExceptionPersistence;
import com.java.persistence.StudentPersistence;
import com.java.persistence.SubjectPersistence;
import com.java.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.rowset.serial.SerialException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
class StudentServiceTest {

    private static final String DB_ERROR = "Database connection error";

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentPersistence studentPersistence;

    @Mock
    private CoursePersistence coursePersistence;

    @Mock
    private SubjectPersistence subjectPersistence;

    @DisplayName("Save StudentDto")
    @Test
    void save_ValidStudentDto_CreatesStudent() throws ExceptionPersistence, ExceptionService {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("Student");
        studentDto.setLastName("One");
        studentDto.setEmail("so@ar.com");
        studentDto.setPassword("!Test1Echo");

        doNothing().when(studentPersistence).create(any(Student.class));

        studentService.save(studentDto);

        verify(studentPersistence, times(1)).create(any(Student.class));
    }

    @DisplayName("Save StudentDto Exception")
    @Test
    void save_ValidStudentDto_ExceptionThrown() throws ExceptionPersistence, ExceptionService {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("Student");
        studentDto.setLastName("One");
        studentDto.setEmail("so@ar.com");
        studentDto.setPassword("!Test1Echo");

        doThrow(new ExceptionPersistence(DB_ERROR)).when(studentPersistence).create(any(Student.class));

        assertThrows(ExceptionService.class, () -> studentService.save(studentDto));
        verify(studentPersistence, times(1)).create(any(Student.class));
    }

    @DisplayName("Find Student by Id")
    @Test
    void findById_ExistingId_ReturnsStudentDto() throws ExceptionService, ExceptionPersistence {
        int studentId = 1;
        String firstName = "Student";
        String lastName = "One";

        Student student = new Student();
        student.setId(studentId);
        student.setFirstName(firstName);
        student.setLastName(lastName);

        when(studentPersistence.findById(studentId)).thenReturn(Optional.of(student));

        StudentDto result = studentService.findById(studentId);

        assertNotNull(result);
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        verify(studentPersistence, times(1)).findById(studentId);
    }

    @DisplayName("Find Student by Id Exception")
    @Test
    void findById_ExistingId_ExceptionThrown() throws ExceptionPersistence {
        int studentId = 1;

        when(studentPersistence.findById(studentId)).thenThrow(new ExceptionPersistence(DB_ERROR));

        assertThrows(ExceptionService.class, () -> studentService.findById(studentId));
        verify(studentPersistence, times(1)).findById(studentId);
    }

    @DisplayName("Find All Students")
    @Test
    void findAll_ReturnsAllStudents() throws ExceptionService, ExceptionPersistence {
        Student student1 = new Student();
        student1.setId(1);
        student1.setFirstName("John");
        student1.setLastName("Doe");

        Student student2 = new Student();
        student2.setId(2);
        student2.setFirstName("Jane");
        student2.setLastName("Smith");

        Collection<Student> students = Arrays.asList(student1, student2);

        when(studentPersistence.findAll()).thenReturn(students);

        Collection<StudentDto> result = studentService.findAll();

        assertNotNull(result);
        assertEquals(students.size(), result.size());
        verify(studentPersistence, times(1)).findAll();
    }

    @DisplayName("Find All Students Exception")
    @Test
    void findAll_ExceptionThrown() throws ExceptionPersistence {
        when(studentPersistence.findAll()).thenThrow(new ExceptionPersistence(DB_ERROR));

        assertThrows(ExceptionService.class, () -> studentService.findAll());
        verify(studentPersistence, times(1)).findAll();
    }

    @DisplayName("Delete Student by Id")
    @Test
    void deleteById_ExistingStudent_ReturnsTrue() throws ExceptionService, ExceptionPersistence {
        int studentId = 1;
        Student student = new Student();
        student.setId(studentId);
        student.setFirstName("Student");
        student.setLastName("One");

        when(studentPersistence.findById(studentId)).thenReturn(Optional.of(student));

        boolean result = studentService.deleteById(studentId);

        assertTrue(result);
        verify(studentPersistence, times(1)).delete(studentId);
        verify(studentPersistence, times(1)).findById(studentId);
    }

    @DisplayName("Delete Student by Id - Nonexistent Student")
    @Test
    void deleteById_NonExistingStudent_ReturnsFalse() throws ExceptionService, ExceptionPersistence {
        int studentId = 1;

        when(studentPersistence.findById(studentId)).thenReturn(Optional.empty());

        boolean result = studentService.deleteById(studentId);

        assertFalse(result);
        verify(studentPersistence, never()).delete(studentId);
        verify(studentPersistence, times(1)).findById(studentId);
    }

    @DisplayName("Delete Student by Id Exception")
    @Test
    void deleteById_ExceptionThrown() throws ExceptionPersistence {
        int studentId = 1;

        when(studentPersistence.findById(studentId)).thenThrow(new RuntimeException(DB_ERROR));

        assertThrows(ExceptionService.class, () -> studentService.deleteById(studentId));
        verify(studentPersistence, times(1)).findById(studentId);
    }

    @DisplayName("Delete All Students")
    @Test
    void deleteAll_ReturnsNothingAndDeleteAllStudents() throws ExceptionService, ExceptionPersistence {
        doNothing().when(studentPersistence).deleteAll();
        studentService.deleteAll();
        verify(studentPersistence, times(1)).deleteAll();
    }

    @DisplayName("Delete All Students Exception")
    @Test
    void deleteAll_ExceptionThrown() throws ExceptionPersistence {
        doThrow(new ExceptionPersistence(DB_ERROR)).when(studentPersistence).deleteAll();
        assertThrows(ExceptionService.class, () -> studentService.deleteAll());
    }

    @DisplayName("Update Student")
    @Test
    void update_ExistingStudent_UpdateFields() throws ExceptionService, ExceptionPersistence {
        int studentId = 1;

        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("Elon");
        studentDto.setLastName("Musk");
        studentDto.setEmail("em@ar.com");
        studentDto.setPassword("!Test1Echo");

        Student student = new Student();
        student.setId(studentId);
        student.setFirstName("Student");
        student.setLastName("One");
        student.setEmail("so@ar.com");
        student.setPassword("password");

        when(studentPersistence.findById(studentId)).thenReturn(Optional.of(student));

        studentService.update(studentDto, studentId);

        verify(studentPersistence, times(1)).findById(studentId);
        assertEquals(studentDto.getFirstName(), student.getFirstName());
        assertEquals(studentDto.getLastName(), student.getLastName());
        assertEquals(studentDto.getEmail(), student.getEmail());
        assertEquals(studentDto.getPassword(), student.getPassword());
    }

    @DisplayName("Update Student Exception")
    @Test
    void update_ExceptionThrown() throws ExceptionPersistence {
        int studentId = 1;
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("Student");
        studentDto.setLastName("One");

        when(studentPersistence.findById(studentId)).thenThrow(new RuntimeException(DB_ERROR));

        assertThrows(ExceptionService.class, () -> studentService.update(studentDto, studentId));
        verify(studentPersistence, times(1)).findById(studentId);
    }

    @DisplayName("Register Student into Course")
    @Test
    void registerIntoCourse_StudentAndCourseExist_RegisterStudentInCourse() throws ExceptionService, ExceptionPersistence {
        int studentId = 1;
        int courseId = 1;

        Student student = new Student();
        student.setId(studentId);
        student.setFirstName("Student");
        student.setLastName("One");
        student.setCourse(null);

        Course course = new Course();
        course.setId(courseId);
        course.setCourseName("Computer Science");
        course.setNumberOfStudents(5);

        when(studentPersistence.findById(studentId)).thenReturn(Optional.of(student));
        when(coursePersistence.findById(courseId)).thenReturn(course);

        studentService.registerIntoCourse(studentId, courseId);

        verify(studentPersistence, times(1)).findById(studentId);
        verify(coursePersistence, times(1)).findById(courseId);
        assertNotNull(student.getCourse());
        assertEquals(course, student.getCourse());
        assertEquals(6, course.getNumberOfStudents());
    }

    @DisplayName("Register Student into Course - Student Already in Course")
    @Test
    void registerIntoCourse_StudentAlreadyInCourse_ExceptionThrown() throws ExceptionService, ExceptionPersistence {
        int studentId = 1;
        int courseId = 1;

        Course studentCourse = new Course();
        studentCourse.setId(2);
        studentCourse.setCourseName("Psychology");

        Student student = new Student();
        student.setId(studentId);
        student.setFirstName("Student");
        student.setLastName("One");
        student.setCourse(studentCourse);

        Course course = new Course();
        course.setId(courseId);
        course.setCourseName("Computer Science");
        course.setNumberOfStudents(5);

        when(studentPersistence.findById(studentId)).thenReturn(Optional.of(student));
        when(coursePersistence.findById(courseId)).thenReturn(course);

        ExceptionService exception = assertThrows(ExceptionService.class, () -> studentService.registerIntoCourse(studentId, courseId));
        assertEquals("The student is already register in a Course", exception.getMessage());
        verify(studentPersistence, times(1)).findById(studentId);
        verify(coursePersistence, times(1)).findById(courseId);
    }

    @DisplayName("Register Student into Course Exception")
    @Test
    void registerIntoCourse_StudentAndCourseExist_ExceptionThrown() throws ExceptionService, ExceptionPersistence {
        int studentId = 1;
        int courseId = 1;

        Student student = new Student();
        student.setId(studentId);
        student.setFirstName("Student");
        student.setLastName("One");
        student.setCourse(null);

        Course course = new Course();
        course.setId(courseId);
        course.setCourseName("Computer Science");
        course.setNumberOfStudents(5);

        when(studentPersistence.findById(studentId)).thenReturn(Optional.of(student));
        when(coursePersistence.findById(courseId)).thenReturn(course);
        doThrow(new ExceptionPersistence(DB_ERROR)).when(studentPersistence).create(student);

        assertThrows(ExceptionService.class, () -> studentService.registerIntoCourse(studentId, courseId));
        verify(studentPersistence, times(1)).findById(studentId);
        verify(coursePersistence, times(1)).findById(courseId);
    }

    @DisplayName("Register Student into Subject")
    @Test
    void registerIntoSubject_StudentAndSubjectExist_RegisterStudentInSubject() throws ExceptionService, ExceptionPersistence {
        int studentId = 1;
        int subjectId = 1;
        int courseId = 1;

        Course course = new Course();
        course.setId(courseId);
        course.setCourseName("Computer Science");

        HashSet<Course> courses = new HashSet<>();
        courses.add(course);

        Student student = new Student();
        student.setId(studentId);
        student.setFirstName("Student");
        student.setLastName("One");
        student.setCourse(course);
        student.setSubjects(new HashSet<Subject>());

        Subject subject = new Subject();
        subject.setSubjectName("Math");
        subject.setCourses(courses);

        when(studentPersistence.findById(studentId)).thenReturn(Optional.of(student));
        when(subjectPersistence.findById(subjectId)).thenReturn(subject);

        studentService.registerIntoSubject(studentId, subjectId);

        verify(studentPersistence, times(1)).findById(studentId);
        verify(subjectPersistence, times(1)).findById(subjectId);
        assertNotNull(student.getSubjects());
        assertTrue(student.getSubjects().contains(subject));
    }

    @DisplayName("Register Student into Subject - Subject Not in Course")
    @Test
    void registerIntoSubject_SubjectNotInStudentCourse_ExceptionThrown() throws ExceptionService, ExceptionPersistence {
        int studentId = 1;
        int subjectId = 1;
        int courseId = 1;

        Course course = new Course();
        course.setId(courseId);
        course.setCourseName("Computer Science");

        HashSet<Course> courses = new HashSet<>();
        courses.add(course);

        Student student = new Student();
        student.setId(studentId);
        student.setFirstName("Student");
        student.setLastName("One");
        student.setCourse(course);
        student.setSubjects(null);

        Subject subject = new Subject();
        subject.setSubjectName("Math");
        subject.setCourses(new HashSet<>());

        when(studentPersistence.findById(studentId)).thenReturn(Optional.of(student));
        when(subjectPersistence.findById(subjectId)).thenReturn(subject);

        ExceptionService exception = assertThrows(ExceptionService.class, () -> studentService.registerIntoSubject(studentId, subjectId));
        assertEquals("The subject you tried to register into, does not belong to your Course", exception.getMessage());
        verify(studentPersistence, times(1)).findById(studentId);
        verify(subjectPersistence, times(1)).findById(subjectId);
    }

    @DisplayName("Register Student in Subject Exception")
    @Test
    void registerInSubject_ExceptionThrown() throws ExceptionService, ExceptionPersistence {
        int studentId = 1;
        int subjectId = 1;
        int courseId = 1;

        Course course = new Course();
        course.setId(courseId);
        course.setCourseName("Computer Science");

        HashSet<Course> courses = new HashSet<>();
        courses.add(course);

        Student student = new Student();
        student.setId(studentId);
        student.setFirstName("Student");
        student.setLastName("One");
        student.setCourse(course);
        student.setSubjects(null);

        Subject subject = new Subject();
        subject.setSubjectName("Math");
        subject.setCourses(courses);

        when(studentPersistence.findById(studentId)).thenReturn(Optional.of(student));
        when(subjectPersistence.findById(subjectId)).thenReturn(subject);
        doThrow(new ExceptionPersistence(DB_ERROR)).when(studentPersistence).create(student);

        assertThrows(ExceptionService.class, () -> studentService.registerIntoSubject(studentId, subjectId));
        verify(studentPersistence, times(1)).findById(studentId);
        verify(subjectPersistence, times(1)).findById(subjectId);
    }

    @DisplayName("Find Student Course")
    @Test
    void findCourse_StudentExists_ReturnCourseName() throws ExceptionService, ExceptionPersistence {
        int studentId = 1;
        String courseName = "Computer Science";

        Course course = new Course();
        course.setCourseName(courseName);

        Student student = new Student();
        student.setId(studentId);
        student.setFirstName("Student");
        student.setLastName("One");
        student.setCourse(course);

        when(studentPersistence.findById(studentId)).thenReturn(Optional.of(student));

        String studentCourseName = studentService.findCourse(studentId);

        verify(studentPersistence, times(1)).findById(studentId);
        assertEquals(courseName, studentCourseName);
    }

    @DisplayName("Find Student Course Exception")
    @Test
    void findCourse_StudentNotFound_ThrowsExceptionService() throws ExceptionPersistence {
        int studentId = 1;

        when(studentPersistence.findById(studentId)).thenThrow(new ExceptionPersistence(DB_ERROR));

        assertThrows(ExceptionService.class, () -> studentService.findCourse(studentId));
        verify(studentPersistence, times(1)).findById(studentId);
    }

    @DisplayName("Find Student Subjects")
    @Test
    void findAllSubjects_StudentExists_ReturnSubjectNames() throws ExceptionService, ExceptionPersistence {
        int studentId = 1;
        Set<String> expectedSubjectNames = new HashSet<>(Arrays.asList("Math", "Science", "English"));

        Student existingStudent = new Student();
        existingStudent.setId(studentId);
        existingStudent.setFirstName("John");
        existingStudent.setLastName("Doe");

        Subject mathSubject = new Subject();
        mathSubject.setSubjectName("Math");
        existingStudent.getSubjects().add(mathSubject);

        Subject scienceSubject = new Subject();
        scienceSubject.setSubjectName("Science");
        existingStudent.getSubjects().add(scienceSubject);

        Subject englishSubject = new Subject();
        englishSubject.setSubjectName("English");
        existingStudent.getSubjects().add(englishSubject);

        when(studentPersistence.findById(studentId)).thenReturn(Optional.of(existingStudent));

        Collection<String> actualSubjectNames = studentService.findAllSubjects(studentId);

        verify(studentPersistence, times(1)).findById(studentId);
        assertEquals(expectedSubjectNames, actualSubjectNames);
    }

    @DisplayName("Find Student Subjects Exception")
    @Test
    void findAllSubjects_ExceptionThrown() throws ExceptionService, ExceptionPersistence {
        int studentId = 1;

        when(studentPersistence.findById(studentId)).thenThrow(new ExceptionPersistence(DB_ERROR));

        assertThrows(ExceptionService.class, () -> studentService.findAllSubjects(studentId));
        verify(studentPersistence, times(1)).findById(studentId);
    }
}
