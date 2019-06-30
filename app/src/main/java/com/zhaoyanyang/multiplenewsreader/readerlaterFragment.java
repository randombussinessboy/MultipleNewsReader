package com.zhaoyanyang.multiplenewsreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.zhaoyanyang.multiplenewsreader.ContextUtils.Readlater;
import com.zhaoyanyang.multiplenewsreader.ContextUtils.hadread;
import com.zhaoyanyang.multiplenewsreader.NewsDetails.NewsDetailActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;

public class readerlaterFragment extends Fragment {

    private ArrayList<Readlater> mList=null;
    private ListView lv;
    private NewsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.readlater, null);

        mList= new ArrayList(DataSupport.findAll(Readlater.class));
        Collections.reverse(mList);

        lv=view.findViewById(R.id.read_later);
        adapter=new NewsAdapter();
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Readlater newsBean=mList.get(position);
                hadread hadread=new hadread();
                hadread.setNewsTitle(newsBean.getNewsTitle());
                hadread.setUrl(newsBean.getUrl());
                hadread.setNewsCategory(newsBean.getNewsCategory());
                hadread.save();
                Intent intent=new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra(NewsDetailActivity.NEWS_NAME,newsBean.getNewsTitle());
                intent.putExtra(NewsDetailActivity.NEWS_DETAILS_URL,newsBean.getUrl());
                intent.putExtra(NewsDetailActivity.NEWS_PIC_URL,newsBean.getNewsPicUrl());
                startActivity(intent);
            }
        });

        lv.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(Menu.NONE, 0, 0, "删除");

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
        public Readlater getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            readerlaterFragment.ViewHolder holder=null;
            if (convertView == null) {
                holder = new readerlaterFragment.ViewHolder();
                convertView = View.inflate(getActivity().getApplicationContext(), R.layout.newslist_item, null);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tv_des = (TextView) convertView.findViewById(R.id.tv_des);
                holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                convertView.setTag(holder);
            } else {
                holder = (readerlaterFragment.ViewHolder) convertView.getTag();
            }
            Readlater item = getItem(position);
            holder.tv_title.setText(item.getNewsTitle());
            holder.tv_des.setText(item.getNewsDescription());
            Glide.with(getActivity()).load(item.getNewsPicUrl()).into(holder.iv_icon);

            return convertView;
        }

    }

    private static class ViewHolder {
        TextView tv_title;
        TextView tv_des;
        ImageView iv_icon;

    }
    public boolean onContextItemSelected(MenuItem item){

            //关键代码在这里
            AdapterView.AdapterContextMenuInfo menuInfo;
            menuInfo =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
            switch (item.getItemId()){
                case 0:
                    //点击第一个菜单项要做的事，如获取点击listview的位置
//                Toast.makeText(getActivity(), String.valueOf(menuInfo.position), Toast.LENGTH_LONG).show();
                    Readlater readlater=mList.get(menuInfo.position);
                    readlater.delete();
                    mList.remove(menuInfo.position);
                    adapter.notifyDataSetChanged();

                    break;

                case 2:
                    /*加入用户喜欢的栏目*/
                    break;
            }

        return super.onContextItemSelected(item);
    }
}
