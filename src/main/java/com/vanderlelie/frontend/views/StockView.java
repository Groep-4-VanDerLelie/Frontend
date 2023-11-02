package com.vanderlelie.frontend.views;

import com.vanderlelie.frontend.controllers.AuthController;
import com.vanderlelie.frontend.controllers.StockController;
import com.vanderlelie.frontend.models.Log;
import com.vanderlelie.frontend.models.Packaging;
import com.vanderlelie.frontend.observers.StockResultObserver;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StockView implements StockResultObserver {
    private final int ENTRIES_PER_PAGE = 16;
    private final double LOG_ENTRY_MARGIN = 7.5;
    private final double SPACING_MARGIN = 5;
    private final int INFO_BOX_TOP_MARGIN = 15;
    @FXML
    private TextField searchInput;
    private StackPane rootPane;
    private StockController stockController;
    private HashMap<Integer, List<Packaging>> packagingMap = new HashMap<>();
    @FXML
    private Label resultsCountLabel;
    @FXML
    private Pagination resultsPagination;

    public void initialize() {
        this.stockController = StockController.getInstance();
        this.stockController.registerLogObserver(this);

        this.searchStocksByQuery();
    }

    public void searchStocksByQuery() {
        String query = searchInput.getText();

        try {
            stockController.searchStocksByQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.show("Couldn't fetch stocks by query \"" + query + "\"", searchInput, false);
        }
    }

    private HBox createPackagingHBox(Packaging packaging) {
        HBox packagingBox = new HBox();
        packagingBox.getStyleClass().add("log-entry");
        packagingBox.setAlignment(Pos.CENTER_LEFT);

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label packagingLeftLabel = new Label();
        packagingLeftLabel.setText(packaging.getName());
        HBox.setMargin(packagingLeftLabel, new Insets(0, 0, 0, SPACING_MARGIN));

        Label packagingRightLabel = new Label();
        packagingRightLabel.setText("In Stock: " + packaging.getMinimum_stock());
        packagingRightLabel.setAlignment(Pos.CENTER_RIGHT);

        Button logInfoButton = new Button();
        logInfoButton.setAlignment(Pos.CENTER_RIGHT);
        logInfoButton.getStyleClass().add("log-info-button");
        logInfoButton.setOnAction(e -> {
            this.showPackagingDetailsPage(packaging);
        });
        HBox.setMargin(logInfoButton, new Insets(0, SPACING_MARGIN, 0, SPACING_MARGIN * 2));

        packagingBox.getChildren().addAll(packagingLeftLabel, spacer, packagingRightLabel, logInfoButton);

        HBox marginWrapper = new HBox(packagingBox);
        HBox.setMargin(packagingBox, new Insets(LOG_ENTRY_MARGIN));

        return marginWrapper;
    }

    private HBox createAllPackagingHBox(int pageIndex) {
        HBox mainHolder = new HBox();
        mainHolder.setAlignment(Pos.CENTER);

        List<Packaging> currentPagePackaging = packagingMap.get(pageIndex);
        VBox leftSide = new VBox();
        int endLeftPosition = Math.min(ENTRIES_PER_PAGE / 2, currentPagePackaging.size());
        for (Packaging packaging: currentPagePackaging.subList(0, endLeftPosition)) {
            leftSide.getChildren().add(createPackagingHBox(packaging));
        }

        VBox rightSide = new VBox();
        int endRightPosition = Math.min(ENTRIES_PER_PAGE, currentPagePackaging.size());
        for (Packaging packaging: currentPagePackaging.subList(endLeftPosition, endRightPosition)) {
            rightSide.getChildren().add(createPackagingHBox(packaging));
        }

        mainHolder.getChildren().addAll(leftSide, rightSide);
        return mainHolder;
    }

    public void showPackagingDetailsPage(Packaging packaging) {
        if (rootPane == null) {
            rootPane = (StackPane) ViewNavigator.root.lookup("#rootPane");
        }

        rootPane.getChildren().add(createPackagingDetailsPage(packaging));
    }

    public void hidePackagingDetailsPage() {
        rootPane.getChildren().remove(1);
    }

    private BorderPane createPackagingDetailsPage(Packaging packaging) {
        BorderPane mainContainer = new BorderPane();
        mainContainer.getStyleClass().add("log-details-container");
        mainContainer.setOnMouseClicked(e -> {
            if (e.getPickResult().getIntersectedNode() != mainContainer) {
                return;
            }

            this.hidePackagingDetailsPage();
        });

        VBox detailsContainer = new VBox();
        detailsContainer.setMaxSize(600, 450);
        detailsContainer.getStyleClass().add("log-details-content");

        HBox topLine = new HBox();
        VBox packageInfo = new VBox();
        Label packageTitle = new Label(packaging.getName());
        packageTitle.getStyleClass().add("title");
        Rectangle logTitleUnderLine = new Rectangle(200, 5);
        logTitleUnderLine.setFill(Color.rgb(75, 200, 182));
        Label logDateLabel = new Label(packaging.getLocation());
        logDateLabel.getStyleClass().add("log-date-label");
        VBox.setMargin(packageInfo, new Insets(0, 0, INFO_BOX_TOP_MARGIN, 0));
        packageInfo.getChildren().addAll(packageTitle, logTitleUnderLine, logDateLabel);

        Button closePanelButton = new Button("X");
        closePanelButton.setMaxSize(20, 20);
        closePanelButton.getStyleClass().add("panel-close-button");
        closePanelButton.setAlignment(Pos.CENTER_RIGHT);

        HBox.setHgrow(packageInfo, Priority.ALWAYS);
        topLine.getChildren().addAll(packageInfo, closePanelButton);

        VBox productInfo = new VBox();
        Label productInfoTitle = new Label("Packaging Info");
        productInfoTitle.getStyleClass().add("title");
        Label productInfoSubType = new Label("Type: " + packaging.getPackaging_type());
        Label productInfoSubHeight = new Label("Height: " + packaging.getHeight() + "cm");
        Label productInfoSubWidth = new Label("Width: " + packaging.getWidth() + "cm");
        Label productInfoSubLength = new Label("Length: " + packaging.getLength() + "cm");
        Label productInfoSubAmount = new Label("Amount Left: " + packaging.getMinimum_stock());
        Label productInfoSubCompany = new Label("Company: " + packaging.getCompany());
        VBox.setMargin(productInfo, new Insets(INFO_BOX_TOP_MARGIN, 0, 0, 0));
        productInfo.getChildren().addAll(productInfoTitle, productInfoSubType, productInfoSubHeight,
                productInfoSubWidth, productInfoSubLength, productInfoSubAmount, productInfoSubCompany);

        VBox alertSetting = new VBox();
        Label alertSettingTitle = new Label("Alert Settings");
        alertSettingTitle.getStyleClass().add("title");
        Label alertSubTitle = new Label("Send an alert when stock reaches:");
        TextField alertSettingInput = new TextField();
        alertSettingInput.setPromptText("Input a number");
        VBox.setMargin(productInfo, new Insets(INFO_BOX_TOP_MARGIN, 0, 0, 0));
        alertSetting.getChildren().addAll(alertSettingTitle, alertSubTitle, alertSettingInput);

        HBox bottomBar = new HBox();
        bottomBar.setAlignment(Pos.BOTTOM_CENTER);
        bottomBar.setSpacing(10);
        HBox spacer = new HBox();

        Button editButton = new Button("Apply Edits");
        editButton.getStyleClass().add("panel-edit-button");
        editButton.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBar.getChildren().addAll(spacer, editButton);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(bottomBar, Priority.ALWAYS);
        VBox.setVgrow(bottomBar, Priority.ALWAYS);

        HBox middleSection = new HBox(productInfo, alertSetting);
        HBox.setMargin(productInfo, new Insets(0, 150, 0, 0));
        VBox.setMargin(middleSection, new Insets(15, 0, 0, 0));

        detailsContainer.getChildren().addAll(topLine, middleSection, bottomBar);
        mainContainer.setCenter(detailsContainer);
        return mainContainer;
    }

    @Override
    public boolean update(ArrayList<Packaging> packaging) {
        Toast.show(String.format("Found %s results!", packaging.size()), searchInput, true);
        resultsCountLabel.setText(String.format("Results (Showing %s of %s)", 10, packaging.size()));

        packagingMap.clear();
        for (int i = 0; i < packaging.size(); i += ENTRIES_PER_PAGE) {
            int endPosition = Math.min(i + ENTRIES_PER_PAGE, packaging.size());
            List<Packaging> chunk = packaging.subList(i, endPosition);
            packagingMap.put(i / ENTRIES_PER_PAGE, chunk);
        }

        resultsPagination.setPageCount((int) Math.ceil((double) packaging.size() / ENTRIES_PER_PAGE));
        resultsPagination.setCurrentPageIndex(0);
        resultsPagination.setMaxPageIndicatorCount(6);
        resultsPagination.setPageFactory(this::createAllPackagingHBox);

        return false;
    }
}
