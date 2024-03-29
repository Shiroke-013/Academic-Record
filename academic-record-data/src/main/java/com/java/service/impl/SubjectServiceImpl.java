package com.java.service.impl;

import com.java.dto.SubjectDto;
import com.java.mappers.SubjectMapper;
import com.java.model.Subject;
import com.java.model.Teacher;
import com.java.persistence.SubjectPersistence;
import com.java.persistence.TeacherPersistence;
import com.java.service.ExceptionService;
import com.java.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectPersistence subjectPersistence;

    @Autowired
    private TeacherPersistence teacherPersistence;

    @Override
    public void save(SubjectDto subjectDto) throws ExceptionService {
        try {
            Subject subject = SubjectMapper.INSTANCE.dtoToSubject(subjectDto);
            subjectPersistence.create(subject);
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public SubjectDto findById(Integer id) throws ExceptionService {
        try {
            Optional<Subject> subject = Optional.ofNullable(subjectPersistence.findById(id));
            return SubjectMapper.INSTANCE.subjectToDto(subject.get());
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Collection<SubjectDto> findAll() throws ExceptionService {
        try {
            Collection<Subject> subjects = subjectPersistence.findAll();
            return subjects.stream()
                    .map(SubjectMapper.INSTANCE::subjectToDto)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void deleteAll() throws ExceptionService {
        try {
            subjectPersistence.deleteAll();
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) throws ExceptionService {
        try {
            subjectPersistence.delete(id);
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void update(SubjectDto subjectDto, Integer id) throws ExceptionService {
        try {
            Subject subject = subjectPersistence.findById(id);
            if (subjectDto.getSubjectName() != null) {
                subject.setSubjectName(subjectDto.getSubjectName());
            }
            if (subjectDto.getDuration() != null) {
                subject.setDuration(subjectDto.getDuration());
            }
            if (subjectDto.getCapacity() != null) {
                subject.setCapacity(subjectDto.getCapacity());
            }
            if (subjectDto.getStartDate() != null) {
                subject.setStartDate(subjectDto.getStartDate());
            }
            if (subjectDto.getEndDate() != null) {
                subject.setEndDate(subjectDto.getEndDate());
            }
            subjectPersistence.update(subject);
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void addTeacher(Integer subjectId, Integer teacherId) throws ExceptionService {
        try {
            Optional<Teacher> teacher = Optional.ofNullable(teacherPersistence.findById(teacherId));
            Optional<Subject> subject = Optional.ofNullable(subjectPersistence.findById(subjectId));
            if (subject.isPresent() && teacher.isPresent()){
                if (subject.get().getCourses().contains(teacher.get().getCourse())) {

                    Set<Subject> subjectsOfDay = teacher.get().getSubjects().stream()
                                    .filter(t -> t.getDay() == subject.get().getDay())
                                    .collect(Collectors.toSet());

                    if (!subjectsOfDay.isEmpty()) {
                        String message = "The teacher has already a subject at that time and can't be in charge of the new subject.";
                        for (Subject teacherSubject : subjectsOfDay) {
                            int start = subject.get().getStartTime().compareTo(teacherSubject.getStartTime());
                            int end = subject.get().getEndTime().compareTo(teacherSubject.getEndTime());
                            int startEnd = subject.get().getStartTime().compareTo(teacherSubject.getEndTime());
                            int endStart = subject.get().getEndTime().compareTo(teacherSubject.getStartTime());
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

                        subject.ifPresent(s -> s.setTeacher(teacher.get()));

                        subject.get().setTeacher(teacher.get());
                        teacher.ifPresent(t -> t.getSubjects().add(subject.get()));

                        subjectPersistence.create(subject.get());
                        teacherPersistence.create(teacher.get());
                    } else {
                        subject.ifPresent(s -> s.setTeacher(teacher.get()));

                        subject.get().setTeacher(teacher.get());
                        teacher.ifPresent(t -> t.getSubjects().add(subject.get()));

                        subjectPersistence.create(subject.get());
                        teacherPersistence.create(teacher.get());
                    }
                } else {
                    throw new ExceptionService("Teacher is not registered in the subject course.");
                }
            }
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Collection<String> findStudents(Integer id) throws ExceptionService {
        try {
            Optional<Subject> subject = Optional.ofNullable(subjectPersistence.findById(id));
            return subject.map(Subject::getStudents).
                    orElse(Collections.emptySet())
                    .stream()
                    .map(student -> student.getFirstName() + " " + student.getLastName())
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void deleteTeacherFromSubject(Integer subjectId, Integer teacherId) throws ExceptionService {
        try {
            Optional<Subject> subject = Optional.ofNullable(subjectPersistence.findById(subjectId));
            if (subject.isPresent()){
                Optional<Teacher> teacher = Optional.ofNullable(teacherPersistence.findById(teacherId));
                if (teacher.isPresent() && teacher.get().getSubjects().contains(subject.get()) ){
                    Set<Subject> subjects = teacher.map(Teacher::getSubjects).orElse(Collections.emptySet());
                    subjects.remove(subject.get());
                    teacher.get().setSubjects(subjects);
                    teacherPersistence.create(teacher.get());
                    subject.get().setTeacher(null);
                    subjectPersistence.create(subject.get());
                } else {
                    throw new ExceptionService("The teacher is not registered in that subject");
                }
            }
        } catch (Exception e) {
            throw new ExceptionService(e.getMessage());
        }
    }
}
