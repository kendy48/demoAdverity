package com.adverity.demo.service;

import com.adverity.demo.domain.DataStats;
import com.adverity.demo.model.DataStatsRequest;
import com.adverity.demo.model.GroupField;
import com.adverity.demo.model.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataStatsService {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<DataStats> getStats(DataStatsRequest request) {
        List<Criteria> criteria = getSearchFilters(request);

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()]))),
                Aggregation.sort(Sort.Direction.ASC, getSortCriteria(request)),
                Aggregation.skip(request.getSkipRecords()),
                Aggregation.limit(request.getPageSize())

        );

        AggregationResults<DataStats> result = mongoTemplate.aggregate(agg, DataStats.class, DataStats.class);
        return result.getMappedResults();

    }

    public List<LinkedHashMap> getStatsGrouped(DataStatsRequest request) {
        List<Criteria> criteriaBeforeGroup = getSearchFiltersBeforeGroup(request);
        List<Criteria> criteriaAfterGroup = getSearchFiltersAfterGroup(request);

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(new Criteria().andOperator(criteriaBeforeGroup.toArray(new Criteria[criteriaBeforeGroup.size()]))),
                Aggregation.group(getGroupCriteria(request))
                        .sum("clicks").as("clicks")
                        .sum("impressions").as("impressions")
                        .avg("ctr").as("ctr"),
                Aggregation.match(new Criteria().andOperator(criteriaAfterGroup.toArray(new Criteria[criteriaAfterGroup.size()]))),
                Aggregation.sort(Sort.Direction.ASC, getGroupSortCriteria(request)),
                Aggregation.skip(request.getSkipRecords()),
                Aggregation.limit(request.getPageSize())
        );

        AggregationResults<LinkedHashMap> result = mongoTemplate.aggregate(agg, DataStats.class, LinkedHashMap.class);
        return result.getMappedResults();
    }

    private List<Criteria> getSearchFilters(DataStatsRequest request) {
        List<Criteria> criteria = getSearchFiltersBeforeGroup(request);
        criteria.addAll(getSearchFiltersAfterGroup(request));
        return criteria;
    }

    private List<Criteria> getSearchFiltersBeforeGroup(DataStatsRequest request) {
        List<Criteria> criteria = new ArrayList<>();
        //Create Filters
        if (request.getDataSource() != null && !request.getDataSource().isEmpty())
            criteria.add(new Criteria("dataSource").regex(request.getDataSource(), "i"));

        if (request.getCampaign() != null && !request.getCampaign().isEmpty())
            criteria.add(new Criteria("campaign").regex(request.getCampaign(), "i"));

        if (request.getFromDate() != null && request.getToDate() != null)
            criteria.add(new Criteria("date").gt(request.getFromDate()).lte(request.getToDate()));
        else if (request.getFromDate() != null)
            criteria.add(new Criteria("date").gt(request.getFromDate()));
        else if (request.getToDate() != null)
            criteria.add(new Criteria("date").lt(request.getToDate()));

        if (criteria.size() == 0)
            criteria.add(new Criteria("_id").exists(true));

        return criteria;
    }

    private List<Criteria> getSearchFiltersAfterGroup(DataStatsRequest request) {
        List<Criteria> criteria = new ArrayList<>();
        //Create Filters
        if (request.getMinClicks() != null && request.getMaxClicks() != null)
            criteria.add(new Criteria("clicks").gt(request.getMinClicks()).lte(request.getMaxClicks()));
        else if (request.getMinClicks() != null)
            criteria.add(new Criteria("clicks").gt(request.getMinClicks()));
        else if (request.getMaxClicks() != null)
            criteria.add(new Criteria("clicks").lt(request.getMaxClicks()));

        if (request.getMinImpressions() != null && request.getMaxImpressions() != null)
            criteria.add(new Criteria("impressions").gt(request.getMinImpressions()).lte(request.getMaxImpressions()));
        else if (request.getMinImpressions() != null)
            criteria.add(new Criteria("impressions").gt(request.getMinImpressions()));
        else if (request.getMaxImpressions() != null)
            criteria.add(new Criteria("impressions").lt(request.getMaxImpressions()));

        if (request.getMinCTR() != null && request.getMaxCTR() != null)
            criteria.add(new Criteria("ctr").gt(request.getMinCTR()).lte(request.getMaxCTR()));
        else if (request.getMinCTR() != null)
            criteria.add(new Criteria("ctr").gt(request.getMinCTR()));
        else if (request.getMaxCTR() != null)
            criteria.add(new Criteria("ctr").lt(request.getMaxCTR()));

        if (criteria.size() == 0)
            criteria.add(new Criteria("_id").exists(true));

        return criteria;
    }

    private String[] getSortCriteria(DataStatsRequest request) {
        if (request.getSortBy() == null || request.getSortBy().size() == 0)
            return new String[]{"dataSource"};
        else
            return request.getSortBy().stream()
                    .map(SortField::getDescription)
                    .collect(Collectors.toList())
                    .toArray(new String[request.getSortBy().size()]);
    }

    private String[] getGroupSortCriteria(DataStatsRequest request) {
        if (request.getSortBy() == null || request.getSortBy().size() == 0)
            return new String[]{"_id"};
        else if (request.getSortBy().size() == 1 && (request.getSortBy().contains(SortField.DATA_SOURCE))
                || request.getSortBy().contains(SortField.CAMPAIGN) || request.getSortBy().contains(SortField.DAILY))
            return new String[]{"_id"};
        else
            return request.getSortBy().stream()
                    .map(SortField::getGroupDescription)
                    .collect(Collectors.toList())
                    .toArray(new String[request.getSortBy().size()]);
    }

    private String[] getGroupCriteria(DataStatsRequest request) {
        if (request.getGroupBy() == null || request.getGroupBy().size() == 0)
            return new String[]{"dataSource"};
        else
            return request.getGroupBy().stream()
                    .map(GroupField::getDescription)
                    .collect(Collectors.toList())
                    .toArray(new String[request.getGroupBy().size()]);
    }
}
