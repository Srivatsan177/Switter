package com.srivatsan177.switter.users.Users.request;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class UserSignupRequestModel {
    @NotNull(message = "Username is required")
    private String username;
    @NotNull(message = "Password is required")
    private String password;
    @NotNull(message = "Email is required")
    private String email;
    @NotNull(message = "Date of Birth is required")
    private Date dob;

    public UserSignupRequestModel() {
    }

    public UserSignupRequestModel(String username, String password, String email, Date dob) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
