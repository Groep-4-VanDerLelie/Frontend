package com.vanderlelie.frontend.shared;

import com.vanderlelie.frontend.models.Order;
import com.vanderlelie.frontend.observers.AuthObserver;
import com.vanderlelie.frontend.observers.OrderObserver;

public interface OrderObservable {
    void registerObserver(OrderObserver orderObserver);

    void unregisterObserver(OrderObserver orderObserver);

    void notifyObservers(Order order);
}
