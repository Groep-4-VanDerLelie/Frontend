module com.vanderlelie.frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;

    opens com.vanderlelie.frontend to javafx.fxml;
    exports com.vanderlelie.frontend;
}