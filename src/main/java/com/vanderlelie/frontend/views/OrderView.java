package com.vanderlelie.frontend.views;

import com.vanderlelie.frontend.controllers.OrderController;
import com.vanderlelie.frontend.controllers.ProductController;
import com.vanderlelie.frontend.models.Order;
import com.vanderlelie.frontend.models.Packaging;
import com.vanderlelie.frontend.models.Product;
import com.vanderlelie.frontend.observers.OrderObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.UUID;

public class OrderView implements OrderObserver {
    @FXML
    private TabPane orderTabs;
    @FXML
    private TextField orderId;
    @FXML
    private ComboBox packagingComboBox;
    @FXML
    private Packaging starting_value;
    private OrderController orderController;
    private ProductController productController;
    private final int ORDER_TAB = 0;
    private final int PACKAGING_TAB = 1;
    private Order currentOrder = new Order();

    public void initialize() {
        this.orderController = OrderController.getInstance();
        this.orderController.registerOrderObserver(this);
        this.productController = ProductController.getInstance();

        selectTabByIndex(ORDER_TAB);
    }

    public void processOrderNumber() throws Exception {
        Order[] orders = orderController.getOrder();
        for (Order order : orders) {
            if (order.getId().equals(UUID.fromString(orderId.getText()))) {
                System.out.println("Order found");
                currentOrder.setId(order.getId());
                currentOrder.setUser(order.getUser());
                currentOrder.setProduct(order.getProduct());
                currentOrder.setCustomer(order.getCustomer());
                currentOrder.setDate(order.getDate());
            }
        }
        swapToPackingScene();
    }

    public void processPackaging() throws Exception {
        productController.getPackaging(currentOrder.getProduct().toString());
        this.processOrder();
        swapToOrderScene();
    }

    private void swapToPackingScene() {
        selectTabByIndex(PACKAGING_TAB);
    }
    private void swapToOrderScene() {
        selectTabByIndex(ORDER_TAB);
    }

    private void selectTabByIndex(int index) {
        SingleSelectionModel<Tab> selectionModel = orderTabs.getSelectionModel();

        selectionModel.select(index);
    }

    @Override
    public boolean update(Order order) {
        return true;
    }

    public void onComboBoxShown(Event event) throws Exception {
        ObservableList<Packaging> packaging = FXCollections.observableArrayList(productController.getPackaging(currentOrder.getProduct().toString()));
        ObservableList<String> packagingString = FXCollections.observableArrayList();
        for (Packaging currentPackaging : packaging) {
            packagingString.add(currentPackaging.getName());

        }
        Product tempProduct = productController.getProduct(currentOrder.getProduct().toString());
        starting_value = productController.getPackage(tempProduct.getPackaging().toString());
        packagingComboBox.setValue(starting_value.getName());
        packagingComboBox.setItems(packagingString);
    }

    public void  processOrder() throws Exception {
        ObservableList<Packaging> packaging = FXCollections.observableArrayList(productController.getPackaging(currentOrder.getProduct().toString()));
        for (Packaging currentPackaging : packaging){
            if (currentPackaging.getName().equals(packagingComboBox.getValue())){
                orderController.processOrder(currentOrder, currentPackaging);
            }
        }
    }
}
