package com.vanderlelie.frontend.controllers;

import com.vanderlelie.frontend.models.Order;
import com.vanderlelie.frontend.models.Packaging;
import com.vanderlelie.frontend.models.Product;
import com.vanderlelie.frontend.observers.OrderObserver;
import com.vanderlelie.frontend.services.RequestService;

public class OrderController {
    private Order order = new Order();
    static OrderController orderController;
    private RequestService requestService;

    public static OrderController getInstance() {
        if (orderController == null) {
            orderController = new OrderController();
        }

        return orderController;
    }
    private OrderController() {
        requestService = RequestService.getInstance();
    }

    public Order[] getOrder() throws Exception {
        return requestService.getOrder();
    }
    public void processOrder(Order order, Packaging packaging) throws Exception {
        requestService.procesOrder(packaging, order.getId().toString());
    }

    public void registerOrderObserver(OrderObserver orderView) {
        this.order.registerObserver(orderView);
    }
}
