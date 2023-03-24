package com.java.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class User extends BaseEntity{

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
