package com.vanderlelie.frontend.models;

import com.vanderlelie.frontend.observers.LogResultObserver;
import com.vanderlelie.frontend.shared.LogObservable;

import java.util.ArrayList;
import java.util.List;

public class Log {
    private Long logId;
    private Long orderId;
    private boolean isArchived;
    private User archiver;

    public Log(){

    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public User getArchiver() {
        return archiver;
    }

    public void setArchiver(User archiver) {
        this.archiver = archiver;
    }

    @Override
    public String toString() {
        String logUsername = "Luke";
        String logProductName = "Hema Large Packaging";
        String logDate = "(12:31pm 11 sept 2023)";

        return logUsername + " - " + logProductName + " " + logDate;
    }
}
