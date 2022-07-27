package com.hao.elastic.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hao.elastic.pojo.Content;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: Hao
 * @create: 2022-02-11 10:28
 **/
@Component
public class ImageParseUtil
{


    public  List<Content> parse() throws IOException
    {
        readFile readFile=new readFile();
        JSONArray jsonArray=readFile.jsonArray("/Users/oushigou/Downloads/242project/elasticsearch/cat.json");
        List<Content> images = new ArrayList();
        Set<String> set=new HashSet<>();
        for (Object o : jsonArray) {
            JSONObject key = (JSONObject) o;
            String src = (String) key.get("src");
            System.out.println(src);
            if(set.contains(src))
            {
                System.out.println("------"+src);
                continue;
            }
            else
                set.add((String)key.get("src"));
            String name = (String) key.get("name");
            String tag = (String) key.get("tags");
            images.add(new Content(name,src,tag));
        }
        return images;

//        获取请求 https://search.jd.com/Search?keyword=java&enc=utf-8
//        获得请求，不能获得ajax，除非模拟浏览器
//        String url = "https://search.jd.com/Search?keyword=" + keywords + "&enc=utf-8";
//        解析网页(Jsoup返回Document就是js页面)
//        Document document = Jsoup.parse(new URL(url), 30000);
//        Element element = document.getElementById("J_goodsList");
//        Elements elements = element.getElementsByTag("li");

//        List<Content> goodsList = new ArrayList();
//        for (Element el : elements)
//        {
//            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
//            String price = el.getElementsByClass("p-price").eq(0).text();
//            String title = el.getElementsByClass("p-name").eq(0).text();
//            goodsList.add(new Content(title, img, price));
//        }
//        return goodsList;
    }
}