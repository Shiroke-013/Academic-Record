package com.java.mappers;

import com.java.dto.StudentDto;
import com.java.model.Course;
import com.java.model.Grade;
import com.java.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(target = "allGrades", source = "grades")
    @Mapping(target = "courseName", source = "course")
    StudentDto studentToDto(Student student);

    default Set<String> mapGradesToAllGrades(Set<Grade> grades) {
        return grades.stream()
                .map(grade -> grade.getSubject().getId() + ": " + grade.getMark().toString())
                .collect(Collectors.toSet());
    }

    default String mapCourseToCourseName(Course course) {
        return course != null ? course.getCourseName() : null;
    }


    Student dtoToStudent(StudentDto studentDto);

}
