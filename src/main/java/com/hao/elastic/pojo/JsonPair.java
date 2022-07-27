package com.hao.elastic.pojo;

import lombok.Data;

/**
 * @description:
 * @author: Hao
 * @create: 2022-03-08 17:35
 **/
@Data
public class JsonPair
{
    float score;
    String src;
    String name;

    public JsonPair(float score, String url, String name)
    {
        this.score = score;
        this.src = url;
        this.name = name;
    }
}