package com.adverity.demo.repository;

import com.adverity.demo.domain.DataStats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataStatsRepository extends MongoRepository<DataStats, String> {

}
