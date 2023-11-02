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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogCentreView implements LogResultObserver {
    private final int ENTRIES_PER_PAGE = 16;
    private final double LOG_ENTRY_MARGIN = 7.5;
    private final int INFO_BOX_TOP_MARGIN = 15;
    private HashMap<Integer, List<Log>> logsMap = new HashMap<>();
    @FXML
    private StackPane rootPane;
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
        logInfoButton.setOnAction(e -> {
            this.showLogDetailsPage(log);
        });
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

    public void showLogDetailsPage(Log log) {
        rootPane.getChildren().add(createLogDetailsPage(log));
    }

    private BorderPane createLogDetailsPage(Log log) {
        BorderPane mainContainer = new BorderPane();
        mainContainer.getStyleClass().add("log-details-container");

        VBox detailsContainer = new VBox();
        detailsContainer.setMaxSize(600, 450);
        detailsContainer.getStyleClass().add("log-details-content");

        VBox logInfo = new VBox();
        Label logTitle = new Label("Hema (3402030)");
        logTitle.getStyleClass().add("title");
        Rectangle logTitleUnderLine = new Rectangle(200, 5);
        logTitleUnderLine.setFill(Color.rgb(75, 200, 182));
        Label logDateLabel = new Label("Processed on 11 sept 2023 (12:32 pm)");
        logDateLabel.getStyleClass().add("log-date-label");
        VBox.setMargin(logInfo, new Insets(0, 0, INFO_BOX_TOP_MARGIN, 0));
        logInfo.getChildren().addAll(logTitle, logTitleUnderLine, logDateLabel);

        HBox packagingAndArchiveInfo = new HBox();
        VBox packagingInfo = new VBox();
        Label packagingInfoTitle = new Label("Packaging Info");
        packagingInfoTitle.getStyleClass().add("title");
        Label packagingInfoSubUsed = new Label("Used: Hema Large");
        Label packagingInfoSubDefault = new Label("Default: Hema Small");
        HBox.setMargin(packagingInfo, new Insets(0, INFO_BOX_TOP_MARGIN * 8, 0, 0));
        packagingInfo.getChildren().addAll(packagingInfoTitle, packagingInfoSubUsed, packagingInfoSubDefault);

        VBox archiveInfo = new VBox();
        Label archiveInfoTitle = new Label("Archive Info");
        archiveInfoTitle.getStyleClass().add("title");
        Label archiveInfoSubUsed = new Label("By: John Doe");
        Label archiveInfoSubDefault = new Label("Date: 11 sept 2023 (12:42 pm)");
        archiveInfo.getChildren().addAll(archiveInfoTitle, archiveInfoSubUsed, archiveInfoSubDefault);
        packagingAndArchiveInfo.getChildren().addAll(packagingInfo, archiveInfo);

        VBox productInfo = new VBox();
        Label productInfoTitle = new Label("Product Info");
        productInfoTitle.getStyleClass().add("title");
        Label productInfoSubName = new Label("Name: Hema Gordijn (24e90290)");
        Label productInfoSubSize = new Label("Size: 30cm x 25cm");
        Label productInfoSubType = new Label("Type: Hanger");
        VBox.setMargin(productInfo, new Insets(INFO_BOX_TOP_MARGIN, 0, 0, 0));
        productInfo.getChildren().addAll(productInfoTitle, productInfoSubName, productInfoSubSize, productInfoSubType);

        HBox bottomBar = new HBox();
        bottomBar.setAlignment(Pos.BOTTOM_CENTER);
        bottomBar.setSpacing(10);
        HBox userInfo = new HBox();
        userInfo.setSpacing(10);
        Circle userIcon = new Circle(15, Color.GREY);
        Label userLabel = new Label("Processed by John Doe");
        Button archiveButton = new Button("Archive");
        archiveButton.getStyleClass().add("log-archive-button");
        archiveButton.setAlignment(Pos.BOTTOM_RIGHT);
        userInfo.setAlignment(Pos.BOTTOM_LEFT);
        userInfo.getChildren().addAll(userIcon, userLabel);
        bottomBar.getChildren().addAll(userInfo, archiveButton);
        HBox.setHgrow(userInfo, Priority.ALWAYS);
        HBox.setHgrow(bottomBar, Priority.ALWAYS);
        VBox.setVgrow(bottomBar, Priority.ALWAYS);

        detailsContainer.getChildren().addAll(logInfo, packagingAndArchiveInfo, productInfo, bottomBar);
        mainContainer.setCenter(detailsContainer);
        return mainContainer;
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
