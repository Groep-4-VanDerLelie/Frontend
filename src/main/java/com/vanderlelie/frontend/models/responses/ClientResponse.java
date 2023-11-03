package com.vanderlelie.frontend.models.responses;

import com.vanderlelie.frontend.models.Customer;
import com.vanderlelie.frontend.observers.ClientObserver;
import com.vanderlelie.frontend.observers.StockResultObserver;
import com.vanderlelie.frontend.shared.ClientObserable;

import java.util.ArrayList;
import java.util.List;

public class ClientResponse implements ClientObserable {

    private ArrayList<Customer> customers = new ArrayList<>();

    private List<ClientObserver> observers = new ArrayList<>();


    @Override
    public void registerObserver(ClientObserver clientObserver) {
        observers.add(clientObserver);
    }

    @Override
    public void unregisterObserver(ClientObserver clientObserver) {
        observers.remove(clientObserver);
    }

    @Override
    public void notifyObservers(ArrayList<Customer> customers) {
        ArrayList<Integer> observersToRemove = new ArrayList<>();

        for (int i = 0; i < observers.size(); i++) {
            ClientObserver observer = observers.get(i);
            boolean markedForRemoval = observer.update(customers);
            if (markedForRemoval) {
                observersToRemove.add(i);
            }
        }

        for (int indexToRemove: observersToRemove) {
            observers.remove(indexToRemove);
        }
    }

    public ArrayList<Customer> getLogs() {
        return this.customers;
    }

    public void setLogs(ArrayList<Customer> customers) {
        this.customers = customers;

        this.notifyObservers(this.customers);
    }
}
