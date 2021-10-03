package de.dtonal.payroll.rest.controller.auth;

import javax.validation.constraints.NotBlank;

public class VerifyMailRequest {

    @NotBlank
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
