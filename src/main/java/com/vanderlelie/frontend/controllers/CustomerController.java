package com.vanderlelie.frontend.controllers;

import com.vanderlelie.frontend.models.Customer;
import com.vanderlelie.frontend.models.responses.ClientResponse;
import com.vanderlelie.frontend.observers.ClientObserver;
import com.vanderlelie.frontend.services.RequestService;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomerController {
    private ClientResponse clientResponse = new ClientResponse();
    private RequestService requestService = RequestService.getInstance();
    static CustomerController customerController;


    public static CustomerController getInstance(){
        if (customerController == null){
            customerController = new CustomerController();
        }
        return customerController;
    }

    public void searchCustomersByQuery(String query) throws Exception {
        Customer[] logs = requestService.getCustomers();

        this.clientResponse.setLogs(new ArrayList<>(Arrays.asList(logs)));
    }

    public void updateCustomerByQuery(String collumName, String value) throws Exception {
        Customer customer= requestService.updateCustomer(collumName, value);
        System.out.println(customer.toString());
        //this.clientResponse.notifyObservers(customer);
    }

    public void registerClientObserver(ClientObserver clientView) {
        this.clientResponse.registerObserver(clientView);
    }
}
