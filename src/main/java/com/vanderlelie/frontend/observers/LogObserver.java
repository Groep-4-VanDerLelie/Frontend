package com.vanderlelie.frontend.observers;

import com.vanderlelie.frontend.models.Log;
import com.vanderlelie.frontend.models.Order;

public interface LogObserver {
    boolean update(Log log);
}
