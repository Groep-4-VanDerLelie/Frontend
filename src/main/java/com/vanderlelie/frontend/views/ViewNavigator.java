package com.vanderlelie.frontend.views;

import com.vanderlelie.frontend.enums.ViewRoute;
import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ViewNavigator {
    private static ViewNavigator instance;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private ViewNavigator(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void initialize(Stage primaryStage) {
        if (instance == null) {
            instance = new ViewNavigator(primaryStage);
        }
    }

    public static ViewNavigator getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ViewNavigator has not been initialized.");
        }
        return instance;
    }

    public void navigateTo(ViewRoute route) {
        String viewPath = "";
        switch (route) {
            case LOGIN -> viewPath = "login-page";
            case ORDER -> viewPath = "order-page";
            default -> viewPath = "route-not-found";
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(viewPath + ".fxml"));

            root = fxmlLoader.load();

            if (scene == null) {
                scene = new Scene(root);
                stage.setScene(scene);

                scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                    if (event.getCode() == KeyCode.F11) {
                        stage.setFullScreen(!stage.isFullScreen());
                        event.consume();
                    }
                });
                stage.setFullScreen(true);
            } else {
                scene.setRoot(root);
            }

            stage.setTitle("Van der Lelie - Packaging");
            stage.setMinWidth(640);
            stage.setMinHeight(480);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
