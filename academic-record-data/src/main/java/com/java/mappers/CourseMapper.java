package com.java.mappers;

import com.java.dto.CourseDto;
import com.java.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(target = "studentsNames", source = "students")
    @Mapping(target = "subjectsNames", source = "subjects")
    @Mapping(target = "teachersNames", source = "teachers")
    CourseDto courseToDto(Course student);

    default Set<String> mapStudentsToStudentNames(Set<Student> students) {
        return students.stream()
                .map(student -> student.getId() + ": "
                        + student.getFirstName() + " " + student.getLastName())
                .collect(Collectors.toSet());
    }

    default Set<String> mapSubjectsToSubjectsNames(Set<Subject> subjects) {
        return subjects.stream()
                .map(subject -> subject.getId() + " " + subject.getSubjectName())
                .collect(Collectors.toSet());
    }

    default Set<String> mapCoursesToCoursesNames(Set<Teacher> teachers) {
        return teachers.stream()
                .map(teacher -> teacher.getId() + " "
                        + teacher.getFirstName() + " " + teacher.getLastName())
                .collect(Collectors.toSet());
    }


    Course dtoToCourse(CourseDto studentDto);

}
