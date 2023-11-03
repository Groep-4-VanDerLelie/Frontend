package com.vanderlelie.frontend.models;

import com.vanderlelie.frontend.observers.OrderObserver;
import com.vanderlelie.frontend.shared.OrderObservable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Order implements OrderObservable {
    private UUID id;
    private UUID user;
    private UUID archiver;
    private UUID product;
    private UUID customer;
    private Date date;

    public Order() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUser() {
        return user;
    }

    public void setUser(UUID user) {
        this.user = user;
    }

    public UUID getArchiver() {
        return archiver;
    }

    public void setArchiver(UUID archiver) {
        this.archiver = archiver;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getProduct() {
        return product;
    }

    public void setProduct(UUID product) {
        this.product = product;
    }

    public UUID getCustomer() {
        return customer;
    }

    public void setCustomer(UUID customer) {
        this.customer = customer;
    }

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
