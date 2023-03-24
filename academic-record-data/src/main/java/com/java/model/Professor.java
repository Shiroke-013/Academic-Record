package com.java.model;

import com.java.dto.ProfessorDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "professors")
public class Professor extends User {

    public Professor(ProfessorDto professorDto, Date createdAt) {
        this.setFirstName(professorDto.getFirstName());
        this.setLastName(professorDto.getLastName());
        this.setEmail(professorDto.getEmail());
        this.setPassword(professorDto.getPassword());
        this.setCreatedAt(createdAt);
    }

}