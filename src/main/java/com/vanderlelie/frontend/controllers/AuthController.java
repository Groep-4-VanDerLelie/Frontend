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

    public void login() {
        // Check password logic here
        boolean isAuthorized = username.equals("test") && password.equals("123");

        this.user.notifyObservers(isAuthorized);
    }

    public void registerLoginObserver(AuthObserver loginView) {
        this.user.registerObserver(loginView);
    }
}
