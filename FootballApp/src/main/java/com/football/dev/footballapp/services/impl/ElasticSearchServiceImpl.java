package com.football.dev.footballapp.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.football.dev.footballapp.models.ES.LeagueES;
import com.football.dev.footballapp.services.ElasticSearchService;
import com.football.dev.footballapp.util.ElasticSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;
    @Override
    public SearchResponse<Map> matchAllServices() throws IOException {
        Supplier<Query> supplier  = ElasticSearchUtil.supplier();
        SearchResponse<Map> searchResponse = elasticsearchClient.search(s->s.query(supplier.get()),Map.class);
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }

    @Override
    public SearchResponse<LeagueES> matchAllLeagueServices() throws IOException{
        Supplier<Query> supplier  = ElasticSearchUtil.supplier();
        SearchResponse<LeagueES> searchResponse = elasticsearchClient.search(s->s.index("league").query(supplier.get()),LeagueES.class);
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }

    @Override
    public SearchResponse<LeagueES> matchProductsWithName(String fieldValue) throws IOException{
        Supplier<Query> supplier  = ElasticSearchUtil.supplierWithNameField(fieldValue);
        SearchResponse<LeagueES> searchResponse = elasticsearchClient.search(s->s.index("league").query(supplier.get()),LeagueES.class);
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }
}
