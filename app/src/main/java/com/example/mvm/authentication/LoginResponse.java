package com.example.mvm.authentication;

public class LoginResponse {

    private String access_token;
    private String bearer;
    private String refresh_token;
    private Integer expires_in;
    private String scope;
    private String jti;

    public LoginResponse() {
    }

    public LoginResponse(String access_token, String bearer, String refresh_token, Integer expires_in, String scope, String jti) {
        this.access_token = access_token;
        this.bearer = bearer;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.scope = scope;
        this.jti = jti;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }
}
