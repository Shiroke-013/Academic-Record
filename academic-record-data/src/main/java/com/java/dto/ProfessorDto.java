package com.java.dto;

import com.java.model.BaseEntity;

import java.io.Serializable;

public class ProfessorDto extends BaseEntity implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
