package com.vanderlelie.frontend.models;

import com.vanderlelie.frontend.observers.AuthObserver;
import com.vanderlelie.frontend.observers.OrderObserver;
import com.vanderlelie.frontend.shared.OrderObservable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Order implements OrderObservable {
    private UUID id;
    private User userObject;
    private UUID user;
    private UUID archiver;
    private String date;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUserObject() {
        return userObject;
    }

    public void setUserObject(User userObject) {
        this.userObject = userObject;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
