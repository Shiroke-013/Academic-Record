package com.java.mappers;

import com.java.dto.StudentDto;
import com.java.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentDto studentToDto(Student student);
    Student dtoToStudent(StudentDto studentDto);

}
