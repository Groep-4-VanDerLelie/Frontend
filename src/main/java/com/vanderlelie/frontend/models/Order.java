package com.vanderlelie.frontend.models;

import com.vanderlelie.frontend.observers.AuthObserver;
import com.vanderlelie.frontend.observers.OrderObserver;
import com.vanderlelie.frontend.shared.OrderObservable;

import java.util.ArrayList;
import java.util.List;

public class Order implements OrderObservable {
    private List<OrderObserver> observers = new ArrayList<>();
    @Override
    public void registerObserver(OrderObserver orderObserver) {
        this.observers.add(orderObserver);
    }

    @Override
    public void unregisterObserver(OrderObserver orderObserver) {
        this.observers.remove(orderObserver);
    }

    @Override
    public void notifyObservers(Order order) {
        ArrayList<Integer> observersToRemove = new ArrayList<>();

        for (int i = 0; i < observers.size(); i++) {
            OrderObserver observer = observers.get(i);
            boolean markedForRemoval = observer.update(order);
            if (markedForRemoval) {
                observersToRemove.add(i);
            }
        }

        for (int indexToRemove: observersToRemove) {
            observers.remove(indexToRemove);
        }
    }
}
