package com.vanderlelie.frontend.observers;

import com.vanderlelie.frontend.models.Order;

public interface OrderObserver {
    boolean update(Order order);
}
