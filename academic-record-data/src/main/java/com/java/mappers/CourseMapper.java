package com.java.mappers;

import com.java.dto.CourseDto;
import com.java.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    CourseDto courseToDto(Course student);
    Course dtoToCourse(CourseDto studentDto);

}
