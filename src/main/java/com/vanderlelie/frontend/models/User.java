package com.vanderlelie.frontend.models;

import com.vanderlelie.frontend.observers.AuthObserver;
import com.vanderlelie.frontend.shared.AuthObservable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User implements AuthObservable {
    private UUID id;
    private String lastName;
    private String firstName;
    private boolean admin;
    private List<AuthObserver> observers = new ArrayList<>();

    public User() {
        this.firstName = "John Doe";
    }

    public User(String username) {
        this.firstName = username;
    }

    public String getUsername() {
        return this.firstName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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
