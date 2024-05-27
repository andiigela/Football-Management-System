package com.football.dev.footballapp.services;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.football.dev.footballapp.models.ES.LeagueES;

import java.io.IOException;
import java.util.Map;

public interface ElasticSearchService {
    SearchResponse<Map> matchAllServices() throws IOException;
    SearchResponse<LeagueES> matchAllLeagueServices() throws IOException;
    SearchResponse<LeagueES> matchProductsWithName(String fieldValue) throws IOException;
}
