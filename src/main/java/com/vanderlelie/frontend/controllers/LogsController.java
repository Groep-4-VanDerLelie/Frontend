package com.vanderlelie.frontend.controllers;

import com.vanderlelie.frontend.models.Log;
import com.vanderlelie.frontend.models.Order;
import com.vanderlelie.frontend.observers.LogObserver;
import com.vanderlelie.frontend.observers.OrderObserver;

import java.util.HashMap;

public class LogsController {
    private Log log = new Log();
    static LogsController logsController;

    public static LogsController getInstance() {
        if (logsController == null) {
            logsController = new LogsController();
        }

        return logsController;
    }

    public void registerLogObserver(LogObserver logView) {
        this.log.registerObserver(logView);
    }
}
