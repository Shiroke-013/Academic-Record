package com.java.mappers;

import com.java.dto.SubjectDto;
import com.java.model.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubjectMapper {

    SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);

    SubjectDto subjectToDto(Subject subject);
    Subject dtoToSubject(SubjectDto subjectDto);

}
