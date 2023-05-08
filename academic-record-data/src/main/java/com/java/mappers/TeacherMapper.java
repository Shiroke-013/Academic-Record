package com.java.mappers;

import com.java.dto.TeacherDto;
import com.java.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherMapper {

    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    TeacherDto teacherToDto(Teacher teacher);
    Teacher dtoToTeacher(TeacherDto teacherDto);

}
