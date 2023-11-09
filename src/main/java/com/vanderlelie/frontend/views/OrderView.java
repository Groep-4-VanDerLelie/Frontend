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
    private Label defaultPackagingHint;
    @FXML
    private ComboBox<Packaging> packagingComboBox;
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

    public void processOrderNumber() {
        try {
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
        } catch (IllegalArgumentException e) {
            Toast.show("\"" + orderId.getText() + "\" is not the correct format of an order ID,\ndouble check what you are scanning.", orderId, false);
        } catch (Exception e) {
            Toast.show("Couldn't find specified order.", orderId, false);
        }
    }

    public void processPackaging() throws Exception {
        productController.getPackaging(currentOrder.getProduct().toString());
        this.processOrder();
        swapToOrderScene();
    }

    private void preparePackagingOptions() throws Exception {
        Packaging[] allProductPackaging = productController.getPackaging(currentOrder.getProduct().toString());

        ObservableList<Packaging> packaging = FXCollections.observableArrayList(allProductPackaging);

        Product tempProduct = productController.getProduct(currentOrder.getProduct().toString());
        Packaging defaultPackaging = productController.getPackage(tempProduct.getPackaging().toString());

        String packagingSize = defaultPackaging.getWidth() + "x" + defaultPackaging.getHeight() + "x" + defaultPackaging.getLength();
        String productSize = tempProduct.getWidth() + "x" + tempProduct.getHeight() + "x" + tempProduct.getLength();
        defaultPackagingHint.setText("Selected: \"" + defaultPackaging.getName() + "\" - " + packagingSize + "\n" +
        "Product Selected: \"Hanger\" - " + productSize);
        packagingComboBox.setValue(defaultPackaging);
        packagingComboBox.setItems(packaging);
    }

    private void swapToPackingScene() {
        try {
            preparePackagingOptions();
        } catch (Exception e) {
            Toast.show("Couldn't process packaging for this product, try again.", packagingComboBox, false);
        }

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

    public void  processOrder() throws Exception {
        Packaging selectedPackaging = packagingComboBox.getValue();
        orderController.processOrder(currentOrder, selectedPackaging);
    }
}
