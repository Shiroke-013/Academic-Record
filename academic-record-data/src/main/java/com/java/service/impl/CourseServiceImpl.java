package com.java.service.impl;

import com.java.dto.CourseDto;
import com.java.mappers.CourseMapper;
import com.java.model.Course;
import com.java.model.Subject;
import com.java.model.Teacher;
import com.java.persistence.CoursePersistence;
import com.java.persistence.SubjectPersistence;
import com.java.persistence.TeacherPersistence;
import com.java.service.CourseService;
import com.java.service.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CoursePersistence coursePersistence;

    @Autowired
    private TeacherPersistence teacherPersistence;

    @Autowired
    private SubjectPersistence subjectPersistence;

    @Override
    public void save(CourseDto courseDto) throws ExceptionService {
        try {
            Course course = CourseMapper.INSTANCE.dtoToCourse(courseDto);
            coursePersistence.create(course);
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Object findById(Integer id) throws ExceptionService {
        try {
            Optional<Course> course = Optional.ofNullable(coursePersistence.findById(id));
            return CourseMapper.INSTANCE.courseToDto(course.get());
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Collection<CourseDto> findAll() throws ExceptionService {
        try {
            Collection<Course> courses = coursePersistence.findAll();
            Set<CourseDto> courseDtos = new HashSet<>();
            for (Course course : courses) {
                courseDtos.add(CourseMapper.INSTANCE.courseToDto(course));
            }
            return courseDtos;
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void deleteAll() throws ExceptionService {
        try {
            coursePersistence.deleteAll();
        } catch (Exception e){
            throw new ExceptionService("Could not delete all courses");
        }
    }

    @Override
    public void deleteById(Integer id) throws ExceptionService {
        try {
            coursePersistence.delete(id);
        } catch (Exception e){
            throw new ExceptionService("Could not delete student with id: " + id);
        }
    }

    @Override
    public boolean update(CourseDto courseDto, Integer id) throws ExceptionService {
        try {
            Course course = coursePersistence.findById(id);
            if (course != null) {
                if (courseDto.getCourseName() != null) {
                    course.setCourseName(courseDto.getCourseName());
                }
                if (courseDto.getNumberOfStudents() != null) {
                    course.setNumberOfStudents(courseDto.getNumberOfStudents());
                }
                if (courseDto.getCourseStart() != null) {
                    course.setCourseStart(courseDto.getCourseStart());
                }
                if (courseDto.getCourseEnd() != null) {
                    course.setCourseEnd(courseDto.getCourseEnd());
                }
                coursePersistence.create(course);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ExceptionService("Could not update");
        }
    }

    @Override
    public Collection<String> findTeachers(Integer id) throws ExceptionService {
        try {
            Optional<Course> course = Optional.ofNullable(coursePersistence.findById(id));
            return course.map(Course::getTeachers)
                    .orElse(Collections.emptySet())
                    .stream()
                    .map(teacher -> teacher.getFirstName() + " " + teacher.getLastName())
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Collection<String> findStudents(Integer id) throws ExceptionService {
        try {
            Optional<Course> course = Optional.ofNullable(coursePersistence.findById(id));
            return course.map(Course::getStudents)
                    .orElse(Collections.emptySet())
                    .stream()
                    .map(student -> student.getFirstName() + " " + student.getLastName())
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Collection<String> findSubjects(Integer id) throws ExceptionService {
        try {
            Optional<Course> course = Optional.ofNullable(coursePersistence.findById(id));
            return course.map(Course::getSubjects)
                    .orElse(Collections.emptySet())
                    .stream()
                    .map(subject -> subject.getSubjectName() + ", capacidad: " + subject.getCapacity().toString())
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public boolean deleteTeacherFromCourse(Integer courseId, Integer teacherId) throws ExceptionService {
        try {
            Optional<Course> course = Optional.ofNullable(coursePersistence.findById(courseId));
            if (course.isPresent()){
                Optional<Teacher> teacher = Optional.ofNullable(teacherPersistence.findById(teacherId));
                if (teacher.isPresent() && teacher.get().getCourse().getCourseName().equals(course.get().getCourseName()) ){
                    Set<Teacher> teachers = course.map(Course::getTeachers).orElse(Collections.emptySet());
                    teachers.remove(teacher.get());
                    course.get().setTeachers(teachers);
                    coursePersistence.create(course.get());
                    teacher.get().setCourse(null);
                    teacherPersistence.create(teacher.get());
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void addTeacher(Integer courseId, Integer teacherId) throws ExceptionService {
        try {
            Optional<Teacher> teacher = Optional.ofNullable(teacherPersistence.findById(teacherId));
            Optional<Course> course = Optional.ofNullable(coursePersistence.findById(courseId));
            if (teacher.isPresent() && course.isPresent()){
                if (teacher.get().getCourse() == null) {
                    Set<Teacher> teachers = course.get().getTeachers();
                    teachers.add(teacher.get());
                    teacher.get().setCourse(course.get());

                    coursePersistence.create(course.get());
                    teacherPersistence.create(teacher.get());
                } else {
                    throw new ExceptionService("Teacher is already inside a course");
                }
            }
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void addSubject(Integer courseId, Integer subjectId) throws ExceptionService {
        try {
            Optional<Subject> subject = Optional.ofNullable(subjectPersistence.findById(subjectId));
            Optional<Course> course = Optional.ofNullable(coursePersistence.findById(courseId));
            subject.ifPresent(s -> course.ifPresent(c -> c.getSubjects().add(s)));
            coursePersistence.create(course.get());

        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }
}
