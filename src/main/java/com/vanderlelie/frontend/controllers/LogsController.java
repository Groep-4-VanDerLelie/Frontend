package com.vanderlelie.frontend.controllers;

import com.vanderlelie.frontend.models.Log;
import com.vanderlelie.frontend.models.responses.AllLogsResponse;
import com.vanderlelie.frontend.models.responses.LogResponse;
import com.vanderlelie.frontend.observers.LogResultObserver;
import com.vanderlelie.frontend.services.RequestService;

import java.util.ArrayList;
import java.util.Arrays;

public class LogsController {
    private LogResponse logResponse = new LogResponse();
    private RequestService requestService = RequestService.getInstance();
    static LogsController logsController;

    public static LogsController getInstance() {
        if (logsController == null) {
            logsController = new LogsController();
        }

        return logsController;
    }

    public void searchLogsByQuery(String query) throws Exception {
        AllLogsResponse[] logs = requestService.getLogs();

        this.logResponse.setLogs(new ArrayList<>(Arrays.asList(logs)));
    }

    public void registerLogObserver(LogResultObserver logView) {
        this.logResponse.registerObserver(logView);
    }
}
