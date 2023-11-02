package com.vanderlelie.frontend.observers;

import com.vanderlelie.frontend.models.Log;
import com.vanderlelie.frontend.models.Packaging;

import java.util.ArrayList;

public interface StockResultObserver {
    boolean update(ArrayList<Packaging> packaging);
}
