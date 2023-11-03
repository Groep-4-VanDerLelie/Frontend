package com.vanderlelie.frontend.shared;

import com.vanderlelie.frontend.models.Customer;
import com.vanderlelie.frontend.observers.ClientObserver;
import com.vanderlelie.frontend.observers.StockResultObserver;

import java.util.ArrayList;

public interface ClientObserable {

    void registerObserver(ClientObserver clientObserver);

    void unregisterObserver(ClientObserver clientObserver);

    void notifyObservers(ArrayList<Customer> customers);
}
