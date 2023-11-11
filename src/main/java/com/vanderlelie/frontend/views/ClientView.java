package com.vanderlelie.frontend.views;

import com.vanderlelie.frontend.controllers.CustomerController;
import com.vanderlelie.frontend.models.Customer;
import com.vanderlelie.frontend.observers.ClientObserver;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientView implements ClientObserver {
    private final int INFO_BOX_TOP_MARGIN = 15;
    private final int ENTRIES_PER_PAGE = 16;
    private final double LOG_ENTRY_MARGIN = 7.5;
    private final double SPACING_MARGIN = 5;
    private StackPane rootPane;

    @FXML
    private TextField searchInput;

    @FXML
    private Label resultsCountLabel;

    @FXML
    private Pagination resultsPagination;
    private HashMap<Integer, List<Customer>> customerMap = new HashMap<>();
    private CustomerController customerController;
    private Label columnName;
    private TextField valueInput;

    public void initialize(){
        this.customerController = CustomerController.getInstance();
        this.customerController.registerClientObserver(this);

        this.searchCustomerByQuery();
    }

    public  void searchCustomerByQuery() {
        String query = searchInput.getText();

        try {
            if (query.isEmpty()){
                customerController.searchCustomersByQuery(query);
            } else {
                customerController.searchCustomersByCustomerNumber(Integer.parseInt(query));
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.show("Couldn't fetch customers by query \"" + query + "\"", searchInput, false);
        }
    }

    private HBox createCustomerHBox(Customer customer) {
        HBox customerBox = new HBox();
        customerBox.getStyleClass().add("log-entry");
        customerBox.setAlignment(Pos.CENTER_LEFT);

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label customerLeftLabel = new Label();
        customerLeftLabel.setText(customer.getName());
        HBox.setMargin(customerLeftLabel, new Insets(0, 0, 0, SPACING_MARGIN));

        Button logInfoButton = new Button();
        logInfoButton.setAlignment(Pos.CENTER_RIGHT);
        logInfoButton.getStyleClass().add("log-info-button");
        logInfoButton.setOnAction(e -> {
            this.showCustomerDetailsPage(customer);
        });
        HBox.setMargin(logInfoButton, new Insets(0, SPACING_MARGIN, 0, SPACING_MARGIN * 2));

        customerBox.getChildren().addAll(customerLeftLabel, spacer, logInfoButton);

        HBox marginWrapper = new HBox(customerBox);
        HBox.setMargin(customerBox, new Insets(LOG_ENTRY_MARGIN));

        return marginWrapper;
    }

    private void showCustomerDetailsPage(Customer customer) {
        if (rootPane == null) {
            rootPane = (StackPane) ViewNavigator.root.lookup("#rootPane");
        }

        rootPane.getChildren().add(createPackagingDetailsPage(customer));
    }

    private HBox createAllPackagingHBox(int pageIndex) {
        resultsCountLabel.setText(String.format("Results (Showing %s of %s)",
                (ENTRIES_PER_PAGE * pageIndex) + 1 + " - " + ENTRIES_PER_PAGE * (pageIndex + 1), customerMap.size() * ENTRIES_PER_PAGE));

        HBox mainHolder = new HBox();
        mainHolder.setAlignment(Pos.CENTER);

        List<Customer> currentPageCustomer = customerMap.get(pageIndex);
        VBox leftSide = new VBox();
        int endLeftPosition = Math.min(ENTRIES_PER_PAGE / 2, currentPageCustomer.size());
        for (Customer customer: currentPageCustomer.subList(0, endLeftPosition)) {
            leftSide.getChildren().add(createCustomerHBox(customer));
        }

        VBox rightSide = new VBox();
        int endRightPosition = Math.min(ENTRIES_PER_PAGE, currentPageCustomer.size());
        for (Customer customer: currentPageCustomer.subList(endLeftPosition, endRightPosition)) {
            rightSide.getChildren().add(createCustomerHBox(customer));
        }

        mainHolder.getChildren().addAll(leftSide, rightSide);
        return mainHolder;
    }

    private void hidePackagingDetailsPage() { rootPane.getChildren().remove(1);}

    private BorderPane createPackagingDetailsPage(Customer customer) {
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
        Label packageTitle = new Label(customer.getName());
        packageTitle.getStyleClass().add("title");
        Rectangle logTitleUnderLine = new Rectangle(200, 5);
        logTitleUnderLine.setFill(Color.rgb(75, 200, 182));
        Label logDateLabel = new Label("Customer Number: " + customer.getCustomerNumber());
        logDateLabel.getStyleClass().add("log-date-label");
        VBox.setMargin(packageInfo, new Insets(0, 0, INFO_BOX_TOP_MARGIN, 0));
        packageInfo.getChildren().addAll(packageTitle, logTitleUnderLine, logDateLabel);

        Button closePanelButton = new Button("X");
        closePanelButton.setMaxSize(20, 20);
        closePanelButton.getStyleClass().add("panel-close-button");
        closePanelButton.setOnAction(e -> {
            this.hidePackagingDetailsPage();
        });
        closePanelButton.setAlignment(Pos.CENTER_RIGHT);

        HBox.setHgrow(packageInfo, Priority.ALWAYS);
        topLine.getChildren().addAll(packageInfo, closePanelButton);

        VBox productInfo = new VBox();
        Label customerInfoTitle = new Label("Customer Info");
        customerInfoTitle.getStyleClass().add("title");
        Label customerInfoSubVsAddress = new Label("Visit Address: " + customer.getVisitAddress());
        customerInfoSubVsAddress.setOnMouseClicked(this::changeColumn);
        Label customerInfoSubDvAddress = new Label("Delivery Address: " + customer.getDeliveryAddress());
        customerInfoSubDvAddress.setOnMouseClicked(this::changeColumn);
        Label customerInfoSubPhone = new Label("Phone Nr: " + customer.getPhoneNr());
        customerInfoSubPhone.setOnMouseClicked(this::changeColumn);
        Label customerInfoSubPostalCode = new Label("Postal Code: " + customer.getPostalCode());
        customerInfoSubPostalCode.setOnMouseClicked(this::changeColumn);
        Label customerInfoSubEmail = new Label("Email: " + customer.getEmail());
        customerInfoSubEmail.setOnMouseClicked(this::changeColumn);
        VBox.setMargin(productInfo, new Insets(INFO_BOX_TOP_MARGIN, 0, 0, 0));
        productInfo.getChildren().addAll(customerInfoTitle, customerInfoSubVsAddress,
                customerInfoSubDvAddress, customerInfoSubPhone, customerInfoSubPostalCode, customerInfoSubEmail);

        VBox alertSetting = new VBox();
        columnName = new Label();
        columnName.getStyleClass().add("title");
        valueInput = new TextField();
        valueInput.setPromptText("Input Value");
        valueInput.setVisible(false);
        VBox.setMargin(productInfo, new Insets(INFO_BOX_TOP_MARGIN, 0, 0, 0));
        alertSetting.getChildren().addAll(columnName, valueInput);

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

        editButton.setOnMouseClicked(mouseEvent -> {
            try {
                String column = null;
                customerController.updateCustomerByQuery(customer.getId().toString(),columnName.getText().toLowerCase().replaceAll("\\s", ""), valueInput.getText());
                switch (columnName.getText()){
                    case "Visit Address" -> {
                        customerInfoSubVsAddress.setText("Visit Address: " + valueInput.getText());
                        column = "visitAddress";
                    }
                    case "Delivery Address" -> {
                        customerInfoSubDvAddress.setText("Delivery Address: " + valueInput.getText());
                        column = "deliveryAddress";
                    }
                    case "Phone Nr" -> {
                        customerInfoSubPhone.setText("Phone Nr: " + valueInput.getText());
                        column = "phoneNr";
                    }
                    case "Postal Code" -> {
                        customerInfoSubPostalCode.setText("Postal Code: " + valueInput.getText());
                        column = "postalCode";
                    }
                    case "Email" -> {
                        customerInfoSubEmail.setText("Email: " + valueInput.getText());
                        column = "email";
                    }
                }
                customerController.updateCustomerByQuery(customer.getId().toString(),column, valueInput.getText());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        HBox middleSection = new HBox(productInfo, alertSetting);
        HBox.setMargin(productInfo, new Insets(0, 150, 0, 0));
        VBox.setMargin(middleSection, new Insets(15, 0, 0, 0));

        detailsContainer.getChildren().addAll(topLine, middleSection, bottomBar);
        mainContainer.setCenter(detailsContainer);
        return mainContainer;
    }

    public void changeColumn(MouseEvent event) {
        String source = event.getSource().toString();
        String name = source.substring(source.indexOf("]") + 2, source.indexOf(":"));
        columnName.setText(name);
        valueInput.setVisible(true);
    }

    @Override
    public boolean update(ArrayList<Customer> customers) {
        Toast.show(String.format("Found %s results!", customers.size()), searchInput, true);

        customerMap.clear();
        for (int i = 0; i < customers.size(); i += ENTRIES_PER_PAGE) {
            int endPosition = Math.min(i + ENTRIES_PER_PAGE, customers.size());
            List<Customer> chunk = customers.subList(i, endPosition);
            customerMap.put(i / ENTRIES_PER_PAGE, chunk);
        }

        resultsPagination.setPageCount((int) Math.ceil((double) customers.size() / ENTRIES_PER_PAGE));
        resultsPagination.setCurrentPageIndex(0);
        resultsPagination.setMaxPageIndicatorCount(6);
        resultsPagination.setPageFactory(this::createAllPackagingHBox);
        return false;
    }
}
