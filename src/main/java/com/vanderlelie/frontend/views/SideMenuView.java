package com.vanderlelie.frontend.views;

import com.vanderlelie.frontend.controllers.AuthController;
import com.vanderlelie.frontend.models.User;
import com.vanderlelie.frontend.observers.AuthObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SideMenuView implements AuthObserver {
    @FXML
    private Label usernameLabel;
    @FXML
    private VBox userContextMenu;
    @FXML
    private Button userMenuToggle;
    private AuthController authController;

    public void initialize() {
        this.authController = AuthController.getInstance();
        this.authController.registerLoginObserver(this);

        User loggedInUser = this.authController.getUser();
        String username = loggedInUser.getUsername();
        if (username != null && username.length() > 0) {
            usernameLabel.setText(username);
        } else {
            usernameLabel.setText("No Username");
        }

        userContextMenu.setVisible(false);
    }

    public void toggleUserMenu() {
        userContextMenu.setVisible(!userContextMenu.isVisible());

        String toggleIcon = userContextMenu.isVisible() ? "▴" : "▾";
        userMenuToggle.setText(toggleIcon);
    }

    @Override
    public boolean update(boolean authorized) {
        return false;
    }
}
