package com.java.mappers;

import com.java.dto.GradeDto;
import com.java.model.Grade;
import com.java.model.Student;
import com.java.model.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GradeMapper {

    GradeMapper INSTANCE = Mappers.getMapper(GradeMapper.class);

    @Mapping(target = "studentId", source = "student")
    @Mapping(target = "subjectId", source = "subject")
    GradeDto gradeToDto(Grade grade);

    default Integer mapStudentToStudentId(Student student) {
        return student != null ? student.getId() : null;
    }

    default Integer mapSubjectToSubjectId(Subject subject) {
        return subject != null ? subject.getId() : null;
    }


    Grade dtoToGrade(GradeDto gradeDto);
}
