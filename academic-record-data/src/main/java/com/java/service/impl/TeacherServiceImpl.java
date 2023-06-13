package com.java.service.impl;

import com.java.dto.GradeDto;
import com.java.dto.TeacherDto;
import com.java.mappers.GradeMapper;
import com.java.mappers.TeacherMapper;
import com.java.model.Grade;
import com.java.model.Student;
import com.java.model.Subject;
import com.java.model.Teacher;
import com.java.persistence.GradePersistence;
import com.java.persistence.StudentPersistence;
import com.java.persistence.SubjectPersistence;
import com.java.persistence.TeacherPersistence;
import com.java.service.ExceptionService;
import com.java.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherPersistence teacherPersistence;

    @Autowired
    private SubjectPersistence subjectPersistence;

    @Autowired
    private StudentPersistence studentPersistence;

    @Autowired
    private GradePersistence gradePersistence;

    @Override
    public Teacher save(TeacherDto teacherDto) throws ExceptionService {
        try {
            Teacher teacher = TeacherMapper.INSTANCE.dtoToTeacher(teacherDto);
            teacherPersistence.create(teacher);
            return teacher;
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Collection<TeacherDto> findAll() throws ExceptionService {
        try{
            Collection<Teacher> teachers = teacherPersistence.findAll();
            return  teachers.stream()
                    .map(TeacherMapper.INSTANCE::teacherToDto)
                    .collect(Collectors.toSet());
        }catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public TeacherDto findById(Integer id) throws ExceptionService {
        try{
            Optional<Teacher> teacher = Optional.ofNullable(teacherPersistence.findById(id));
            return TeacherMapper.INSTANCE.teacherToDto(teacher.get());
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void delete() throws ExceptionService {
        try {
            teacherPersistence.delete();
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) throws ExceptionService {
        try {
            teacherPersistence.deleteById(id);
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void update(TeacherDto teacherDto, Integer id) throws ExceptionService {
        try {
            Teacher teacher = teacherPersistence.findById(id);
            if (teacher != null){
                if (teacherDto.getFirstName() != null){
                    teacher.setFirstName(teacherDto.getFirstName());
                }
                if (teacherDto.getLastName() != null){
                    teacher.setLastName(teacherDto.getLastName());
                }
                if (teacherDto.getEmail() != null){
                    teacher.setEmail(teacherDto.getEmail());
                }
                if (teacherDto.getPassword() != null){
                teacher.setPassword(teacherDto.getPassword());
                }
                teacherPersistence.update(teacher);
            }
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public String findCourse(Integer id) throws ExceptionService {
        try {
            Teacher teacher = teacherPersistence.findById(id);
            return teacher.getCourse().getCourseName();
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Object findSubjects(Integer id) throws ExceptionService {
        try {
            Teacher teacher = teacherPersistence.findById(id);
            return teacher.getSubjects().stream()
                    .map(Subject::getSubjectName)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void addGrade(GradeDto gradeDto, Integer subjectId, Integer studentId) throws ExceptionService {
        try {
            Grade grade = GradeMapper.INSTANCE.dtoToGrade(gradeDto);
            Optional<Student> student = studentPersistence.findById(studentId);
            Optional<Subject> subject = Optional.ofNullable(subjectPersistence.findById(subjectId));

            if (student.isPresent() && subject.isPresent()) {
                grade.setStudent(student.get());
                grade.setSubject(subject.get());
                gradePersistence.create(grade);

                student.ifPresent(s -> s.getGrades().add(grade));
                subject.ifPresent(s -> s.getGrades().add(grade));
                studentPersistence.create(student.get());
                subjectPersistence.create(subject.get());
            }

        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }
}
