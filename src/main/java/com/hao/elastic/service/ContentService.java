package com.hao.elastic.service;

import com.alibaba.fastjson.JSON;
import com.hao.elastic.pojo.Content;
import com.hao.elastic.util.ImageParseUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Hao
 * @create: 2022-02-11 11:10
 **/
@Service
public class ContentService
{


    @Autowired
    private RestHighLevelClient restHighLevelClient;



    public Boolean parseContent() throws IOException
    {
        List<Content> contents = new ImageParseUtil().parse();
        //1.把查询数据放入es
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("2m");
        for (Content content : contents)
        {
            bulkRequest.add(
                    new IndexRequest("images")
                            .source(JSON.toJSONString(content), XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }
    //2.获取数据实现搜索
    public List<Map<String,Object>> searchPage(String keyword,int pageNo,int pageSize) throws IOException
    {
        if(pageNo<=1)
        {
            pageNo=1;
        }
        //条件搜索
        SearchRequest searchRequest = new SearchRequest("images");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);

        //精准匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", keyword);
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        ArrayList<Map<String,Object>> list = new ArrayList();
        //解析结果
        for (SearchHit documentFields : searchResponse.getHits().getHits())
        {
            list.add(documentFields.getSourceAsMap());

        }
        return list;
    }


    //3.获取数据实现高亮
    public List<Map<String,Object>> highlightSearch(String keyword,int pageNo,int pageSize) throws IOException
    {
        if(pageNo<=1)
        {
            pageNo=1;
        }
        //条件搜索
        SearchRequest searchRequest = new SearchRequest("images");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);

        //精准匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", keyword);
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //高凉
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("name");
        highlightBuilder.requireFieldMatch(false); //多个高亮显示
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);

        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        ArrayList<Map<String,Object>> list = new ArrayList();
        //解析结果
        for (SearchHit documentFields : searchResponse.getHits().getHits())
        {
            // 解析高亮的字段
            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
            HighlightField name = highlightFields.get("name");
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            //将原来的字段换为我们高亮的字段
            if(name!=null){
                Text[] fragments = name.fragments();
                String newName = "";
                for(Text text : fragments){
                    newName += text;
                }
                sourceAsMap.put("name",newName);  //高亮替换掉原来的
            }

            list.add(sourceAsMap);
        }
        return list;
    }

}