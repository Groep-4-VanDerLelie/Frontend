package com.vanderlelie.frontend.models;

import java.util.UUID;

public class Customer {
    private UUID id;
    private String name;
    private Packaging basicPackaging;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Packaging getBasicPackaging() {
        return basicPackaging;
    }

    public void setBasicPackaging(Packaging basicPackaging) {
        this.basicPackaging = basicPackaging;
    }
}

