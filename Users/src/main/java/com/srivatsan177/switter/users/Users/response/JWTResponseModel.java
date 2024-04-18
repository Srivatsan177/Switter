package com.srivatsan177.switter.users.Users.response;

public class JWTResponseModel {
    public String jwt;

    public JWTResponseModel(String jwt) {
        this.jwt = jwt;
    }

    public JWTResponseModel() {
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
