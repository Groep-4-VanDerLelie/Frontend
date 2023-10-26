package com.vanderlelie.frontend.controllers;

import com.vanderlelie.frontend.models.User;
import com.vanderlelie.frontend.observers.AuthObserver;

public class AuthController {
    private User user = new User();
    private String username = "";
    private String password = "";
    static AuthController authController;

    public static AuthController getInstance() {
        if (authController == null){
            authController = new AuthController();
        }
        return authController;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return this.user;
    }

    public void login() {
        try {
            String authCode = requestAuthCode();
            String authToken = getTokenFromCode(authCode);

            this.user.notifyObservers(true);
        } catch (Exception e) {
            this.user.notifyObservers(false);
        }
    }

    private String requestAuthCode() {
        return "";
    }

    private String getTokenFromCode(String code) {
        return "";
    }


    public void registerLoginObserver(AuthObserver loginView) {
        this.user.registerObserver(loginView);
    }
}
