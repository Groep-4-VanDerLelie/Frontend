package com.vanderlelie.frontend.models.responses;

import com.vanderlelie.frontend.models.Log;
import com.vanderlelie.frontend.observers.LogResultObserver;
import com.vanderlelie.frontend.shared.LogObservable;

import java.util.ArrayList;
import java.util.List;

public class LogResponse implements LogObservable {
    private ArrayList<AllLogsResponse> logs = new ArrayList<>();
    private List<LogResultObserver> observers = new ArrayList<>();
    @Override
    public void registerObserver(LogResultObserver logObserver) {
        this.observers.add(logObserver);
    }

    @Override
    public void unregisterObserver(LogResultObserver logObserver) {
        this.observers.remove(logObserver);
    }

    @Override
    public void notifyObservers(ArrayList<AllLogsResponse> logs) {
        ArrayList<Integer> observersToRemove = new ArrayList<>();

        for (int i = 0; i < observers.size(); i++) {
            LogResultObserver observer = observers.get(i);
            boolean markedForRemoval = observer.update(logs);
            if (markedForRemoval) {
                observersToRemove.add(i);
            }
        }

        for (int indexToRemove: observersToRemove) {
            observers.remove(indexToRemove);
        }
    }

    public ArrayList<AllLogsResponse> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<AllLogsResponse> logs) {
        this.logs = logs;

        this.notifyObservers(this.logs);
    }
}
