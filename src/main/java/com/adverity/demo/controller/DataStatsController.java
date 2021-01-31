package com.adverity.demo.controller;

import com.adverity.demo.service.DataStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/data")
public class DataStatsController {

    @Autowired
    DataStatsService dataStatsService;


}
