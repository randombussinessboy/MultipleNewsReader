package com.zhaoyanyang.multiplenewsreader.BaiduTraslate;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

public class BaiduUtils {
    private static final String APP_ID = "20190625000310456";
    private static final String SECURITY_KEY = "a6z_WyMcMaTbBJOvj4YZ";
    public static void SendStirng(String content,final BaiduReturnCallback listener){
        Gson gson=new Gson();

        new Thread(()->{
            try {
                TransApi api=null;
                api=new TransApi(APP_ID, SECURITY_KEY);
                String newContent = api.getTransResult(content,"zh","en");

                    JSONObject jsonObject = new JSONObject(newContent);

                        JSONArray resultJsonArray = jsonObject.getJSONArray("trans_result");
                        //循环列表
                        for (int i = 0; i < resultJsonArray.length(); i++) {

                            JSONObject resultJsonObject = resultJsonArray.getJSONObject(i);
                            String rawString=resultJsonObject.getString("dst");
//                            rawString=rawString.replace("991","<p>");
//                            rawString=rawString.replace("992","</p>");
//                            rawString=rawString.replace("SRC ","src");
                            listener.onFinish(rawString);


                        }
            }catch (Exception e){
                listener.onError(e);
            }
        }).start();

    }
}
