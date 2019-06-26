package com.zhaoyanyang.multiplenewsreader.HttpUtils;

import android.util.Log;
import android.widget.Toast;

import com.zhaoyanyang.multiplenewsreader.ContextUtils.MyApplication;
import com.zhaoyanyang.multiplenewsreader.NewsBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static ArrayList<NewsBean> NewslistData(String jsonstring) {
        ArrayList<NewsBean> newsList = new ArrayList<NewsBean>();
        try {
            JSONObject jsonObject = new JSONObject(jsonstring);
            int resultCode = jsonObject.getInt("code");
            if (resultCode == 200) {
                //获取新闻列表
                JSONArray resultJsonArray = jsonObject.getJSONArray("newslist");
                //循环列表
                for (int i = 0; i < resultJsonArray.length(); i++) {
                    NewsBean news = new NewsBean();
                    JSONObject resultJsonObject = resultJsonArray.getJSONObject(i);
                    news.setTitle(resultJsonObject.getString("title"));
                    Log.d("新闻",news.getTitle());
                    news.setCtime(resultJsonObject.getString("ctime"));
                    Log.d("新闻",news.getCtime());
                    news.setDescription(resultJsonObject.getString("description"));
                    Log.d("新闻",news.getDescription());
                    news.setPicurl(resultJsonObject.getString("picUrl"));
                    Log.d("新闻",news.getPicurl());
                    news.setUrl(resultJsonObject.getString("url"));
                    Log.d("新闻",news.getUrl());
                    newsList.add(news);
                }

                return newsList;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        Toast.makeText(MyApplication.getContext(),"未请求到有效数据",Toast.LENGTH_SHORT).show();
        return newsList=NewsUtils.getAllNews(MyApplication.getContext());
    }

    public static NewsBean NewsDetailsData(String jsonString){
        NewsBean newsBean=new NewsBean();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            int resultCode = jsonObject.getInt("code");
            if (resultCode == 200) {
                //获取新闻列表
                JSONArray resultJsonArray = jsonObject.getJSONArray("newslist");
                //循环列表
                for (int i = 0; i < resultJsonArray.length(); i++) {

                    JSONObject resultJsonObject = resultJsonArray.getJSONObject(i);
                    newsBean.setContent(resultJsonObject.getString("content"));
                    newsBean.setCtime(resultJsonObject.getString("ctime"));
                    newsBean.setTitle(resultJsonObject.getString("title"));

                }

                return newsBean;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return  newsBean;
    }

}
