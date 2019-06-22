package com.zhaoyanyang.multiplenewsreader.NewsDetails;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.zhaoyanyang.multiplenewsreader.R;

public class NewsDetailActivity extends AppCompatActivity {
    public static final String NEWS_NAME="news_name";
    public static final String NEWS_PIC_URL="news_pic_url";
    public static final String NEWS_DETAILS_URL="news_details_url";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Intent intent=getIntent();
        String newsName=intent.getStringExtra(NEWS_NAME);
        String newsPic=intent.getStringExtra(NEWS_PIC_URL);
        String newsUrl=intent.getStringExtra(NEWS_DETAILS_URL);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar=(CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        ImageView newsImage=findViewById(R.id.news_details);

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
        webView.loadUrl(newsUrl);

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
}
