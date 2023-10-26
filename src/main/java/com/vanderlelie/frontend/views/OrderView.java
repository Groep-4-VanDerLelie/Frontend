package com.vanderlelie.frontend.views;

import com.vanderlelie.frontend.controllers.AuthController;
import com.vanderlelie.frontend.controllers.OrderController;
import com.vanderlelie.frontend.models.Order;
import com.vanderlelie.frontend.observers.AuthObserver;
import com.vanderlelie.frontend.observers.OrderObserver;
import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class OrderView implements OrderObserver {
    @FXML
    private TabPane orderTabs;
    private OrderController orderController;
    private final int ORDER_TAB = 0;
    private final int PACKAGING_TAB = 1;

    public void initialize() {
        this.orderController = OrderController.getInstance();
        this.orderController.registerOrderObserver(this);

        selectTabByIndex(ORDER_TAB);
    }

    public void processOrderNumber() {
        swapToPackingScene();
    }

    private void swapToPackingScene() {
        selectTabByIndex(PACKAGING_TAB);
    }

    private void selectTabByIndex(int index) {
        SingleSelectionModel<Tab> selectionModel = orderTabs.getSelectionModel();

        selectionModel.select(index);
    }

    @Override
    public boolean update(Order order) {
        return true;
    }
}
