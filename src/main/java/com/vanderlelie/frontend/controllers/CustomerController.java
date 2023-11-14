package com.vanderlelie.frontend.controllers;

import com.vanderlelie.frontend.models.Customer;
import com.vanderlelie.frontend.models.responses.ClientResponse;
import com.vanderlelie.frontend.observers.ClientObserver;
import com.vanderlelie.frontend.services.RequestService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

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
        Arrays.sort(logs, Comparator.comparingInt(Customer::getCustomerNumber));

        this.clientResponse.setLogs(new ArrayList<>(Arrays.asList(logs)));
    }

    public void updateCustomerByQuery(String customerId, String columnName, String value) throws Exception {
        requestService.updateCustomer(customerId, columnName, value);
        searchCustomersByQuery("");
    }

    public void searchCustomersByCustomerNumber(int customerNumber) throws Exception {
        Customer[] logs = requestService.getCustomersByCustomerNumber(customerNumber);

        this.clientResponse.setLogs(new ArrayList<>(Arrays.asList(logs)));
    }

    public void registerClientObserver(ClientObserver clientView) {
        this.clientResponse.registerObserver(clientView);
    }
}
