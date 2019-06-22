package com.zhaoyanyang.multiplenewsreader.HttpUtils;

import android.content.Context;

import com.zhaoyanyang.multiplenewsreader.NewsBean;

import java.util.ArrayList;

public class NewsUtils {
    /**
     * @param context 上下文环境
     * @return 新闻集合
     */
    public static ArrayList<NewsBean> getAllNews(Context context) {
        ArrayList<NewsBean> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            NewsBean newsBean1 = new NewsBean();
            newsBean1.title = "无论谁当美国下届总统 现在的中国只认一个道理";
            //newsBean1.des = "7月5-6日，武汉普降暴雨-大暴雨，中心城区、蔡甸部分地区出现特大暴雨。江夏大道汤逊湖大桥段，被湖水冲破的路障。记者贾代腾飞 陈卓摄";
            //newsBean1.icon = context.getResources().getDrawable(R.drawable.wuhan);
            newsBean1.news_url = "http://slide.news.sina.com.cn/s/slide_1_2841_101020.html#p=1";
            newsBean1.setPicurl("https://img3.utuku.china.com/300x0/mili/20190619/fce502b7-c3f6-4481-bb61-0148783dfb6f.jpg");
            arrayList.add(newsBean1);

            NewsBean newsBean2 = new NewsBean();
            newsBean2.title = "外交部驻港公署连续两天提出严正交涉";
            //newsBean2.des = "因强降雨造成内涝，安徽省芜湖市芜湖县花桥镇鳄鱼湖农庄所养鳄鱼逃跑至附近农田。。据悉，溜出来的鳄鱼为散养的扬子鳄，比较温驯。初步预计有三四十条，具体数量未统计，其中最大的约1.8米长。图为网友拍摄到的农田中的鳄鱼。";
            //newsBean2.icon = context.getResources().getDrawable(R.drawable.eyu);
            newsBean2.news_url = "http://slide.news.sina.com.cn/s/slide_1_2841_101024.html#p=1";
            newsBean2.setPicurl("https://img3.utuku.china.com/300x0/mili/20190618/fbfe5bf9-06b4-4f08-93e4-cc61209fac96.jpg");

            arrayList.add(newsBean2);

            NewsBean newsBean3 = new NewsBean();
            newsBean3.title = "美国飞机跑到解放军吉布提基地上空低空飞行却恶人先告状";
            //newsBean3.des = "近日，持续强降雨，导致地势低洼的南京理工大学出现严重积水。这一组几张照片，南理工恍若童话世界中。网友：泡在水中的南理工，也可以倔强地刷出颜值新高度。";
            //newsBean3.icon = context.getResources().getDrawable(R.drawable.qihuan);
            newsBean3.news_url = "http://slide.news.sina.com.cn/s/slide_1_2841_101010.html#p=1";
            newsBean3.setPicurl("https://img3.utuku.china.com/300x0/mili/20190618/b2c654f0-b822-4f71-8ff1-697862fd7343.jpg");
            arrayList.add(newsBean3);
        }
        return arrayList;
    }
}
