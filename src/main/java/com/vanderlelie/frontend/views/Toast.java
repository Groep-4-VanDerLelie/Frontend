package com.vanderlelie.frontend.views;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

public class Toast {

    private static int TOAST_TIMEOUT = 3000;

    private static Popup createPopup(String message, boolean isSuccess) {
        final Popup popup = new Popup();
        popup.setAutoFix(true);
        Label label = new Label(message);
        label.getStyleClass().add("toast");
        label.getStyleClass().add(isSuccess ? "success" : "error");

        URL fxmlResource = Toast.class.getResource("/styles/toast.css");
        label.getStylesheets().add(fxmlResource.toExternalForm());

        popup.getContent().add(label);
        return popup;
    }

    public static void show(String message, Control control, boolean isSuccess) {
        Scene scene = control.getScene();
        if (scene == null) {
            System.out.println("Couldn't display toast due to being on different screen.");
            return;
        }

        Stage stage = (Stage) scene.getWindow();
        if (stage == null) {
            return;
        }
        Popup popup = createPopup(message, isSuccess);
        popup.setOnShown(e -> {
            popup.setX(stage.getX() + stage.getWidth() / 2 - popup.getWidth() / 2);
            popup.setY(stage.getY() + stage.getHeight() / 1.2 - popup.getHeight() / 2);
        });
        popup.show(stage);

        new Timeline(new KeyFrame(
                Duration.millis(TOAST_TIMEOUT),
                ae -> popup.hide())).play();
    }
}