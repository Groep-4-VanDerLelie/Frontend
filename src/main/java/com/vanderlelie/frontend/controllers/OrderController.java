package com.vanderlelie.frontend.controllers;

import com.vanderlelie.frontend.models.Order;
import com.vanderlelie.frontend.observers.OrderObserver;

public class OrderController {
    private Order order = new Order();
    static OrderController orderController;

    public static OrderController getInstance() {
        if (orderController == null) {
            orderController = new OrderController();
        }

        return orderController;
    }

    public void registerOrderObserver(OrderObserver orderView) {
        this.order.registerObserver(orderView);
    }
}
