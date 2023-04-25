package com.java.mappers;

import com.java.dto.StudentDto;
import com.java.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
    Student toModel(StudentDto studentDto);
    StudentDto toDto(Student student);
}
