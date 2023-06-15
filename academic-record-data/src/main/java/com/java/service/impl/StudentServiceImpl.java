package com.java.service.impl;

import com.java.dto.StudentDto;
import com.java.mappers.StudentMapper;
import com.java.model.Course;
import com.java.model.Student;
import com.java.model.Subject;
import com.java.persistence.CoursePersistence;
import com.java.persistence.StudentPersistence;
import com.java.persistence.SubjectPersistence;
import com.java.service.ExceptionService;
import com.java.service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentPersistence studentPersistence;

    @Autowired
    private CoursePersistence coursePersistence;

    @Autowired
    private SubjectPersistence subjectPersistence;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void save(StudentDto studentDto) throws ExceptionService {
        try {
            Student student = StudentMapper.INSTANCE.dtoToStudent(studentDto);
            studentPersistence.create(student);
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public StudentDto findById(Integer id) throws ExceptionService {
        try {
            Optional<Student> student = studentPersistence.findById(id);
            return student.map(StudentMapper.INSTANCE::studentToDto).orElse(null);
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Collection<StudentDto> findAll() throws ExceptionService {
        try {
            Collection<Student> students = studentPersistence.findAll();
            return students.stream()
                    .map(StudentMapper.INSTANCE::studentToDto)
                    .collect(Collectors.toSet());
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public boolean deleteById(Integer id) throws ExceptionService {
        try {
            if (studentPersistence.findById(id).isPresent()) {
                studentPersistence.delete(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            throw new ExceptionService("Could not delete student with id: " + id);
        }
    }

    @Override
    public void deleteAll() throws ExceptionService {
        try {
            studentPersistence.deleteAll();
        } catch (Exception e){
            throw new ExceptionService("Could not delete all students");
        }
    }

    @Override
    public void update(StudentDto studentDto, Integer id) throws ExceptionService {
        try {
            Optional<Student> student = studentPersistence.findById(id);
            if (student.isPresent()) {
                if (studentDto.getFirstName() != null) {
                    student.get().setFirstName(studentDto.getFirstName());
                }
                if (studentDto.getLastName() != null) {
                    student.get().setLastName(studentDto.getLastName());
                }
                if (studentDto.getEmail() != null) {
                    student.get().setEmail(studentDto.getEmail());
                }
                if (studentDto.getPassword() != null) {
                    student.get().setPassword(studentDto.getPassword());
                }
            }
        } catch (Exception e) {
            throw new ExceptionService("Could not update");
        }
    }

    @Override
    public void registerIntoCourse(Integer studentId, Integer courseId) throws ExceptionService {
        try {
            Optional<Student> student = studentPersistence.findById(studentId);
            Optional<Course> course = Optional.ofNullable(coursePersistence.findById(courseId));
            if (student.isPresent() && course.isPresent()) {
                if (student.get().getCourse() == null){
                    student.get().setCourse(course.get());
                    course.get().setNumberOfStudents(course.get().getNumberOfStudents() + 1);
                    studentPersistence.create(student.get());
                    coursePersistence.create(course.get());
                } else {
                    throw new ExceptionService("The student is already register in a Course");
                }
            }
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void registerIntoSubject(Integer studentId, Integer subjectId) throws ExceptionService {
        try {
            Optional<Student> student = studentPersistence.findById(studentId);
            if (student.isPresent() && student.get().getCourse() != null) {
                Optional<Subject> subject = Optional.ofNullable(subjectPersistence.findById(subjectId));
                if (subject.isPresent() && subject.get().getCourses().contains(student.get().getCourse())){

                    Set<Subject> subjectsOfDay = student.get().getSubjects().stream()
                            .filter(s -> s.getDay() == subject.get().getDay())
                            .collect(Collectors.toSet());

                    if (!subjectsOfDay.isEmpty()){
                        String message = "The student has already a subject at that time and can't register the new subject.";
                        for (Subject studentSubject : subjectsOfDay) {
                            int start = subject.get().getStartTime().compareTo(studentSubject.getStartTime());
                            int end = subject.get().getEndTime().compareTo(studentSubject.getEndTime());
                            int startEnd = subject.get().getStartTime().compareTo(studentSubject.getEndTime());
                            int endStart = subject.get().getEndTime().compareTo(studentSubject.getStartTime());
                            if (start == 0 && end == 0){
                                throw new ExceptionService(message);
                            }
                            if (start > 0 && end < 0) {
                                throw new ExceptionService(message);
                            }
                            if (start < 0 && (endStart > 0 && end < 0)) {
                                throw new ExceptionService(message);
                            }
                            if (start > 0 && (startEnd < 0 && end > 0)) {
                                throw new ExceptionService(message);
                            }
                        }
                        Set<Subject> subjects = student.get().getSubjects();
                        subjects.add(subject.get());
                        Set<Student> students = subject.get().getStudents();
                        students.add(student.get());

                        student.get().setSubjects(subjects);
                        subject.get().setStudents(students);

                        studentPersistence.create(student.get());
                        subjectPersistence.create(subject.get());
                    } else {
                        Set<Subject> subjects = student.get().getSubjects();
                        subjects.add(subject.get());
                        Set<Student> students = subject.get().getStudents();
                        students.add(student.get());

                        student.get().setSubjects(subjects);
                        subject.get().setStudents(students);

                        studentPersistence.create(student.get());
                        subjectPersistence.create(subject.get());
                    }

                } else {
                    throw new ExceptionService("The subject you tried to register into, does not belong to your Course");
                }
            }
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public String findCourse(Integer id) throws ExceptionService {
        try {
            Optional<Student> student = studentPersistence.findById(id);
            return student.get().getCourse().getCourseName();
        } catch (Exception e){
            throw new ExceptionService("Could not find the student courses");
        }
    }

    @Override
    public Collection<String> findAllSubjects(Integer id) throws ExceptionService {
        try {
            Optional<Student> student = studentPersistence.findById(id);
            return  student.get().getSubjects().stream()
                    .map(Subject::getSubjectName)
                    .collect(Collectors.toSet());
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }
}
