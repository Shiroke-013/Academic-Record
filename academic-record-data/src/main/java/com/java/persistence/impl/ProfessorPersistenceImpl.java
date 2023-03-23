package com.java.persistence.impl;

import com.java.dto.ProfessorDto;
import com.java.model.Professor;
import com.java.persistence.ExceptionPersistence;
import com.java.persistence.ProfessorPersistence;
import com.java.persistence.repository.ProfessorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.Optional;

@Repository
public class ProfessorPersistenceImpl implements ProfessorPersistence {

    @Autowired
    ProfessorRepository professorRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void create(Professor professor) throws ExceptionPersistence {
        try{
            professorRepository.save(professor);
        }catch (Exception e){
            throw new ExceptionPersistence("Failed to create Professor");
        }
    }

    @Override
    public Collection<Professor> getAll() throws ExceptionPersistence {
        try{
            if (professorRepository.count() != 0){
                return professorRepository.findAll();
            }
        } catch (Exception e){
            throw new ExceptionPersistence("Failed to get All Professors");
        }
        return professorRepository.findAll();
    }

    @Override
    public Professor findById(Integer id) throws ExceptionPersistence {
        try{
            Optional<Professor> professor = professorRepository.findById(id);
            if (professor.isPresent()){
                return professor.get();
            }
        } catch (Exception e){
            throw new ExceptionPersistence("Could not find Professor with id: " + id);
        }
        return new Professor();
    }

    @Override
    public void delete(Integer id) throws ExceptionPersistence {
        try{
            professorRepository.deleteById(id);
        } catch (Exception e){
            throw new ExceptionPersistence("Could not delete Professor with id: " + id);
        }
    }

    @Override
    public void deleteAll() throws ExceptionPersistence {
        try{
            if (professorRepository.count()!=0){
                professorRepository.deleteAll();
            }
        }catch (Exception e){
            throw new ExceptionPersistence("Could not delete Professors");
        }
    }

    /**
     * The method is null due to slow implementation...
     * @param professorDto
     * @param id
     * @throws ExceptionPersistence
     */
    @Override
    public void update(ProfessorDto professorDto, Integer id) throws ExceptionPersistence {
        //Does nothing due to slow implementation...
    }
}
