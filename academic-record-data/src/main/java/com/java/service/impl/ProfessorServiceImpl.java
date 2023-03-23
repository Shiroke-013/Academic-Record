package com.java.service.impl;

import com.java.dto.ProfessorDto;
import com.java.model.Professor;
import com.java.persistence.ProfessorPersistence;
import com.java.service.ExceptionService;
import com.java.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
            Integer id = UUID.randomUUID().hashCode();
            Professor professor = new Professor(id, professorDto, new Date());
            professorPersistence.create(professor);
            return professor;
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Set<Professor> findAll() throws ExceptionService {
        try{
            return (Set<Professor>) professorPersistence.getAll();
        }catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public Professor findById(Integer id) {
        return null;
    }

    @Override
    public void delete() throws ExceptionService {
        try {
            professorPersistence.deleteAll();
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) throws ExceptionService {
        try {
            professorPersistence.delete(id);
        } catch (Exception e){
            throw new ExceptionService(e.getMessage());
        }
    }

    @Override
    public void update(Professor professor, Integer id) {
        //Not yet...
    }
}
