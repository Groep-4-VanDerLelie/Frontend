package com.vanderlelie.frontend.models;

import com.vanderlelie.frontend.observers.LogObserver;
import com.vanderlelie.frontend.observers.OrderObserver;
import com.vanderlelie.frontend.shared.LogObservable;

import java.util.ArrayList;
import java.util.List;

public class Log implements LogObservable {
    private List<LogObserver> observers = new ArrayList<>();
    @Override
    public void registerObserver(LogObserver logObserver) {
        this.observers.add(logObserver);
    }

    @Override
    public void unregisterObserver(LogObserver logObserver) {
        this.observers.remove(logObserver);
    }

    @Override
    public void notifyObservers(Log log) {
        ArrayList<Integer> observersToRemove = new ArrayList<>();

        for (int i = 0; i < observers.size(); i++) {
            LogObserver observer = observers.get(i);
            boolean markedForRemoval = observer.update(log);
            if (markedForRemoval) {
                observersToRemove.add(i);
            }
        }

        for (int indexToRemove: observersToRemove) {
            observers.remove(indexToRemove);
        }
    }
}
