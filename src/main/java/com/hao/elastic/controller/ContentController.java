package com.hao.elastic.controller;

import com.hao.elastic.pojo.JsonPair;
import com.hao.elastic.service.ContentService;
import com.hao.elastic.service.HadoopSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Hao
 * @create: 2022-02-11 11:11
 **/
@RestController
public class ContentController
{
    @Autowired
    private ContentService contentService;

    @Autowired
    private HadoopSearchService hadoopSearchService;
    @GetMapping("/parse")
    public Boolean parse() throws IOException
    {
        return contentService.parseContent();
    }

    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    public List<Map<String, Object>> search(@PathVariable("keyword") String keyword,
                                            @PathVariable("pageNo") int pageNo,
                                            @PathVariable("pageSize") int PageSize) throws IOException
    {

        return contentService.highlightSearch(keyword, pageNo, PageSize);
    }

    @GetMapping("/hadoop/{keyword}")
    public List<JsonPair> findURL(@PathVariable("keyword") String keyword){
        return hadoopSearchService.findURL(keyword);
    }

}