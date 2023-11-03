package com.vanderlelie.frontend.observers;

import com.vanderlelie.frontend.models.Log;
import com.vanderlelie.frontend.models.responses.AllLogsResponse;
import com.vanderlelie.frontend.models.responses.LogResponse;

import java.util.ArrayList;

public interface LogResultObserver {
    boolean update(ArrayList<AllLogsResponse> logs);
}
