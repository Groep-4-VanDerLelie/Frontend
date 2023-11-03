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
    private UUID customer;
    private UUID user;

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
}
