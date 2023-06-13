package com.java.mappers;

import com.java.dto.StudentDto;
import com.java.model.Course;
import com.java.model.Grade;
import com.java.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(target = "allGrades", source = "grades")
    @Mapping(target = "courseName", source = "course")
    StudentDto studentToDto(Student student);

    default Set<String> mapGradesToAllGrades(Set<Grade> grades) {
        if (!grades.isEmpty()){
            return grades.stream()
                    .map(grade -> grade.getSubject().getId() + ": " + grade.getMark().toString())
                    .collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }

    default String mapCourseToCourseName(Course course) {
        return course != null ? course.getCourseName() : null;
    }


    Student dtoToStudent(StudentDto studentDto);

}
