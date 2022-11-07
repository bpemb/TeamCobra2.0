package com.example.uploadingfiles.user;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class UserData implements Serializable
{

    @NotEmpty(message = "{register.validation.firstname}")
    private String firstName;

    @NotEmpty(message = "{registation.validation.lastname}")
    private String lastName;

    @NotEmpty(message = "{registation.validation.email}")
    private String email;

    @NotEmpty(message = "{registation.validation.password}")
    private String password;


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
}
