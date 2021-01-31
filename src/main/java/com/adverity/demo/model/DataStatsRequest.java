package com.adverity.demo.model;

import com.adverity.demo.exception.ControllerException;
import java.util.Date;
import java.util.List;

public class DataStatsRequest {
    private String dataSource;
    private String campaign;
    private Date fromDate;
    private Date toDate;
    private Integer minClicks;
    private Integer maxClicks;
    private Integer minImpressions;
    private Integer maxImpressions;
    private Double minCTR;
    private Double maxCTR;
    private List<GroupField> groupBy;
    private List<SortField> sortBy;
    private Integer page;
    private Long pageSize;

    public DataStatsRequest() {
        this.page = 0;
        this.pageSize = 10l;
    }

    public DataStatsRequest(String dataSource, String campaign, Date fromDate, Date toDate, Integer minClicks,
                            Integer maxClicks, Integer minImpressions, Integer maxImpressions, Double minCTR,
                            Double maxCTR, List<GroupField> groupBy, List<SortField> sortBy, Integer page,
                            Long pageSize) throws Throwable {
        this();
        this.dataSource = dataSource;
        this.campaign = campaign;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.minClicks = minClicks;
        this.maxClicks = maxClicks;
        this.minImpressions = minImpressions;
        this.maxImpressions = maxImpressions;
        this.minCTR = minCTR;
        this.maxCTR = maxCTR;
        this.groupBy = groupBy;
        this.sortBy = sortBy;
        if(fromDate != null && toDate != null && fromDate.getTime() > toDate.getTime())
            throw new ControllerException.InvalidDateException();
        if(page != null)
            this.page = page;
        if(pageSize != null){
            if(pageSize > 100 || pageSize < 1)
                throw new ControllerException.InvalidPageSizeException();
            this.pageSize = pageSize;
        }

    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getMinClicks() {
        return minClicks;
    }

    public void setMinClicks(Integer minClicks) {
        this.minClicks = minClicks;
    }

    public Integer getMaxClicks() {
        return maxClicks;
    }

    public void setMaxClicks(Integer maxClicks) {
        this.maxClicks = maxClicks;
    }

    public Integer getMinImpressions() {
        return minImpressions;
    }

    public void setMinImpressions(Integer minImpressions) {
        this.minImpressions = minImpressions;
    }

    public Integer getMaxImpressions() {
        return maxImpressions;
    }

    public void setMaxImpressions(Integer maxImpressions) {
        this.maxImpressions = maxImpressions;
    }

    public Double getMinCTR() {
        return minCTR;
    }

    public void setMinCTR(Double minCTR) {
        this.minCTR = minCTR;
    }

    public Double getMaxCTR() {
        return maxCTR;
    }

    public void setMaxCTR(Double maxCTR) {
        this.maxCTR = maxCTR;
    }

    public List<GroupField> getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(List<GroupField> groupBy) {
        this.groupBy = groupBy;
    }

    public List<SortField> getSortBy() {
        return sortBy;
    }

    public void setSortBy(List<SortField> sortBy) {
        this.sortBy = sortBy;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getSkipRecords(){
        return  this.page * this.pageSize;
    }
}
