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

public static void urlNewsDetails(String newsurl,final HttpCallbackListener
        listener){
    //不要硬编码
    final String uri="https://api.tianapi.com/txapi/htmltext/"+"?&key="+"3a6fe062098c8b7b70ed4d7ddb45e429"+"&url="+newsurl;

    new Thread(()->{
        StringBuilder response=null;
        HttpURLConnection httpconn=null;
        try {

            URL url = new URL(uri);
            BufferedReader reader=null;

            httpconn = (HttpURLConnection) url.openConnection();
            httpconn.setRequestMethod("GET");

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


    public static void  IurlConnectInRecommend(String uri, int[] Catogory,int[] PageNumber, final RecommendCallbackListener
            listener){

        final String uri1=uri+"?&key="+"3a6fe062098c8b7b70ed4d7ddb45e429"+"&num="+Catogory[0]+"&col=7&page="+PageNumber[0];
        final String uri2=uri+"?&key="+"3a6fe062098c8b7b70ed4d7ddb45e429"+"&num="+Catogory[1]+"&col=22&page="+PageNumber[1];
        final String uri3=uri+"?&key="+"3a6fe062098c8b7b70ed4d7ddb45e429"+"&num="+Catogory[2]+"&col=27&page="+PageNumber[2];
        final String uri4=uri+"?&key="+"3a6fe062098c8b7b70ed4d7ddb45e429"+"&num="+Catogory[3]+"&col=29&page="+PageNumber[3];

        new Thread(()->{
            StringBuilder response1=null;
            StringBuilder response2=null;
            StringBuilder response3=null;
            StringBuilder response4=null;
            HttpURLConnection httpconn=null;
            try {

                URL url = new URL(uri1);
                BufferedReader reader=null;
                httpconn = (HttpURLConnection) url.openConnection();
                httpconn.setRequestMethod("GET");
                httpconn.setConnectTimeout(8000);
                httpconn.connect();
                if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader in = new InputStreamReader(httpconn.getInputStream(), "utf-8");
                    reader=new BufferedReader(in);
                    response1=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response1.append(line);
                    }
                    Log.d("wsl",response1.toString());
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

            try {

                URL url = new URL(uri2);
                BufferedReader reader=null;
                httpconn = (HttpURLConnection) url.openConnection();
                httpconn.setRequestMethod("GET");
                httpconn.setConnectTimeout(8000);
                httpconn.connect();
                if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader in = new InputStreamReader(httpconn.getInputStream(), "utf-8");
                    reader=new BufferedReader(in);
                    response2=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response2.append(line);
                    }
                    Log.d("wsl",response2.toString());
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

            try {

                URL url = new URL(uri3);
                BufferedReader reader=null;
                httpconn = (HttpURLConnection) url.openConnection();
                httpconn.setRequestMethod("GET");
                httpconn.setConnectTimeout(8000);
                httpconn.connect();
                if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader in = new InputStreamReader(httpconn.getInputStream(), "utf-8");
                    reader=new BufferedReader(in);
                    response3=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response3.append(line);
                    }
                    Log.d("wsl",response3.toString());
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

            try {

                URL url = new URL(uri4);
                BufferedReader reader=null;
                httpconn = (HttpURLConnection) url.openConnection();
                httpconn.setRequestMethod("GET");
                httpconn.setConnectTimeout(8000);
                httpconn.connect();
                if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader in = new InputStreamReader(httpconn.getInputStream(), "utf-8");
                    reader=new BufferedReader(in);
                    response4=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response4.append(line);
                    }
                    Log.d("wsl",response4.toString());
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
            if (listener!=null){
                listener.onFinish(response1.toString(),response2.toString(),response3.toString(),response4.toString());
            }

        }).start();



    }
}
