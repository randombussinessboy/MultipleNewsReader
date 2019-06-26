package com.zhaoyanyang.multiplenewsreader.NewsDetails;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.zhaoyanyang.multiplenewsreader.BaiduTraslate.BaiduReturnCallback;
import com.zhaoyanyang.multiplenewsreader.BaiduTraslate.BaiduUtils;
import com.zhaoyanyang.multiplenewsreader.BaiduTraslate.TransApi;
import com.zhaoyanyang.multiplenewsreader.ContextUtils.MyApplication;
import com.zhaoyanyang.multiplenewsreader.HttpUtils.HttpCallbackListener;
import com.zhaoyanyang.multiplenewsreader.HttpUtils.HttpUtils;
import com.zhaoyanyang.multiplenewsreader.HttpUtils.JsonUtils;
import com.zhaoyanyang.multiplenewsreader.NewsBean;
import com.zhaoyanyang.multiplenewsreader.R;


public class NewsDetailActivity extends AppCompatActivity {
    public static final String NEWS_NAME="news_name";
    public static final String NEWS_PIC_URL="news_pic_url";
    public static final String NEWS_DETAILS_URL="news_details_url";

    private String Ctime;
    private String Content=null;
    private String tranContent=null;
    private String enContent=null;
    private boolean translate=false;
    private boolean chorzn=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Intent intent=getIntent();
        String newsName=intent.getStringExtra(NEWS_NAME);
        String newsPic=intent.getStringExtra(NEWS_PIC_URL);
        String newsUrl=intent.getStringExtra(NEWS_DETAILS_URL);
        FloatingActionButton fab_trans=findViewById(R.id.fab_trans);



        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar=(CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        ImageView newsImage=findViewById(R.id.news_details);
//        TextView textViewContent=findViewById(R.id.textViewContent);
//        RichText.initCacheDir(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(newsName);
        Glide.with(this).load(newsPic).into(newsImage);
        WebView webView=findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        Log.d("网页","地址"+newsUrl);
        HttpUtils.urlNewsDetails(newsUrl, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                runOnUiThread(()->{
                    Log.d("新闻详情", response);
                    NewsBean newsBean= JsonUtils.NewsDetailsData(response);
                    Ctime=newsBean.getCtime();
                    Content=newsBean.getContent();
                    tranContent=newsBean.getContent();
//                    RichText.from(newsBean.getContent()).bind(this)
//                            .showBorder(false)
//                            .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
//                            .into(textViewContent);
                    String htmlText=getHtmlData(newsBean.getContent(),newsBean.getTitle(),newsBean.getCtime());
                    webView.loadData(htmlText,"text/html", "utf-8");
                });
            }


            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

        fab_trans.setOnClickListener((V)->{
            if (!translate){
//                tranContent=tranContent.replace("<p>","991");
//                tranContent=tranContent.replace("</p>","992");
                BaiduUtils.SendStirng(tranContent, new BaiduReturnCallback() {
                    @Override
                    public void onFinish(String response) {
                        runOnUiThread(()->{
                            enContent=response;
                            String htmlText=getHtmlData(response,newsName,Ctime);
                            webView.loadData(htmlText,"text/html", "utf-8");
                            chorzn=true;
                        });
                        translate=true;

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(MyApplication.getContext(),"翻译出错",Toast.LENGTH_SHORT).show();
                    }
                });

            }else {
                if (chorzn==false){
                    String htmlText=getHtmlData(enContent,newsName,Ctime);
                    webView.loadData(htmlText,"text/html", "utf-8");
                    chorzn=true;

                }else {
                    String htmlText=getHtmlData(Content,newsName,Ctime);
                    webView.loadData(htmlText,"text/html", "utf-8");
                    chorzn=false;
                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_details,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.share:
                Toast.makeText(NewsDetailActivity.this,"分享",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        RichText.clear(this);
    }
    public static final String getHtmlData(String bodyHTML,String title,String ctime) {
        String head = "<head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:100%; height:auto;} p{text-indent:2em;}img{margin-left: -2em;}</style> " +
                "<h2>"+title+"</h2>"+ctime+"</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
/*
 // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "";
    private static final String SECURITY_KEY = "";

    public static void main(String[] args) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        String query = "高度600米";
        System.out.println(api.getTransResult(query, "auto", "en"));
 */