package com.cricapp.cricrev.payload;


import lombok.Data;

@Data
public class SignUpDto {
    private String username;
    private String firstname;
    private String lastname;

    private String dob;
    private String email;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String name) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String name) {
        this.lastname = lastname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String email) {
        this.dob = dob;
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