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
            Professor professor = findById(id);
            if (professor != null){
                if (professorDto.getFirstName() != null){
                    professor.setFirstName(professorDto.getFirstName());
                }
                if (professorDto.getLastName() != null){
                    professor.setLastName(professorDto.getLastName());
                }
                if (professorDto.getEmail() != null){
                    professor.setEmail(professorDto.getEmail());
                }
                if (professorDto.getPassword() != null){
                professor.setPassword(professorDto.getPassword());
                }
                professorPersistence.update(professor,id);
            }
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }
}
