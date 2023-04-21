package com.java.dto;

import com.java.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto extends BaseEntity {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
