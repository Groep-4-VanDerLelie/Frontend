package com.vanderlelie.frontend.models;

import com.vanderlelie.frontend.observers.AuthObserver;
import com.vanderlelie.frontend.shared.AuthObservable;

import java.util.ArrayList;
import java.util.List;

public class User implements AuthObservable {
    private String username;
    private List<AuthObserver> observers = new ArrayList<>();

    public User() {
        this.username = "John Doe";
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

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
        ArrayList<Integer> observersToRemove = new ArrayList<Integer>();

        for (int i = 0; i < observers.size(); i++) {
            AuthObserver observer = observers.get(i);
            boolean markedForRemoval = observer.update(authorized);
            if (markedForRemoval) {
                observersToRemove.add(i);
            }
        }

        for (int indexToRemove: observersToRemove) {
            observers.remove(indexToRemove);
        }
    }
}
