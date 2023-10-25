package com.vanderlelie.frontend.views;

import com.vanderlelie.frontend.controllers.AuthController;
import com.vanderlelie.frontend.enums.ViewRoute;
import com.vanderlelie.frontend.observers.AuthObserver;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginView implements AuthObserver {
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    private AuthController authController;

    public void initialize() {
        this.authController = AuthController.getInstance();
        this.authController.registerLoginObserver(this);

        usernameInput.textProperty().addListener((_observable, _oldValue, newValue) -> {
            this.authController.setUsername(newValue);
        });

        passwordInput.textProperty().addListener((_observable, _oldValue, newValue) -> {
            this.authController.setPassword(newValue);
        });
    }

    public void signIn() {
        this.authController.login();
    }

    private void navigateToOrderPage() {
        ViewNavigator.getInstance().navigateTo(ViewRoute.ORDER);
    }

    @Override
    public boolean update(boolean authorized) {
        if (authorized) {
            this.navigateToOrderPage();

            return true;
        }

        System.out.println("nuh uh");

        return false;
    }
}
