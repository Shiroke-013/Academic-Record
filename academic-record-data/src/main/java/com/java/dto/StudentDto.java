package com.java.dto;

import com.java.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Getter
@Setter
public class StudentDto extends BaseEntity implements Serializable {

    private Integer id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String password;

}
