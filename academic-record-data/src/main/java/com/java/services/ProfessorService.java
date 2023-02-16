package com.java.services;

import com.java.model.Professor;
import java.util.Set;

public interface ProfessorService {

    Professor findByLastName(String lastName);
    Professor findById(Long id);
    Professor save(Professor professor);
    Set<Professor> findAll();
}
