package com.vanderlelie.frontend.views;

import com.vanderlelie.frontend.controllers.AuthController;
import com.vanderlelie.frontend.controllers.LogsController;
import com.vanderlelie.frontend.models.Log;
import com.vanderlelie.frontend.observers.LogObserver;

public class LogCentreView implements LogObserver {
    private LogsController logsController;

    public void initialize() {
        this.logsController = LogsController.getInstance();
        this.logsController.registerLogObserver(this);
    }


    @Override
    public boolean update(Log log) {
        return false;
    }
}
