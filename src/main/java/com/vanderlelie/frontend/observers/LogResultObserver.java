package com.vanderlelie.frontend.observers;

import com.vanderlelie.frontend.models.Log;

import java.util.ArrayList;

public interface LogResultObserver {
    boolean update(ArrayList<Log> logs);
}
