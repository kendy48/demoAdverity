package com.adverity.demo.controller;

import com.adverity.demo.model.DataStatsRequest;
import com.adverity.demo.model.GroupField;
import com.adverity.demo.model.SortField;
import com.adverity.demo.service.DataStatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/v1/data")
@Tag(name = "kendy.alvarado@gmail.com", description = "Demo API to fetch data stats")
public class DataStatsController {

    @Autowired
    DataStatsService dataStatsService;

    @GetMapping("/")
    @Operation(summary = "Api to get Data Stats")
    public List getData(
            @Parameter(description = "When set data is filtered by dataSource")
            @RequestParam(required = false) String dataSource,

            @Parameter(description = "When set data is filtered by campaign")
            @RequestParam(required = false) String campaign,

            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "When set data is filtered by fromDate, valid format yyyy-MM-dd")
            @RequestParam(required = false) Date fromDate,

            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "When set data is filtered by toDate, valid format yyyy-MM-dd")
            @RequestParam(required = false) Date toDate,

            @Parameter(description = "When set data is filtered by minClicks")
            @RequestParam(required = false) Integer minClicks,

            @Parameter(description = "When set data is filtered by maxClicks")
            @RequestParam(required = false) Integer maxClicks,

            @Parameter(description = "When set data is filtered by minImpressions")
            @RequestParam(required = false) Integer minImpressions,

            @Parameter(description = "When set data is filtered by maxImpressions")
            @RequestParam(required = false) Integer maxImpressions,

            @Parameter(description = "When set data is filtered by minCTR")
            @RequestParam(required = false) Double minCTR,

            @Parameter(description = "When set data is filtered by maxCTR")
            @RequestParam(required = false) Double maxCTR,

            @Parameter(description = "default is not grouped, when set returns total clicks, total impressions and average of ctr")
            @RequestParam(required = false) List<GroupField> groupBy,

            @Parameter(description = "default is sorted by dataSource")
            @RequestParam(required = false) List<SortField> sortBy,

            @Parameter(description = "default is 0")
            @RequestParam(required = false) Integer page,

            @Parameter(description = "default is 10")
            @RequestParam(required = false) Long pageSize) throws Throwable {

        DataStatsRequest request = new DataStatsRequest(dataSource, campaign,
                fromDate, toDate, minClicks, maxClicks, minImpressions, maxImpressions,
                minCTR, maxCTR, groupBy, sortBy, page, pageSize);

        return dataStatsService.getStats(request);
    }

}
