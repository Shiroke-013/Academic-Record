package com.java.service;

import com.java.dto.CourseDto;
import com.java.model.Course;
import com.java.model.Student;
import com.java.model.Subject;
import com.java.model.Teacher;
import com.java.persistence.CoursePersistence;
import com.java.persistence.ExceptionPersistence;
import com.java.persistence.SubjectPersistence;
import com.java.persistence.TeacherPersistence;
import com.java.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

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

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class CourseServiceTest {

    private static final String DB_ERROR = "Database connection error";

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CoursePersistence coursePersistence;

    @Mock
    private TeacherPersistence teacherPersistence;

    @Mock
    private SubjectPersistence subjectPersistence;

    @DisplayName("Save Course")
    @Test
    void save_ValidCourseDto_CreatesCourse() throws ExceptionPersistence, ExceptionService {
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");
        courseDto.setNumberOfStudents(0);
        courseDto.setCourseStart(LocalDate.parse("2023-01-17"));
        courseDto.setCourseEnd(LocalDate.parse("2023-05-29"));

        doNothing().when(coursePersistence).create(any(Course.class));

        courseService.save(courseDto);

        verify(coursePersistence, times(1)).create(any(Course.class));
    }


    @DisplayName("Save Course Exception")
    @Test
    void save_ExceptionThrown() throws ExceptionPersistence {
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");
        courseDto.setNumberOfStudents(0);
        courseDto.setCourseStart(LocalDate.parse("2023-01-17"));
        courseDto.setCourseEnd(LocalDate.parse("2023-05-29"));

        doThrow(new ExceptionPersistence(DB_ERROR)).when(coursePersistence).create(any(Course.class));

        assertThrows(ExceptionService.class, () -> courseService.save(courseDto));
        verify(coursePersistence, times(1)).create(any(Course.class));
    }


    @DisplayName("Find Course By Id")
    @Test
    void findById_ExistingId_ReturnsCourseDto() throws ExceptionPersistence, ExceptionService {
        Integer id = 1;
        Course course = new Course();
        course.setId(id);
        course.setCourseName("Computer Science");
        course.setNumberOfStudents(50);
        course.setCourseStart(LocalDate.parse("2023-01-17"));
        course.setCourseEnd(LocalDate.parse("2023-05-29"));
        course.setTeachers(new HashSet<>());
        course.setStudents(new HashSet<>());
        course.setSubjects(new HashSet<>());

        when(coursePersistence.findById(id)).thenReturn(course);

        CourseDto result = (CourseDto) courseService.findById(id);

        assertEquals("Computer Science", result.getCourseName());
        assertEquals(50, result.getNumberOfStudents());
        verify(coursePersistence, times(1)).findById(id);
    }


    @DisplayName("Find Course By Id Exception")
    @Test
    void findById_ThrowsExceptionService() throws ExceptionPersistence {
        Integer id = 1;
        when(coursePersistence.findById(id)).thenThrow(new ExceptionPersistence(DB_ERROR));

        assertThrows(ExceptionService.class, () -> courseService.findById(id));
        verify(coursePersistence, times(1)).findById(id);
    }

    @DisplayName("Find All Courses")
    @Test
    void findAll_ReturnsListOfGradeDtos() throws ExceptionService, ExceptionPersistence {
        Course course = new Course();
        course.setCourseName("Computer Science");
        course.setNumberOfStudents(0);
        course.setCourseStart(LocalDate.parse("2023-01-17"));
        course.setCourseEnd(LocalDate.parse("2023-05-29"));
        course.setTeachers(new HashSet<>());
        course.setStudents(new HashSet<>());
        course.setSubjects(new HashSet<>());
        Course course2 = new Course();
        course2.setCourseName("Chemistry");
        course2.setNumberOfStudents(0);
        course2.setCourseStart(LocalDate.parse("2023-01-13"));
        course2.setCourseEnd(LocalDate.parse("2023-05-31"));
        course2.setTeachers(new HashSet<>());
        course2.setStudents(new HashSet<>());
        course2.setSubjects(new HashSet<>());
        HashSet<Course> courses = new HashSet<>();
        courses.add(course);
        courses.add(course2);

        when(coursePersistence.findAll()).thenReturn(courses);

        Collection<CourseDto> result = courseService.findAll();

        assertEquals(2, result.size());
        verify(coursePersistence, times(1)).findAll();
    }

    @DisplayName("Find All Courses Exception")
    @Test
    void findAll_ExceptionThrown() throws ExceptionPersistence {
        when(coursePersistence.findAll()).thenThrow(new ExceptionPersistence(DB_ERROR));

        assertThrows(ExceptionService.class, () -> courseService.findAll());
        verify(coursePersistence, times(1)).findAll();
    }

    @DisplayName("Delete All Courses")
    @Test
    void deleteAll_DeleteAllCourses() throws ExceptionPersistence, ExceptionService {
        doNothing().when(coursePersistence).deleteAll();
        courseService.deleteAll();
        verify(coursePersistence, times(1)).deleteAll();
    }

    @DisplayName("Delete All Courses Exception")
    @Test
    void deleteAll_ExceptionThrown() throws ExceptionPersistence {
        doThrow(new ExceptionPersistence(DB_ERROR)).when(coursePersistence).deleteAll();
        assertThrows(ExceptionService.class, () -> courseService.deleteAll());
    }

    @DisplayName("Delete Course by Id")
    @Test
    void deleteById_ExistingID_DeletesCourse() throws ExceptionPersistence, ExceptionService {
        Integer courseId = 1;
        Course course = new Course();
        course.setId(1);
        course.setCourseName("Computer Science");
        course.setNumberOfStudents(0);
        course.setCourseStart(LocalDate.parse("2023-01-17"));
        course.setCourseEnd(LocalDate.parse("2023-05-29"));
        course.setTeachers(new HashSet<>());
        course.setStudents(new HashSet<>());
        course.setSubjects(new HashSet<>());

        doNothing().when(coursePersistence).delete(courseId);

        courseService.deleteById(courseId);

        verify(coursePersistence, times(1)).delete(courseId);
    }

    @DisplayName("Delete Course by Id Exception")
    @Test
    void deleteById_ExceptionThrown() throws ExceptionPersistence {
        Integer courseId = 1;
        doThrow(new ExceptionPersistence(DB_ERROR)).when(coursePersistence).delete(courseId);
        assertThrows(ExceptionService.class, () -> courseService.deleteById(courseId));
    }

    @DisplayName("Upgrade Course")
    @Test
    void update_ValidDtoAndId_UpdatesCourse() throws ExceptionPersistence, ExceptionService {
        Integer id = 1;
        CourseDto courseDto = new CourseDto();
        courseDto.setNumberOfStudents(29);
        Course course = new Course();
        course.setId(id);

        when(coursePersistence.findById(id)).thenReturn(course);
        doNothing().when(coursePersistence).create(course);

        courseService.update(courseDto, id);

        verify(coursePersistence, times(1)).findById(id);
        verify(coursePersistence, times(1)).create(course);
        assertEquals(courseDto.getNumberOfStudents(), course.getNumberOfStudents());
    }

    @DisplayName("Upgrade Course Exception")
    @Test
    void update_ExceptionThrown() throws ExceptionPersistence {
        Integer id = 1;
        CourseDto courseDto = new CourseDto();
        courseDto.setNumberOfStudents(29);
        Course course = new Course();
        course.setId(id);

        when(coursePersistence.findById(id)).thenReturn(course);
        doThrow(new ExceptionPersistence(DB_ERROR)).when(coursePersistence).create(course);

        assertThrows(ExceptionService.class, () -> courseService.update(courseDto, id));
        verify(coursePersistence, times(1)).findById(id);
        verify(coursePersistence, times(1)).create(course);
    }

    @DisplayName("Find Course Teachers")
    @Test
    void findTeachers_ReturnsTeachersName() throws ExceptionService, ExceptionPersistence {
        Integer id = 1;
        Course course = new Course();
        course.setId(id);
        Teacher teacher = new Teacher();
        teacher.setFirstName("Teacher");
        teacher.setLastName("One");
        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("Teacher");
        teacher2.setLastName("Two");
        HashSet<Teacher> teachers = new HashSet<>();
        teachers.add(teacher);
        teachers.add(teacher2);
        course.setTeachers(teachers);

        when(coursePersistence.findById(id)).thenReturn(course);
        Object result = courseService.findTeachers(id);

        verify(coursePersistence, times(1)).findById(id);
        assertNotNull(result);
        assertTrue(result instanceof Set);
        Set<String> teacherNames = (Set<String>) result;
        assertEquals(2, teacherNames.size());
        assertTrue(teacherNames.contains("Teacher One"));
        assertTrue(teacherNames.contains("Teacher Two"));
    }

    @DisplayName("Find Course Teachers Exception")
    @Test
    void findTeachers_ExceptionThrown() throws ExceptionPersistence {
        Integer id = 1;
        when(coursePersistence.findById(id)).thenThrow(new ExceptionPersistence(DB_ERROR));

        assertThrows(ExceptionService.class, () -> courseService.findSubjects(id));
        verify(coursePersistence, times(1)).findById(id);
    }

    @DisplayName("Find Course Students")
    @Test
    void findStudents_ReturnsStudentsName() throws ExceptionService, ExceptionPersistence {
        Integer id = 1;
        Course course = new Course();
        course.setId(id);
        Student student = new Student();
        student.setFirstName("Student");
        student.setLastName("One");
        Student student2 = new Student();
        student2.setFirstName("Student");
        student2.setLastName("Two");
        HashSet<Student> students = new HashSet<>();
        students.add(student);
        students.add(student2);
        course.setStudents(students);

        when(coursePersistence.findById(id)).thenReturn(course);
        Object result = courseService.findStudents(id);

        verify(coursePersistence, times(1)).findById(id);
        assertNotNull(result);
        assertTrue(result instanceof Set);
        Set<String> studentNames = (Set<String>) result;
        assertEquals(2, studentNames.size());
        assertTrue(studentNames.contains("Student One"));
        assertTrue(studentNames.contains("Student Two"));
    }

    @DisplayName("Find Course Students Exception")
    @Test
    void findStudents_ExceptionThrown() throws ExceptionPersistence {
        Integer id = 1;
        when(coursePersistence.findById(id)).thenThrow(new ExceptionPersistence(DB_ERROR));

        assertThrows(ExceptionService.class, () -> courseService.findSubjects(id));
        verify(coursePersistence, times(1)).findById(id);
    }

    @DisplayName("Find Course Subjects")
    @Test
    void findSubjects_ReturnsSubjectsNameWithCapacity() throws ExceptionService, ExceptionPersistence {
        Integer id = 1;
        Course course = new Course();
        course.setId(id);
        Subject subject = new Subject();
        subject.setSubjectName("Math");
        subject.setCapacity(20);
        Subject subject2 = new Subject();
        subject2.setSubjectName("Science");
        subject2.setCapacity(30);
        HashSet<Subject> subjects = new HashSet<>();
        subjects.add(subject);
        subjects.add(subject2);
        course.setSubjects(subjects);

        when(coursePersistence.findById(id)).thenReturn(course);
        Object result = courseService.findSubjects(id);

        verify(coursePersistence, times(1)).findById(id);
        assertNotNull(result);
        assertTrue(result instanceof Set);
        Set<String> subjectsNames = (Set<String>) result;
        assertEquals(2, subjectsNames.size());
        assertTrue(subjectsNames.contains("Math, capacity: "+20));
        assertTrue(subjectsNames.contains("Science, capacity: "+30));
    }

    @DisplayName("Find Course Subjects Exception")
    @Test
    void findSubjects_ExceptionThrown() throws ExceptionPersistence {
        Integer id = 1;
        when(coursePersistence.findById(id)).thenThrow(new ExceptionPersistence(DB_ERROR));

        assertThrows(ExceptionService.class, () -> courseService.findSubjects(id));
        verify(coursePersistence, times(1)).findById(id);
    }

    @DisplayName("Delete Teacher From Course")
    @Test
    void deleteTeacherFromCourse_AllValid_DeletesTeacher() throws ExceptionPersistence, ExceptionService {
        Integer courseId = 1;
        Integer teacherId = 1;
        Course course = new Course();
        course.setId(courseId);
        course.setCourseName("Computer Science");

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setCourse(course);

        when(coursePersistence.findById(courseId)).thenReturn(course);
        when(teacherPersistence.findById(teacherId)).thenReturn(teacher);
        doNothing().when(coursePersistence).create(course);
        doNothing().when(teacherPersistence).create(teacher);

        boolean result = courseService.deleteTeacherFromCourse(courseId, teacherId);

        assertTrue(result);
        assertNull(teacher.getCourse());
        verify(coursePersistence, times(1)).create(course);
        verify(teacherPersistence, times(1)).create(teacher);
    }

    @DisplayName("Delete Teacher From Course - No Course")
    @Test
    void deleteTeacherFromCourse_InvalidCourseId_ReturnsFalse() throws ExceptionService, ExceptionPersistence {
        Integer courseId = 1;
        Integer teacherId = 1;

        when(coursePersistence.findById(courseId)).thenReturn(null);

        boolean result = courseService.deleteTeacherFromCourse(courseId, teacherId);

        assertFalse(result);
        verify(teacherPersistence, never()).findById(anyInt());
        verify(coursePersistence, never()).create(any(Course.class));
        verify(teacherPersistence, never()).create(any(Teacher.class));
    }

    @DisplayName("Delete Teacher From Course - No Teacher")
    @Test
    void deleteTeacherFromCourse_InvalidTeacherId_ReturnsFalse() throws ExceptionService, ExceptionPersistence {
        Integer courseId = 1;
        Integer teacherId = 1;
        Course course = new Course();
        course.setId(courseId);
        course.setCourseName("Computer Science");

        when(coursePersistence.findById(courseId)).thenReturn(course);
        when(teacherPersistence.findById(teacherId)).thenReturn(null);

        boolean result = courseService.deleteTeacherFromCourse(courseId, teacherId);

        assertFalse(result);
        verify(teacherPersistence, times(1)).findById(teacherId);
        verify(coursePersistence, never()).create(any(Course.class));
        verify(teacherPersistence, never()).create(any(Teacher.class));
    }

    @DisplayName("Delete Teacher From Course Exception")
    @Test
    void deleteTeacherFromCourse_TeacherCourseMismatch_ReturnsFalse() throws ExceptionPersistence {
        Integer courseId = 1;
        Integer teacherId = 1;
        Course course = new Course();
        course.setId(courseId);
        course.setCourseName("Computer Science");

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setCourse(course);

        when(coursePersistence.findById(courseId)).thenReturn(course);
        when(teacherPersistence.findById(teacherId)).thenReturn(teacher);
        doThrow(new ExceptionPersistence(DB_ERROR)).when(coursePersistence).create(course);
        doNothing().when(teacherPersistence).create(teacher);

        assertThrows(ExceptionService.class, () -> courseService.deleteTeacherFromCourse(courseId, teacherId));
        verify(coursePersistence, times(1)).findById(courseId);
        verify(teacherPersistence, times(1)).findById(teacherId);
    }

    @DisplayName("Add Teacher")
    @Test
    void addTeacher_ValidCourseAndTeacher_AddsTeacherToCourse() throws ExceptionService, ExceptionPersistence {
        Integer courseId = 1;
        Integer teacherId = 1;
        Course course = new Course();
        course.setId(courseId);
        course.setCourseName("Computer Science");
        course.setTeachers(new HashSet<>());
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);

        when(coursePersistence.findById(courseId)).thenReturn(course);
        when(teacherPersistence.findById(teacherId)).thenReturn(teacher);
        doNothing().when(coursePersistence).create(course);
        doNothing().when(teacherPersistence).create(teacher);

        courseService.addTeacher(courseId, teacherId);

        assertTrue(course.getTeachers().contains(teacher));
        assertEquals(course, teacher.getCourse());
        verify(coursePersistence, times(1)).create(course);
        verify(teacherPersistence, times(1)).create(teacher);
    }

    @DisplayName("Add Teacher - Course not Found")
    @Test
    void addTeacher_CourseAndTeacherNotFound() throws ExceptionPersistence {
        Integer courseId = 1;
        Integer teacherId = 1;
        Course course = new Course();
        course.setId(courseId);
        course.setCourseName("Computer Science");
        course.setTeachers(new HashSet<>());
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setCourse(course);

        when(coursePersistence.findById(courseId)).thenReturn(course);
        when(teacherPersistence.findById(teacherId)).thenReturn(teacher);

        assertThrows(ExceptionService.class, () -> courseService.addTeacher(courseId, teacherId));
        verify(coursePersistence, times(0)).create(any(Course.class));
        verify(teacherPersistence, times(0)).create(any(Teacher.class));
    }

    @DisplayName("Add Teacher Exception")
    @Test
    void addTeacher_ExceptionThrownn() throws ExceptionPersistence {
        Integer courseId = 1;
        Integer teacherId = 1;
        Course course = new Course();
        course.setId(courseId);
        course.setCourseName("Computer Science");
        course.setTeachers(new HashSet<>());
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setCourse(course);
        when(coursePersistence.findById(courseId)).thenReturn(course);
        when(teacherPersistence.findById(teacherId)).thenReturn(teacher);

        assertThrows(ExceptionService.class, () -> courseService.addTeacher(courseId, teacherId));
        verify(coursePersistence, times(0)).create(any(Course.class));
        verify(teacherPersistence, times(0)).create(any(Teacher.class));
    }

    @DisplayName("Add Subject")
    @Test
    void addSubject_ValidCourseAndSubject_AddsSubjectToCourse() throws ExceptionService, ExceptionPersistence {
        Integer courseId = 1;
        Integer subjectId = 1;
        Course course = new Course();
        course.setId(courseId);
        course.setCourseName("Computer Science");
        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setSubjectName("Math");

        when(coursePersistence.findById(courseId)).thenReturn(course);
        when(subjectPersistence.findById(subjectId)).thenReturn(subject);
        doNothing().when(coursePersistence).create(course);

        courseService.addSubject(courseId, subjectId);

        assertTrue(course.getSubjects().contains(subject));
        verify(coursePersistence, times(1)).create(course);
    }

    @DisplayName("Add Subject - Subject not Found")
    @Test
    void addSubject_InvalidCourse_ThrowsException() throws ExceptionPersistence {
        Integer courseId = 1;
        Integer subjectId = 1;
        when(coursePersistence.findById(courseId)).thenReturn(null);
        Subject subject = new Subject();
        subject.setId(subjectId);

        assertThrows(ExceptionService.class, () -> courseService.addSubject(courseId, subjectId));
        verify(coursePersistence, times(0)).create(any(Course.class));
    }

    @DisplayName("Add Subject Exception")
    @Test
    void addSubject_InvalidSubject_ThrowsException() throws ExceptionPersistence {
        Integer courseId = 1;
        Integer subjectId = 1;
        Course course = new Course();
        course.setId(courseId);
        course.setCourseName("Computer Science");
        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setSubjectName("Math");

        when(coursePersistence.findById(courseId)).thenReturn(course);
        when(subjectPersistence.findById(subjectId)).thenReturn(subject);
        doThrow(new ExceptionPersistence(DB_ERROR)).when(coursePersistence).create(course);

        assertThrows(ExceptionService.class, () -> courseService.addSubject(courseId, subjectId));
    }
}
