module com.vanderlelie.frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.net.http;
    requires com.google.gson;

    opens com.vanderlelie.frontend to javafx.fxml;
    exports com.vanderlelie.frontend;
    opens com.vanderlelie.frontend.views to javafx.fxml;
    exports com.vanderlelie.frontend.views;
    opens com.vanderlelie.frontend.controllers to javafx.fxml;
    exports com.vanderlelie.frontend.controllers;
    exports com.vanderlelie.frontend.observers;
    opens com.vanderlelie.frontend.models to com.google.gson;
    exports com.vanderlelie.frontend.models;
    opens com.vanderlelie.frontend.models.responses to com.google.gson;
    exports com.vanderlelie.frontend.models.responses;
}