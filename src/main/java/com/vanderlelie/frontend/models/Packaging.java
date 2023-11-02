package com.vanderlelie.frontend.models;

import java.util.UUID;

public class Packaging {
    private UUID id;
    private UUID packaging_type;
    private String name;
    private int width;
    private int height;
    private int length;
    private int amount;
    private String location;
    private int minimum_stock;
    private String company;


    public Packaging(){

    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPackaging_type() {
        return packaging_type;
    }

    public void setPackaging_type(UUID type) {
        this.packaging_type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public String getLocation() {
        return location;
    }

    public int getLength() {
        return length;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getMinimum_stock() {
        return minimum_stock;
    }

    public void setMinimum_stock(int minumum_stock) {
        this.minimum_stock = minumum_stock;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount){
                this.amount = amount;
            }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
