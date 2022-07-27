package com.hao.elastic.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @description:
 * @author: Hao
 * @create: 2022-02-11 12:11
 **/
public class readFile
{
    public  String readFileContent(String fileName)
    {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null)
            {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                } catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
    public JSONArray jsonArray(String path)
    {
        String s=readFileContent(path);
        JSONObject jobj = JSON.parseObject(s);
        JSONArray jsonArray = jobj.getJSONArray("RECORDS");//构建JSONArray数组
        return jsonArray;
    }
}