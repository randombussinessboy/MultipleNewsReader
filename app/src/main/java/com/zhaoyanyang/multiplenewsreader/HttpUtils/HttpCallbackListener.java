package com.zhaoyanyang.multiplenewsreader.HttpUtils;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
