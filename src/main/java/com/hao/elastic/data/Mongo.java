package com.hao.elastic.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hao.elastic.pojo.Content;
import com.hao.elastic.util.readFile;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;



public class Mongo {
    private static MongoClient mongoClient;

    public static void main(String [] args) throws IOException {
        mongoClient = MongoClients.create("mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+1.2.2a/cs242");
        MongoDatabase mongoDatabase = mongoClient.getDatabase("cs242");
        mongoDatabase.createCollection("inverted");

        //获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("inverted");


//        readFile readFile = new readFile();
//        JSONArray jsonArray = readFile.jsonArray("/Users/oushigou/Downloads/242project/elasticsearch/cat.json");
//        int index = 1;
//        for (Object o : jsonArray) {
//            JSONObject key = (JSONObject) o;
//            String src = (String) key.get("src");
//            String name = (String) key.get("name");
//            //向集合中插入文档
//            Document document = new Document("index", index).
//                    append("url", src).
//                    append("name", name);
//            List<Document> documents = new ArrayList<>();
//            documents.add(document);
//            collection.insertMany(documents);
//            index++;
//            System.out.println("文档插入成功");
//        }

        String filePath = "/Users/oushigou/Downloads/242project/elasticsearch/output.txt";
        FileInputStream fin = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(reader);
        String strTmp = "";
        int index = 1;
        while((strTmp = buffReader.readLine())!=null){
            Document document = new Document();
            StringTokenizer st = new StringTokenizer(strTmp);
            while(st.hasMoreTokens()){
                if(index %2 != 0){
                    document.append("word",st.nextToken());
                }else{
                    document.append("index",st.nextToken());
                }
                index++;
            }
            List<Document> documents = new ArrayList<>();
            documents.add(document);
            collection.insertMany(documents);
            System.out.println("文档插入成功");
        }
        buffReader.close();

    }
}
