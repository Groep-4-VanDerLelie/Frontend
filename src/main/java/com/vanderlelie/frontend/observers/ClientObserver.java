package com.vanderlelie.frontend.observers;

import com.vanderlelie.frontend.models.Customer;

import java.util.ArrayList;

public interface ClientObserver {
    boolean update(ArrayList<Customer> customers);
}
