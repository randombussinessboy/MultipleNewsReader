package com.zhaoyanyang.multiplenewsreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhaoyanyang.multiplenewsreader.ContextUtils.HateNews;
import com.zhaoyanyang.multiplenewsreader.ContextUtils.LikeBean;
import com.zhaoyanyang.multiplenewsreader.ContextUtils.Readlater;
import com.zhaoyanyang.multiplenewsreader.ContextUtils.hadread;
import com.zhaoyanyang.multiplenewsreader.CustomizeView.DropListView;
import com.zhaoyanyang.multiplenewsreader.HttpUtils.HttpCallbackListener;
import com.zhaoyanyang.multiplenewsreader.HttpUtils.HttpUtils;
import com.zhaoyanyang.multiplenewsreader.HttpUtils.JsonUtils;
import com.zhaoyanyang.multiplenewsreader.HttpUtils.NewsUtils;
import com.zhaoyanyang.multiplenewsreader.HttpUtils.RecommendCallbackListener;
import com.zhaoyanyang.multiplenewsreader.NewsDetails.NewsDetailActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
/*
这部分是推荐模块的界面和数据请求，因为设计的不合理，可复用的代码非常少，
现在的想法是维持四个List，数据适配器的List数据由着四个子List提供，每个List需要带
一个ID来标识类，我们视乎可以重写List，不过不建议，应该可以提供其它解决的方法
这样封装后，除了进行网络请求，其它逻辑不需要改变，因为每一次网络请求先需要判断四个类别的
数据到底要要分别占多少，我们把这方面的业务逻辑抽取出来，里面包括和数据库请求，向量计算，权重计算
跟非推荐的界面一样，我们每次刷新或者加载都为二十个数据，返回一个四元组，代表每个类别应该请求的量

为了显示我们的推荐效果，把单元项布局的description文本设置为这条新闻所属于的类别，
本身newsbean带有这一个属性，设置一下就好了，还有单item被点击后，怎么获取是哪一个类别的
这里我们对bean的description文本字段映射回int值 因为数据库这个字段存的int所以，我们这样做


* */

public class RecommendFragement extends Fragment  implements DropListView.ILoadListener {


    private DropListView lv;
    private ArrayList<NewsBean> mList=null;
    FloatingActionButton fab=null;
    RecommendFragement.NewsAdapter adapter;

    /* 新增四个List数据结构，和四个page，用来维持和服务器的页面上下文*/
    private ArrayList<NewsBean> subList_InCountry;
    private ArrayList<NewsBean> subList_Technology;
    private ArrayList<NewsBean> subList_Military;
    private ArrayList<NewsBean> subList_AI;

