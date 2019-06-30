package com.zhaoyanyang.multiplenewsreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.zhaoyanyang.multiplenewsreader.NewsDetails.NewsDetailActivity;

import java.util.ArrayList;


public class TestFragment extends Fragment   implements DropListView.ILoadListener{
    String text;
    private DropListView lv;
    private ArrayList<NewsBean> mList=null;
    FloatingActionButton fab=null;
    private int page;
    NewsAdapter adapter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment, null);
        /*TextView textView = (TextView) view.findViewById(R.id.text_1);
        Bundle args = getArguments();
        if (args != null) {
            text = args.getString("text");
        }
        textView.setText(text);*/
        Log.d("主线程",String.valueOf(Thread.currentThread().getId()));
        Bundle args = getArguments();
        if (args != null) {
            text = args.getString("text");
            Log.d("类别号", text);
        }
        page=1;
        lv=view.findViewById(R.id.lv);
        fab=view.findViewById(R.id.fab);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        fab.setOnClickListener((V)->{
          page++;
            lv.smoothScrollToPosition(0);
          swipeRefreshLayout.setRefreshing(true);

            HttpUtils.urlConnect("https://api.tianapi.com/allnews/",text,new HttpCallbackListener(){
                @Override
                public void onFinish(String response) {

                    // mList=JsonUtils.NewslistData(response);



                    getActivity().runOnUiThread(()->{
                        mList.clear();
                        mList.addAll(JsonUtils.NewslistData(response));

                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    });



                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            },page);


        });
        lv.setInterface(this);
        mList=NewsUtils.getAllNews(getActivity());
        adapter=new NewsAdapter();
        lv.setAdapter(adapter);
        //String content= HttpUtils.urlConnect("https://api.tianapi.com/allnews/",text,getActivity());
        HttpUtils.urlConnect("https://api.tianapi.com/allnews/",text,new HttpCallbackListener(){
            @Override
            public void onFinish(String response) {
                //mList=JsonUtils.NewslistData(response);

                getActivity().runOnUiThread(()->{
                    mList.clear();
                    mList.addAll(JsonUtils.NewslistData(response));
                    adapter.notifyDataSetChanged();
                });


            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        },page);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*跳转到新闻详情*/
                NewsBean newsBean=mList.get(position);

                hadread hadread=new hadread();
                hadread.setNewsTitle(newsBean.getTitle());
                hadread.setUrl(newsBean.getUrl());
                hadread.setNewsCategory(Integer.parseInt(text));
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
                page++;
                HttpUtils.urlConnect("https://api.tianapi.com/allnews/",text,new HttpCallbackListener(){
                    @Override
                    public void onFinish(String response) {

                       // mList=JsonUtils.NewslistData(response);

                        Log.d("回调函数执行的线程",String.valueOf(Thread.currentThread().getId()));

                        getActivity().runOnUiThread(()->{
                            mList.clear();
                            mList.addAll(JsonUtils.NewslistData(response));
                            swipeRefreshLayout.setRefreshing(false);
                            adapter.notifyDataSetChanged();
                        });



                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                },page);
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
            ViewHolder holder=null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getActivity().getApplicationContext(), R.layout.newslist_item, null);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tv_des = (TextView) convertView.findViewById(R.id.tv_des);
                holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            NewsBean item = getItem(position);
            holder.tv_title.setText(item.title);
            holder.tv_des.setText(item.des);
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
        page++;
        HttpUtils.urlConnect("https://api.tianapi.com/allnews/",text,new HttpCallbackListener(){
            @Override
            public void onFinish(String response) {



                Log.d("回调函数执行的线程",String.valueOf(Thread.currentThread().getId()));

                getActivity().runOnUiThread(()->{
                    mList.addAll(JsonUtils.NewslistData(response));
                    adapter.notifyDataSetChanged();
                    lv.loadComplete();
                });



            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        },page);

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
                    hateNews.setNewsCategory(Integer.parseInt(text));
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
                    readlater.setNewsCategory(Integer.parseInt(text));
                    readlater.save();
                    /*加入稍后阅读*/
                    break;
                case 2:
                    NewsBean bean1=mList.get(menuInfo.position);
                    LikeBean likeBean=new LikeBean();
                    likeBean.setNewsCategory(Integer.parseInt(text));
                    likeBean.setNewsTitle(bean1.getTitle());
                    likeBean.setUrl(bean1.getNews_url());
                    likeBean.save();

                    /*加入用户喜欢的栏目*/
                    break;
            }
        }
        return super.onContextItemSelected(item);
    }
}
