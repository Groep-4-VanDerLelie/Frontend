package com.vanderlelie.frontend.views;

import com.vanderlelie.frontend.controllers.LogsController;
import com.vanderlelie.frontend.models.Log;
import com.vanderlelie.frontend.observers.LogResultObserver;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogCentreView implements LogResultObserver {
    private final int ENTRIES_PER_PAGE = 16;
    private final double LOG_ENTRY_MARGIN = 7.5;
    private HashMap<Integer, List<Log>> logsMap = new HashMap<>();
    @FXML
    private TextField searchInput;
    @FXML
    private Label resultsCountLabel;
    @FXML
    private Pagination resultsPagination;
    private LogsController logsController;

    public void initialize() {
        this.logsController = LogsController.getInstance();
        this.logsController.registerLogObserver(this);

        this.searchLogsByQuery();
    }

    public void searchLogsByQuery() {
        String query = searchInput.getText();

        try {
            logsController.searchLogsByQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.show("Couldn't fetch logs by query \"" + query + "\"", searchInput, false);
        }
    }

    private HBox createLogHBox(Log log) {
        HBox logBox = new HBox();
        logBox.getStyleClass().add("log-entry");
        logBox.setAlignment(Pos.CENTER_LEFT);

        Label logLabel = new Label();
        logLabel.setText(log.toString());
        logLabel.setAlignment(Pos.CENTER_LEFT);

        Button logInfoButton = new Button();
        logInfoButton.setAlignment(Pos.CENTER_RIGHT);
        logInfoButton.getStyleClass().add("log-info-button");
        HBox.setMargin(logInfoButton, new Insets(0, 0, 0, LOG_ENTRY_MARGIN));

        logBox.getChildren().addAll(logLabel, logInfoButton);

        HBox marginWrapper = new HBox(logBox);
        HBox.setMargin(logBox, new Insets(LOG_ENTRY_MARGIN));

        return marginWrapper;
    }

    private HBox createAllLogsHBox(int pageIndex) {
        HBox mainHolder = new HBox();
        mainHolder.setAlignment(Pos.CENTER);

        List<Log> currentPageLogs = logsMap.get(pageIndex);
        VBox leftSide = new VBox();
        int endLeftPosition = Math.min(ENTRIES_PER_PAGE / 2, currentPageLogs.size());
        for (Log log: currentPageLogs.subList(0, endLeftPosition)) {
            leftSide.getChildren().add(createLogHBox(log));
        }

        VBox rightSide = new VBox();
        int endRightPosition = Math.min(ENTRIES_PER_PAGE, currentPageLogs.size());
        for (Log log: currentPageLogs.subList(endLeftPosition, endRightPosition)) {
            rightSide.getChildren().add(createLogHBox(log));
        }

        mainHolder.getChildren().addAll(leftSide, rightSide);
        return mainHolder;
    }

    @Override
    public boolean update(ArrayList<Log> logs) {
        Toast.show(String.format("Found %s results!", logs.size()), searchInput, true);
        resultsCountLabel.setText(String.format("Results (Showing %s of %s)", 10, logs.size()));

        logsMap.clear();
        for (int i = 0; i < logs.size(); i += ENTRIES_PER_PAGE) {
            int endPosition = Math.min(i + ENTRIES_PER_PAGE, logs.size());
            List<Log> chunk = logs.subList(i, endPosition);
            logsMap.put(i / ENTRIES_PER_PAGE, chunk);
        }

        resultsPagination.setPageCount((int) Math.ceil((double) logs.size() / ENTRIES_PER_PAGE));
        resultsPagination.setCurrentPageIndex(0);
        resultsPagination.setMaxPageIndicatorCount(6);
        resultsPagination.setPageFactory(this::createAllLogsHBox);

        return false;
    }
}
