package com.zhaoyanyang.multiplenewsreader.ContextUtils;

import org.litepal.crud.DataSupport;

public class LikeBean extends DataSupport {
    private int id;
    private String newsTitle;
    private String newsDescription;
    private String newsPicUrl;
    private int newsCategory;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public String getNewsPicUrl() {
        return newsPicUrl;
    }

    public void setNewsPicUrl(String newsPicUrl) {
        this.newsPicUrl = newsPicUrl;
    }

    public int getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(int newsCategory) {
        this.newsCategory = newsCategory;
    }

}
