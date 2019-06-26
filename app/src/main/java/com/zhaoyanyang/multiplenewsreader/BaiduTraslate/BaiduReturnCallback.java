package com.zhaoyanyang.multiplenewsreader.BaiduTraslate;

public interface BaiduReturnCallback {
    void onFinish(String response);
    void onError(Exception e);
}
