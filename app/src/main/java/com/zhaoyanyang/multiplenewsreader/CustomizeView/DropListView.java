package com.zhaoyanyang.multiplenewsreader.CustomizeView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zhaoyanyang.multiplenewsreader.R;

public class DropListView extends ListView implements AbsListView.OnScrollListener{
    private View moreView;
    private Context mContext;
    private boolean isLoading=false;
    private ILoadListener iLoadListener;//数据加载接口
    public DropListView(Context context) {
        super(context);
        mContext=context;
        initView();
    }

    public DropListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initView();
    }

    public DropListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initView();
    }
    private void initView(){
        moreView= LayoutInflater.from(mContext).inflate(R.layout.drop_load_more_layout,null);//加载底部的正在加载的信息提示布局
        moreView.findViewById(R.id.load_layout).setVisibility(View.GONE);//默认影藏正在加载的提示信息
        this.addFooterView(moreView);//把提示布局添加到ListView中
        this.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (view.getLastVisiblePosition()==view.getCount()-1 && scrollState==SCROLL_STATE_IDLE){//判断是否滑到最后一条
            if (!isLoading){
                isLoading=true;
                moreView.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
                iLoadListener.onLoad();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (isLoading) {
            moreView.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
        }
    }

    /**
     * 当数据加载完成时调用此方法
     */
    public void loadComplete(){
        isLoading = false;
        moreView.findViewById(R.id.load_layout).setVisibility(
                View.GONE);
    }

    /**
     * 设置数据加载时的接口对象
     * @param iLoadListener
     */
    public void setInterface(ILoadListener iLoadListener){
        this.iLoadListener = iLoadListener;
    }
    //数据加载接口
    public interface ILoadListener{
        public void onLoad();
    }


}
