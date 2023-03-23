package com.java.model;

import com.java.dto.ProfessorDto;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Professor extends User {

    public Professor(Integer id, ProfessorDto professorDto, Date createdAt) {
        this.setId(id);
        this.setFirstName(professorDto.getFirstName());
        this.setLastName(professorDto.getLastName());
        this.setEmail(professorDto.getEmail());
        this.setPassword(professorDto.getPassword());
        this.setCreatedAt(createdAt);
    }
}
