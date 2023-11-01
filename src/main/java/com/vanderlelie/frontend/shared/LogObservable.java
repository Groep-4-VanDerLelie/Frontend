package com.vanderlelie.frontend.shared;

import com.vanderlelie.frontend.models.Log;
import com.vanderlelie.frontend.observers.LogResultObserver;

import java.util.ArrayList;

public interface LogObservable {
    void registerObserver(LogResultObserver logObserver);

    void unregisterObserver(LogResultObserver logObserver);

    void notifyObservers(ArrayList<Log> logs);
}
