package com.java.mappers;

import com.java.dto.GradeDto;
import com.java.model.Grade;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GradeMapper {

    GradeMapper INSTANCE = Mappers.getMapper(GradeMapper.class);

    GradeDto gradeToDto(Grade grade);
    Grade dtoToGrade(GradeDto gradeDto);
}
