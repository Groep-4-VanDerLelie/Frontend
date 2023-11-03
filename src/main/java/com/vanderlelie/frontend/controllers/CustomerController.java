package com.vanderlelie.frontend.controllers;

import com.vanderlelie.frontend.models.Customer;
import com.vanderlelie.frontend.services.RequestService;

public class CustomerController {
    private Customer customer;

    private RequestService requestService;
    static CustomerController customerController;

    public CustomerController() {
        requestService = RequestService.getInstance();
    }

    public static CustomerController getInstance(){
        if (customerController == null){
            customerController = new CustomerController();
        }
        return customerController;
    }


    public Customer[] getCustomers() throws Exception {
        return requestService.getCustomers();
    }
}
