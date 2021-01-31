package com.adverity.demo.model;

public enum SortField {
    DATA_SOURCE("dataSource", "_id.dataSource"),
    CAMPAIGN("campaign", "_id.campaign"),
    DAILY("daily", "_id.daily"),
    CLICKS("clicks", "clicks"),
    IMPRESSIONS("impressions", "impressions"),
    CTR("ctr", "ctr");

    private String description;
    private String groupDescription;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    SortField(String description, String groupDescription) {
        this.description = description;
        this.groupDescription = groupDescription;
    }

}
