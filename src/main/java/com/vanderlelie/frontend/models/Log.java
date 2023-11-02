package com.vanderlelie.frontend.models;

import com.vanderlelie.frontend.observers.LogResultObserver;
import com.vanderlelie.frontend.shared.LogObservable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Log {
    private UUID id;
    private UUID order;
    private UUID archiver;
    private Order orderObject;
    private UUID customer;
    private UUID user;
    private User userObject;

    public Log(){

    }

    public UUID getId() {
        return id;
    }

    public UUID getOrder() {
        return order;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setOrder(UUID order) {
        this.order = order;
    }

    public UUID getArchiver() {
        return archiver;
    }

    public void setArchiver(UUID archiver) {
        this.archiver = archiver;
    }

    public Order getOrderObject() {
        return orderObject;
    }

    public void setOrderObject(Order orderObject) {
        this.orderObject = orderObject;
    }

    public UUID getCustomer() {
        return customer;
    }

    public void setCustomer(UUID customer) {
        this.customer = customer;
    }

    public UUID getUser() {
        return user;
    }

    public void setUser(UUID user) {
        this.user = user;
    }

    public User getUserObject() {
        return userObject;
    }

    public void setUserObject(User userObject) {
        this.userObject = userObject;
    }

    @Override
    public String toString() {
        String logUsername = "Luke";
        String logProductName = "Hema Large Packaging";
        String logDate = "(12:31pm 11 sept 2023)";

        return logUsername + " - " + logProductName + " " + logDate;
    }
}