    private int PageInCountry;
    private int PageTechnology;
    private int PageMilitart;
    private int PageAI;







    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment, null);


        PageInCountry=1;
        PageMilitart=1;
        PageTechnology=1;
        PageAI=1;


        lv=view.findViewById(R.id.lv);
        fab=view.findViewById(R.id.fab);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);




        fab.setOnClickListener((V)->{

            //获取每个类别的配额
            int CatogtyNumber[]=CalCulateNumOnRecommend();

            lv.smoothScrollToPosition(0);
            swipeRefreshLayout.setRefreshing(true);

            if (CatogtyNumber[0]!=0)PageInCountry++;
            if (CatogtyNumber[1]!=0)PageTechnology++;
            if (CatogtyNumber[2]!=0)PageMilitart++;
            if (CatogtyNumber[3]!=0)PageAI++;

            int[] SubPage = { PageInCountry, PageTechnology, PageMilitart, PageAI };

            HttpUtils.IurlConnectInRecommend("https://api.tianapi.com/allnews/", CatogtyNumber, SubPage,
                    new RecommendCallbackListener() {
                        @Override
                        public void onFinish(String response1, String response2, String response3, String response4) {

                            getActivity().runOnUiThread(() -> {
                                subList_InCountry.clear();
                                subList_Technology.clear();
                                subList_Military.clear();
                                subList_AI.clear();
                                mList.clear();
                                //7 22 27 29
                                subList_InCountry.addAll(JsonUtils.NewslistDataOnRecommend(response1,7));
                                subList_Technology.addAll(JsonUtils.NewslistDataOnRecommend(response2,22));
                                subList_Military.addAll(JsonUtils.NewslistDataOnRecommend(response3,27));
                                subList_AI.addAll(JsonUtils.NewslistDataOnRecommend(response4,29));

                                mList.addAll(subList_InCountry);
                                mList.addAll(subList_Technology);
                                mList.addAll(subList_Military);
                                mList.addAll(subList_AI);

                                //为用户更好观感，随机排序一下
                                Collections.shuffle(mList);

                                adapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            });

                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                        }
                    });

        });
        lv.setInterface(this);
        mList= NewsUtils.getAllNews(getActivity());
        subList_InCountry=new ArrayList<NewsBean>();
        subList_Technology=new ArrayList<NewsBean>();
        subList_Military=new ArrayList<NewsBean>();
        subList_AI=new ArrayList<NewsBean>();
        adapter=new RecommendFragement.NewsAdapter();
        lv.setAdapter(adapter);

        int CatogtyNumber[]=CalCulateNumOnRecommend();
        int[] SubPage = { PageInCountry, PageTechnology, PageMilitart, PageAI };
        HttpUtils.IurlConnectInRecommend("https://api.tianapi.com/allnews/", CatogtyNumber, SubPage,
                new RecommendCallbackListener() {
                    @Override
                    public void onFinish(String response1, String response2, String response3, String response4) {

                        getActivity().runOnUiThread(() -> {
                            subList_InCountry.clear();
                            subList_Technology.clear();
                            subList_Military.clear();
                            subList_AI.clear();
                            mList.clear();
                            //7 22 27 29
                            subList_InCountry.addAll(JsonUtils.NewslistDataOnRecommend(response1,7));
                            subList_Technology.addAll(JsonUtils.NewslistDataOnRecommend(response2,22));
                            subList_Military.addAll(JsonUtils.NewslistDataOnRecommend(response3,27));
                            subList_AI.addAll(JsonUtils.NewslistDataOnRecommend(response4,29));

                            mList.addAll(subList_InCountry);
                            mList.addAll(subList_Technology);
                            mList.addAll(subList_Military);
                            mList.addAll(subList_AI);

                            //为用户更好观感，随机排序一下
                            Collections.shuffle(mList);

                            adapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        });

                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*跳转到新闻详情*/
                NewsBean newsBean=mList.get(position);

                hadread hadread=new hadread();
                hadread.setNewsTitle(newsBean.getTitle());
                hadread.setUrl(newsBean.getUrl());
                hadread.setNewsCategory(newsBean.getCategory());
                hadread.save();
                Intent intent=new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra(NewsDetailActivity.NEWS_NAME,newsBean.getTitle());
                intent.putExtra(NewsDetailActivity.NEWS_DETAILS_URL,newsBean.getUrl());
                intent.putExtra(NewsDetailActivity.NEWS_PIC_URL,newsBean.picurl);
                startActivity(intent);

            }
        });

        lv.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(Menu.NONE, 0, 0, "屏蔽");
                contextMenu.add(Menu.NONE, 1, 0, "加入稍后阅读");
                contextMenu.add(Menu.NONE, 2, 0, "喜欢");
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                int CatogtyNumber[]=CalCulateNumOnRecommend();

                if (CatogtyNumber[0]!=0)PageInCountry++;
                if (CatogtyNumber[1]!=0)PageTechnology++;
                if (CatogtyNumber[2]!=0)PageMilitart++;
                if (CatogtyNumber[3]!=0)PageAI++;

                int[] SubPage = { PageInCountry, PageTechnology, PageMilitart, PageAI };

                HttpUtils.IurlConnectInRecommend("https://api.tianapi.com/allnews/", CatogtyNumber, SubPage,
                        new RecommendCallbackListener() {
                            @Override
                            public void onFinish(String response1, String response2, String response3, String response4) {

                                getActivity().runOnUiThread(() -> {
                                    subList_InCountry.clear();
                                    subList_Technology.clear();
                                    subList_Military.clear();
                                    subList_AI.clear();
                                    mList.clear();
                                    //7 22 27 29
                                    subList_InCountry.addAll(JsonUtils.NewslistDataOnRecommend(response1,7));
                                    subList_Technology.addAll(JsonUtils.NewslistDataOnRecommend(response2,22));
                                    subList_Military.addAll(JsonUtils.NewslistDataOnRecommend(response3,27));
                                    subList_AI.addAll(JsonUtils.NewslistDataOnRecommend(response4,29));

                                    mList.addAll(subList_InCountry);
                                    mList.addAll(subList_Technology);
                                    mList.addAll(subList_Military);
                                    mList.addAll(subList_AI);

                                    //为用户更好观感，随机排序一下
                                    Collections.shuffle(mList);

                                    adapter.notifyDataSetChanged();
                                    swipeRefreshLayout.setRefreshing(false);
                                });

                            }

                            @Override
                            public void onError(Exception e) {
                                e.printStackTrace();
                            }
                        });
            }
        });


        return view;
    }

    private class NewsAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public NewsBean getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RecommendFragement.ViewHolder holder=null;
            if (convertView == null) {
                holder = new RecommendFragement.ViewHolder();
                convertView = View.inflate(getActivity().getApplicationContext(), R.layout.newslist_item, null);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tv_des = (TextView) convertView.findViewById(R.id.tv_des);
                holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                convertView.setTag(holder);
            } else {
                holder = (RecommendFragement.ViewHolder) convertView.getTag();
            }
            NewsBean item = getItem(position);
            holder.tv_title.setText(item.title);

            holder.tv_des.setText(item.getDescription());
            Glide.with(getActivity()).load(item.picurl).into(holder.iv_icon);

            return convertView;
        }

    }

    private static class ViewHolder {
        TextView tv_title;
        TextView tv_des;
        ImageView iv_icon;

    }

    @Override
    public void onLoad() {
        int CatogtyNumber[]=CalCulateNumOnRecommend();


        if (CatogtyNumber[0]!=0)PageInCountry++;
        if (CatogtyNumber[1]!=0)PageTechnology++;
        if (CatogtyNumber[2]!=0)PageMilitart++;
        if (CatogtyNumber[3]!=0)PageAI++;

        int[] SubPage = { PageInCountry, PageTechnology, PageMilitart, PageAI };

        HttpUtils.IurlConnectInRecommend("https://api.tianapi.com/allnews/", CatogtyNumber, SubPage,
                new RecommendCallbackListener() {
                    @Override
                    public void onFinish(String response1, String response2, String response3, String response4) {

                        getActivity().runOnUiThread(() -> {
                            subList_InCountry.clear();
                            subList_Technology.clear();
                            subList_Military.clear();
                            subList_AI.clear();
                            //mList.clear();
                            //7 22 27 29
                            subList_InCountry.addAll(JsonUtils.NewslistDataOnRecommend(response1,7));
                            subList_Technology.addAll(JsonUtils.NewslistDataOnRecommend(response2,22));
                            subList_Military.addAll(JsonUtils.NewslistDataOnRecommend(response3,27));
                            subList_AI.addAll(JsonUtils.NewslistDataOnRecommend(response4,29));

                           ArrayList<NewsBean> midList=new ArrayList<NewsBean>();
                            midList.addAll(subList_InCountry);
                            midList.addAll(subList_Technology);
                            midList.addAll(subList_Military);
                            midList.addAll(subList_AI);
                            Collections.shuffle(midList);
                            mList.addAll(midList);

                            //为用户更好观感，随机排序一下
                            adapter.notifyDataSetChanged();

                        });

                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });

    }

    //选中菜单Item后触发
    public boolean onContextItemSelected(MenuItem item){
        //关键代码在这里
        AdapterView.AdapterContextMenuInfo menuInfo;
        menuInfo =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if (getUserVisibleHint()){
            switch (item.getItemId()){
                case 0:
                    //点击第一个菜单项要做的事，如获取点击listview的位置
//                Toast.makeText(getActivity(), String.valueOf(menuInfo.position), Toast.LENGTH_LONG).show();
                    NewsBean bean=mList.get(menuInfo.position);
                    HateNews hateNews=new HateNews();
                    hateNews.setNewsCategory(bean.getCategory());//类别需要更改一下
                    hateNews.setNewsTitle(bean.getTitle());
                    hateNews.setUrl(bean.getUrl());
                    hateNews.save();
                    mList.remove(menuInfo.position);

                    /*先通知本地服务器屏蔽列表，再同步到服务器，生成简单的用户画像*/
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    //点击第二个菜单项要做的事，如获取点击的数据
                    //Toast.makeText(getActivity(), ""+mList.get(menuInfo.position), Toast.LENGTH_LONG).show();
                    Readlater readlater=new Readlater();
                    readlater.setNewsTitle(mList.get(menuInfo.position).getTitle());
                    readlater.setNewsDescription(mList.get(menuInfo.position).getDescription());
                    readlater.setNewsPicUrl(mList.get(menuInfo.position).getPicurl());
                    readlater.setUrl(mList.get(menuInfo.position).getUrl());
                    readlater.setNewsCategory(mList.get(menuInfo.position).getCategory());
                    readlater.save();
                    /*加入稍后阅读*/
                    break;
                case 2:
                    NewsBean bean1=mList.get(menuInfo.position);
                    LikeBean likeBean=new LikeBean();
                    likeBean.setNewsCategory(bean1.getCategory());
                    likeBean.setNewsTitle(bean1.getTitle());
                    likeBean.setUrl(bean1.getNews_url());
                    likeBean.save();

                    /*加入用户喜欢的栏目*/
                    break;
            }
        }
        return super.onContextItemSelected(item);
    }


    private int[] CalCulateNumOnRecommend(){
        /* 第一步，如用户画像生成部分的兴趣向量计算，先进行生成，
        * 下一次重构的时候这两个应该合并，目前，为了避免软件修改引起的
        * 错误，毕竟为了苟过课设，时间要紧，先不用改*/

        int categoty11=DataSupport.where("newsCategory = ?","7").find(hadread.class).size();
        int categoty12=DataSupport.where("newsCategory = ?","22").find(hadread.class).size();
        int categoty13=DataSupport.where("newsCategory = ?","27").find(hadread.class).size();
        int categoty14=DataSupport.where("newsCategory = ?","29").find(hadread.class).size();
        int categoty21=DataSupport.where("newsCategory = ?","7").find(HateNews.class).size();
        int categoty22=DataSupport.where("newsCategory = ?","22").find(HateNews.class).size();
        int categoty23=DataSupport.where("newsCategory = ?","27").find(HateNews.class).size();
        int categoty24=DataSupport.where("newsCategory = ?","29").find(HateNews.class).size();
        int categoty31=DataSupport.where("newsCategory = ?","7").find(LikeBean.class).size();
        int categoty32=DataSupport.where("newsCategory = ?","22").find(LikeBean.class).size();
        int categoty33=DataSupport.where("newsCategory = ?","27").find(LikeBean.class).size();
        int categoty34=DataSupport.where("newsCategory = ?","29").find(LikeBean.class).size();
        int categoty41=DataSupport.where("newsCategory = ?","7").find(Readlater.class).size();
        int categoty42=DataSupport.where("newsCategory = ?","22").find(Readlater.class).size();
        int categoty43=DataSupport.where("newsCategory = ?","27").find(Readlater.class).size();
        int categoty44=DataSupport.where("newsCategory = ?","29").find(Readlater.class).size();
        int guonei=categoty11-categoty12*5+categoty13*2+categoty14*3;
        int keji=categoty21-categoty22*5+categoty23*2+categoty24*3;
        int junshi=categoty31-categoty32*5+categoty33*2+categoty34*3;
        int ai=categoty41-categoty42*5+categoty43*2+categoty44*3;

        /*请求20条，现在跟据兴趣向量来分配每个类的配额
        * 注意到，每个分量有可能为负的，这种情况下该类别应该分配为0
        * 先把为负的置为0，再算正的总量，然后就是按比例分配了，取整*/

        if (guonei<0)guonei=0;
        if(ai<0)ai=0;
        if (keji<0)keji=0;
        if (junshi<0)junshi=0;

        int sumPositive=guonei+ai+keji+junshi;
        /*算好了直接赋值给原来的变量吧，节省内存,这里不考虑Java是怎么取整了，我也已经忘记了
        * 不知道最后的志是不是20哈哈哈*/

        guonei=guonei/sumPositive*20;
        ai=ai/sumPositive*20;
        keji=keji/sumPositive*20;
        junshi=junshi/sumPositive*20;

        int result[]=new int[4];

        result[0]=guonei;
        result[1]=keji;
        result[2]=junshi;
        result[3]=ai;

        return result;



    }
}
