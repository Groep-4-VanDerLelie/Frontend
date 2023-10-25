package com.vanderlelie.frontend;

import com.vanderlelie.frontend.enums.ViewRoute;
import com.vanderlelie.frontend.views.ViewNavigator;
import javafx.application.Application;
import javafx.stage.Stage;

public class VanDerLelieClient extends Application {
    @Override
    public void start(Stage stage) {
        ViewNavigator.initialize(stage);

        ViewNavigator navigator = ViewNavigator.getInstance();
        navigator.navigateTo(ViewRoute.LOGIN);
    }

    public static void main(String[] args) {
        launch();
    }
}