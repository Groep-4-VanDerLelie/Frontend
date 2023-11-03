package com.vanderlelie.frontend.controllers;

import com.vanderlelie.frontend.models.Packaging;
import com.vanderlelie.frontend.models.Product;
import com.vanderlelie.frontend.observers.OrderObserver;
import com.vanderlelie.frontend.services.RequestService;

public class ProductController {
    private Product product = new Product();
    static ProductController productController;
    private RequestService requestService;

    public static ProductController getInstance() {
        if (productController == null) {
            productController = new ProductController();
        }

        return productController;
    }
    private ProductController() {
        requestService = RequestService.getInstance();
    }

    public Product getProduct(String  id) throws Exception {
        return requestService.getProduct(id);
    }
    public Packaging[] getPackaging(String id) throws Exception {
        return requestService.getPackaging(id);
    }
    public Packaging getPackage(String id) throws Exception {
        return requestService.getPackage(id);
    }
}
