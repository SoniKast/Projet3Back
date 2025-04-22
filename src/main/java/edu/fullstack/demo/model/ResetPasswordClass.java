package edu.fullstack.demo.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPasswordClass {
    private String token;
    private String newPassword;

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
