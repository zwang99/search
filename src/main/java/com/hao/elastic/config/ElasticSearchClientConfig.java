package com.hao.elastic.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: Hao
 * @create: 2022-02-11 11:29
 **/
@Configuration
public class ElasticSearchClientConfig
{
    // 注册 rest客户端
    @Bean
    public RestHighLevelClient restHighLevelClient()
    {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")
                )
        );
        return client;
    }
}