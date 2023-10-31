package com.vanderlelie.frontend.shared;

import com.vanderlelie.frontend.models.Log;
import com.vanderlelie.frontend.models.Order;
import com.vanderlelie.frontend.observers.LogObserver;
import com.vanderlelie.frontend.observers.OrderObserver;

public interface LogObservable {
    void registerObserver(LogObserver logObserver);

    void unregisterObserver(LogObserver logObserver);

    void notifyObservers(Log log);
}
