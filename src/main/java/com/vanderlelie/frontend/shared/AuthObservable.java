package com.vanderlelie.frontend.shared;

import com.vanderlelie.frontend.observers.AuthObserver;

public interface AuthObservable {
    void registerObserver(AuthObserver authObserver);

    void unregisterObserver(AuthObserver authObserver);

    void notifyObservers(boolean authorized);
}
