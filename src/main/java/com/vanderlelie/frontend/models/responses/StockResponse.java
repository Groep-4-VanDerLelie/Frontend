package com.vanderlelie.frontend.models.responses;

import com.vanderlelie.frontend.models.Log;
import com.vanderlelie.frontend.models.Packaging;
import com.vanderlelie.frontend.observers.LogResultObserver;
import com.vanderlelie.frontend.observers.StockResultObserver;
import com.vanderlelie.frontend.shared.LogObservable;
import com.vanderlelie.frontend.shared.StockObservable;

import java.util.ArrayList;
import java.util.List;

public class StockResponse implements StockObservable {
    private ArrayList<Packaging> packaging = new ArrayList<>();
    private List<StockResultObserver> observers = new ArrayList<>();
    @Override
    public void registerObserver(StockResultObserver stockObserver) {
        this.observers.add(stockObserver);
    }

    @Override
    public void unregisterObserver(StockResultObserver stockObserver) {
        this.observers.remove(stockObserver);
    }

    @Override
    public void notifyObservers(ArrayList<Packaging> packaging) {
        ArrayList<Integer> observersToRemove = new ArrayList<>();

        for (int i = 0; i < observers.size(); i++) {
            StockResultObserver observer = observers.get(i);
            boolean markedForRemoval = observer.update(packaging);
            if (markedForRemoval) {
                observersToRemove.add(i);
            }
        }

        for (int indexToRemove: observersToRemove) {
            observers.remove(indexToRemove);
        }
    }

    public ArrayList<Packaging> getLogs() {
        return packaging;
    }

    public void setLogs(ArrayList<Packaging> packaging) {
        this.packaging = packaging;

        this.notifyObservers(this.packaging);
    }
}
