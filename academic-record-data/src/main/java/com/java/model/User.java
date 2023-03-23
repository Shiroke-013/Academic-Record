package com.java.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends BaseEntity{

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
