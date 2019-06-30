package com.zhaoyanyang.multiplenewsreader.HttpUtils;

public interface RecommendCallbackListener {

    void onFinish(String response1,String response2,String response3,String response4);
    void onError(Exception e);
}
