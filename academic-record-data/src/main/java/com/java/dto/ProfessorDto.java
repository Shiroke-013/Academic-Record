package com.java.dto;

import com.java.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class ProfessorDto extends BaseEntity implements Serializable {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
