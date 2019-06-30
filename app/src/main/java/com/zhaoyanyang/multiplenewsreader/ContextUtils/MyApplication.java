package com.zhaoyanyang.multiplenewsreader.ContextUtils;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate(){
        context=getApplicationContext();
        LitePal.initialize(context);
        super.onCreate();
    }
    public static Context getContext(){
        return context;
    }
}
