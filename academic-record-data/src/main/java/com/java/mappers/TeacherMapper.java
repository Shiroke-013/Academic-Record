package com.java.mappers;

import com.java.dto.TeacherDto;
import com.java.model.Course;
import com.java.model.Subject;
import com.java.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface TeacherMapper {

    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    @Mapping(target = "subjectsNames", source = "subjects")
    @Mapping(target = "courseName", source = "course")
    TeacherDto teacherToDto(Teacher teacher);

    default Set<String> mapSubjectsToSubjectNames(Set<Subject> subjects) {
        return subjects.stream()
                .map(subject -> subject.getId() + ": " + subject.getSubjectName())
                .collect(Collectors.toSet());
    }

    default String mapCourseToCourseName(Course course) {
        return course != null ? course.getCourseName() : null;
    }


    Teacher dtoToTeacher(TeacherDto teacherDto);

}
