package com.java.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;


public class TeacherDto {

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "Email is required")
    @Pattern(regexp = ".+@ar\\.com", message = "The domain name of the email, must be: @ar.com ")
    private String email;

    @NotEmpty(message = "Password name is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9!@#$%^&*]{6,14}$", message = "Password must be min 6 and max 14 length containing at least 1 uppercase and 1 digit ")
    private String password;

    private Set<String> subjectsNames;

    private String courseName;

    public TeacherDto() {/* Empty constructor*/ }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getSubjectsNames() {
        return subjectsNames;
    }

    public void setSubjectsNames(Set<String> subjectsNames) {
        this.subjectsNames = subjectsNames;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
