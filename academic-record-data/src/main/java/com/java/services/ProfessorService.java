package com.java.services;

import com.java.model.Professor;
import java.util.Set;

public interface ProfessorService extends CrudService<Professor, Integer> {

    Professor findByLastName(String lastName);

    void update(Professor professor, Integer id);

}
