package com.hao.elastic.service;

import com.hao.elastic.pojo.JsonPair;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class HadoopSearchService {
    private static MongoClient mongoClient;
    public List<JsonPair> findURL(String keyword){

        mongoClient = MongoClients.create("mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+1.2.2a/cs242");
        MongoDatabase mongoDatabase = mongoClient.getDatabase("cs242");

        MongoCollection<Document> collectionInverted = mongoDatabase.getCollection("inverted");
        MongoCollection<Document> collectionJson = mongoDatabase.getCollection("all_files");

        FindIterable<Document> documentFindIterable = collectionInverted.find(Filters.eq("word", keyword));
        MongoCursor<Document> documentMongoCursor = documentFindIterable.iterator();
        String info = new String();
        int count = 100;
        while(documentMongoCursor.hasNext()){
            Document document = documentMongoCursor.next();
            info = document.getString("index");
        }
        String[] pairs = info.split(";");
//        PriorityQueue<JsonPair> pq = new PriorityQueue<>((o1, o2) -> o1.getScore() >= o2.getScore() ? -1 : 1);
        List<JsonPair> list = new ArrayList<>();
        for (String pair : pairs)
        {
            String[] element = pair.split(":");
            String index = element[0];
            float cnt = Float.valueOf(element[1]);
            documentFindIterable = collectionJson.find(Filters.eq("index", Integer.valueOf(index)));
            documentMongoCursor = documentFindIterable.iterator();
            while (documentMongoCursor.hasNext())
            {
                Document document = documentMongoCursor.next();
                String url = document.getString("url");
                String name = document.getString("name");
//                pq.offer(new JsonPair(cnt / name.length(), url, name));
                list.add(new JsonPair(cnt / name.length(), url, name));
            }
            if (count-- == 0)
                break;
        }
        Collections.sort(list,((o1, o2) ->o1.getScore() >= o2.getScore() ? -1 : 1 ));

        return list;

    }
}
