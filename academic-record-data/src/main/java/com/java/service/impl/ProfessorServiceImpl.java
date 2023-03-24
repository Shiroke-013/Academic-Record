package com.java.service.impl;

import com.java.dto.ProfessorDto;
import com.java.model.Professor;
import com.java.persistence.ProfessorPersistence;
import com.java.service.ExceptionService;
import com.java.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    @Autowired
    private ProfessorPersistence professorPersistence;

    @Override
    public Professor save(ProfessorDto professorDto) throws ExceptionService {
        try {
            Professor professor = new Professor(professorDto, new Date());
            professorPersistence.create(professor);
            return professor;
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Collection<Professor> findAll() throws ExceptionService {
        try{
            return professorPersistence.getAll();
        }catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Professor findById(Integer id) throws ExceptionService {
        try{
            return professorPersistence.findById(id);
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void delete() throws ExceptionService {
        try {
            professorPersistence.delete();
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) throws ExceptionService {
        try {
            professorPersistence.deleteById(id);
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void update(ProfessorDto professorDto, Integer id) throws ExceptionService {
        try {
            if (professorPersistence.findById(id) != null){
                Professor professor = professorPersistence.findById(id);
                professor.setFirstName(professorDto.getFirstName());
                professor.setLastName(professorDto.getLastName());
                professor.setEmail(professorDto.getEmail());
                professor.setPassword(professorDto.getPassword());
                professorPersistence.update(professor,id);
            }
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }
}
