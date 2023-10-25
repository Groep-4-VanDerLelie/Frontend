package com.vanderlelie.frontend.models;

import com.vanderlelie.frontend.observers.AuthObserver;
import com.vanderlelie.frontend.shared.AuthObservable;

import java.util.ArrayList;
import java.util.List;

public class User implements AuthObservable {
    private List<AuthObserver> observers = new ArrayList<>();

    @Override
    public void registerObserver(AuthObserver authObserver) {
        this.observers.add(authObserver);
    }

    @Override
    public void unregisterObserver(AuthObserver authObserver) {
        this.observers.remove(authObserver);
    }

    @Override
    public void notifyObservers(boolean authorized) {
        for (AuthObserver authObserver : observers) {
            authObserver.update(authorized);
        }
    }
}
