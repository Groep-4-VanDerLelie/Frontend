package com.vanderlelie.frontend.views;

import com.vanderlelie.frontend.controllers.LogsController;
import com.vanderlelie.frontend.models.Log;
import com.vanderlelie.frontend.observers.LogResultObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class LogCentreView implements LogResultObserver {
    @FXML
    private TextField searchInput;
    @FXML
    private Label resultsCountLabel;
    private LogsController logsController;

    public void initialize() {
        this.logsController = LogsController.getInstance();
        this.logsController.registerLogObserver(this);
    }

    public void searchLogsByQuery(ActionEvent actionEvent) {
        String query = searchInput.getText();

        try {
            logsController.searchLogsByQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.show("Couldn't fetch logs by query \"" + query + "\"", searchInput, false);
        }
    }

    @Override
    public boolean update(ArrayList<Log> logs) {
        resultsCountLabel.setText(String.format("Showing results (%s of %s)", 10, logs.size()));

        return false;
    }
}
