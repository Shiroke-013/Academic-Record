package com.java.service;

import com.java.model.Professor;

public interface ProfessorService extends CrudService<Professor, Integer> {

    Professor findByLastName(String lastName);

}
