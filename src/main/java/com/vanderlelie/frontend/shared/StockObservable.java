package com.vanderlelie.frontend.shared;

import com.vanderlelie.frontend.models.Log;
import com.vanderlelie.frontend.models.Packaging;
import com.vanderlelie.frontend.observers.LogResultObserver;
import com.vanderlelie.frontend.observers.StockResultObserver;

import java.util.ArrayList;

public interface StockObservable {
    void registerObserver(StockResultObserver stockObserver);

    void unregisterObserver(StockResultObserver stockObserver);

    void notifyObservers(ArrayList<Packaging> packaging);
}
