package com.adverity.demo.model;

public enum GroupField {
    DATA_SOURCE("dataSource"),
    CAMPAIGN("campaign"),
    DAILY("daily");

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    GroupField(String description) {
        this.description = description;
    }

}
