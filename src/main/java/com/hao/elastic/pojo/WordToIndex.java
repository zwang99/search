package com.hao.elastic.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @description:
 * @author: Hao
 * @create: 2022-03-08 09:33
 **/
@Document(collection="WordToIndex")
@Data
public class WordToIndex
{
    @Id
    private int id;
    private String word;
    private String index;
}