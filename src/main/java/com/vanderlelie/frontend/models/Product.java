package com.vanderlelie.frontend.models;

import java.util.UUID;

public class Product {
    private UUID id;
    private UUID product_type;
    private UUID order;
    private UUID packaging;
    private int width;
    private int length;
    private int height;

    public Product(){

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID productId) {
        this.id = productId;
    }

    public UUID getProduct_type() {
        return product_type;
    }

    public void setProduct_type(UUID packagingId) {
        this.product_type = packagingId;
    }

    public UUID getOrder() {
        return order;
    }

    public void setOrder(UUID order) {
        this.order = order;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public UUID getPackaging() {
        return packaging;
    }

    public void setPackaging(UUID packaging) {
        this.packaging = packaging;
    }

}
