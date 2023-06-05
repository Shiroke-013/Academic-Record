package com.java.mappers;

import com.java.dto.SubjectDto;
import com.java.model.Course;
import com.java.model.Student;
import com.java.model.Subject;
import com.java.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface SubjectMapper {

    SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);

    @Mapping(target = "studentsNames", source = "students")
    @Mapping(target = "coursesNames", source = "courses")
    @Mapping(target = "teacherName", source = "teacher")
    SubjectDto subjectToDto(Subject subject);

    default Set<String> mapStudentsToStudentNames(Set<Student> students) {
        return students.stream()
                .map(student -> student.getFirstName() + " " + student.getLastName())
                .collect(Collectors.toSet());
    }

    default Set<String> mapCoursesToCourseNames(Set<Course> courses) {
        return courses.stream()
                .map(Course::getCourseName)
                .collect(Collectors.toSet());
    }

    default String mapTeacherToTeacherName(Teacher teacher) {
        return teacher != null ? teacher.getFirstName() + " " + teacher.getLastName() : null;
    }

    Subject dtoToSubject(SubjectDto dto);

}
