package com.vanderlelie.frontend.controllers;

import com.vanderlelie.frontend.models.Log;
import com.vanderlelie.frontend.models.Packaging;
import com.vanderlelie.frontend.models.responses.LogResponse;
import com.vanderlelie.frontend.models.responses.StockResponse;
import com.vanderlelie.frontend.observers.LogResultObserver;
import com.vanderlelie.frontend.observers.StockResultObserver;
import com.vanderlelie.frontend.services.RequestService;

import java.util.ArrayList;
import java.util.Arrays;

public class StockController {
    private StockResponse stockResponse = new StockResponse();
    private RequestService requestService = RequestService.getInstance();
    static StockController stockController;

    public static StockController getInstance() {
        if (stockController == null) {
            stockController = new StockController();
        }

        return stockController;
    }

    public void searchStocksByQuery(String query) throws Exception {
        Packaging[] logs = requestService.getAllPackaging();

        this.stockResponse.setLogs(new ArrayList<>(Arrays.asList(logs)));
    }

    public void registerLogObserver(StockResultObserver stockView) {
        this.stockResponse.registerObserver(stockView);
    }
}
