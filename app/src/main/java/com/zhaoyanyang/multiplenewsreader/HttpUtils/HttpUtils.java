package com.zhaoyanyang.multiplenewsreader.HttpUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpUtils {
    public static void  urlConnect(String uri, String index,final HttpCallbackListener
                            listener,int page) {

        final String uri1=uri+"?&key="+"3a6fe062098c8b7b70ed4d7ddb45e429"+"&num=20&col="+index+"&page="+page;

        new Thread(()->{
            StringBuilder response=null;
            HttpURLConnection httpconn=null;
            try {

                URL url = new URL(uri1);
                BufferedReader reader=null;

                httpconn = (HttpURLConnection) url.openConnection();
                httpconn.setRequestMethod("GET");

                httpconn.setRequestProperty("key", "3a6fe062098c8b7b70ed4d7ddb45e429");
                httpconn.setConnectTimeout(8000);

                httpconn.connect();
                Log.d("wsl","okpre");
                if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader in = new InputStreamReader(httpconn.getInputStream(), "utf-8");
                    reader=new BufferedReader(in);
                    response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    Log.d("wsl",response.toString());


                }
                if (listener!=null){
                    listener.onFinish(response.toString());
                }


            } catch (Exception e) {
                Log.d("NetAccessYang", e.toString());
                e.printStackTrace();
                if (listener!=null){
                    listener.onError(e);
                }

            }
            finally {
                if (httpconn!=null){
                    httpconn.disconnect();
                }
            }
        }).start();

    }



}
