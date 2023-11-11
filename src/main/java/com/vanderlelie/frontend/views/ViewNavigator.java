package com.vanderlelie.frontend.views;

import com.vanderlelie.frontend.enums.ViewRoute;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ViewNavigator {
    private static ViewNavigator instance;
    private Stage stage;
    private Scene scene;
    public static Parent root;
    private Locale selectedLanguage = Locale.UK;

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
            case LOGGER -> viewPath = "logs-centre-page";
            case ADMIN -> viewPath = "admin-page";
            default -> viewPath = "route-not-found";
        }

        try {
            ResourceBundle languageBundle = ResourceBundle.getBundle("languages/" + viewPath, selectedLanguage);
            
            URL fxmlResource = getClass().getResource("/views/" + viewPath + ".fxml");
            if (fxmlResource == null) {
                System.out.println("Couldn't find fxml resource");
                return;
            }

            root = FXMLLoader.load(fxmlResource, languageBundle);

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
            stage.setFullScreenExitHint("");
            stage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void switchLanguage() {
        if (this.selectedLanguage.equals(Locale.UK)) {
            selectedLanguage = Locale.of("ro_RO");
        } else {
            selectedLanguage = Locale.UK;
        }
    }

    public Locale getSelectedLanguage() {
        return selectedLanguage;
    }
}
