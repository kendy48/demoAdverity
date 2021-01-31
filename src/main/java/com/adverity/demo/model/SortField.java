package com.adverity.demo.model;

public enum SortField {
    DATA_SOURCE("dataSource"),
    CAMPAIGN("campaign"),
    DAILY("daily"),
    CLICKS("clicks"),
    IMPRESSIONS("impressions"),
    CTR("ctr");

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    SortField(String description) {
        this.description = description;
    }

}
