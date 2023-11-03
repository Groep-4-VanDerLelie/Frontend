package com.vanderlelie.frontend.views;

import com.vanderlelie.frontend.controllers.CustomerController;
import com.vanderlelie.frontend.controllers.PackagingController;
import com.vanderlelie.frontend.models.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class ClientView {

    @FXML
    private Label customerName;

    @FXML
    private TextField inputCustomerNumber;

    private Customer[] customers;

    private CustomerController customerController;
    private PackagingController packagingController;

    public void initialize(){
        this.customerController = CustomerController.getInstance();

        inputCustomerNumber.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                try {
                    customers = customerController.getCustomers();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
