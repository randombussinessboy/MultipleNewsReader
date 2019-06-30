package com.zhaoyanyang.multiplenewsreader;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.zhaoyanyang.multiplenewsreader.ContextUtils.HateNews;
import com.zhaoyanyang.multiplenewsreader.ContextUtils.LikeBean;
import com.zhaoyanyang.multiplenewsreader.ContextUtils.Readlater;
import com.zhaoyanyang.multiplenewsreader.ContextUtils.hadread;
import com.zhaoyanyang.multiplenewsreader.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, null);
        TextView textView=view.findViewById(R.id.simple_chart);
        String biaotou="*\t国内\t科技\t军事\tAI\t总数量\n";

        int readcount= DataSupport.findAll(hadread.class).size();
        int categoty11=DataSupport.where("newsCategory = ?","7").find(hadread.class).size();
        int categoty12=DataSupport.where("newsCategory = ?","22").find(hadread.class).size();
        int categoty13=DataSupport.where("newsCategory = ?","27").find(hadread.class).size();
        int categoty14=DataSupport.where("newsCategory = ?","29").find(hadread.class).size();
        String read="详细阅读:\t"+categoty11+"\t"+categoty12+"\t"+categoty13+"\t"+categoty14+
        readcount+"\t"+"\n";


        int hatecount= DataSupport.findAll(HateNews.class).size();
        int categoty21=DataSupport.where("newsCategory = ?","7").find(HateNews.class).size();
        int categoty22=DataSupport.where("newsCategory = ?","22").find(HateNews.class).size();
        int categoty23=DataSupport.where("newsCategory = ?","27").find(HateNews.class).size();
        int categoty24=DataSupport.where("newsCategory = ?","29").find(HateNews.class).size();
        String hate="屏蔽:\t"+categoty21+"\t"+categoty22+"\t"+categoty23+"\t"+categoty24+
                hatecount+"\t"+"\n";


        int likecount= DataSupport.findAll(LikeBean.class).size();
        int categoty31=DataSupport.where("newsCategory = ?","7").find(LikeBean.class).size();
        int categoty32=DataSupport.where("newsCategory = ?","22").find(LikeBean.class).size();
        int categoty33=DataSupport.where("newsCategory = ?","27").find(LikeBean.class).size();
        int categoty34=DataSupport.where("newsCategory = ?","29").find(LikeBean.class).size();
        String like="喜欢:\t"+categoty31+"\t"+categoty32+"\t"+categoty33+"\t"+categoty34+
                likecount+"\t"+"\n";


        int latercount= DataSupport.findAll(Readlater.class).size();
        int categoty41=DataSupport.where("newsCategory = ?","7").find(Readlater.class).size();
        int categoty42=DataSupport.where("newsCategory = ?","22").find(Readlater.class).size();
        int categoty43=DataSupport.where("newsCategory = ?","27").find(Readlater.class).size();
        int categoty44=DataSupport.where("newsCategory = ?","29").find(Readlater.class).size();
        String later="稍后阅读:\t"+categoty41+"\t"+categoty42+"\t"+categoty43+"\t"+categoty44+
                latercount+"\t"+"\n";


//        textView.setText(biaotou+read+hate+like+later);

        /*本demo中暂时不考虑时间上下文
        有四个列表,详细阅读过的列表，屏蔽的列表，喜欢的列表，进入稍后阅读的列表
        每个列表里面有四种类型的新闻 包括 国内 科技 军事 AI
        很简单，查询每个列表的数量，每个列表里面的子项又是什么  先查出来，
        最后看看能不能进行可视化，画几个图也好的。

        类别数量查好后，使用特殊的公式 生成用户兴趣向量或则词云形式

        登录或则注册只是对用户进行标签化，不一定需使用登录注册才能使用App,
        但是注册应该有某种奖励机制。

            *     国内  科技  军事  AI  总数量
           详细阅读
           屏蔽
           喜欢
           稍后阅读
         */

        /*
        用户画像生成算法，应该是一个四维向量 向量应该怎么计算？这里不考虑时间上下文

        四个类别的四种状态 详细阅读加1分 屏蔽减5分  喜欢加2分  收藏加入列表加3分

        * */

        int guonei=categoty11-categoty12*5+categoty13*2+categoty14*3;
        int keji=categoty21-categoty22*5+categoty23*2+categoty24*3;
        int junshi=categoty31-categoty32*5+categoty33*2+categoty34*3;
        int ai=categoty41-categoty42*5+categoty43*2+categoty44*3;

//        /*兴趣向量,可视化为词云*/
//        String inteVector="("+guonei+","+keji+","+junshi+","+ai+")";
//
//
//        TextView textView2=view.findViewById(R.id.simple_chart_2);
//        textView2.setText("兴趣特征向量："+"\n"+inteVector);


        /*使用可视化组件重构 四个柱形图 一个折线图  一个柱形图或者词云来展现画像
        * 需要六个图左右*/

        LineChart lineChart=view.findViewById(R.id.chart5);
        LineData mLineData = getLineData(36, 100);
        showChart(lineChart, mLineData, Color.rgb(114, 188, 223));

       BarChart mBarChart1=(BarChart)view.findViewById(R.id.chart1);
        int[] a=new int[]{guonei,keji,junshi,ai};

       BarData mBarData1=getBarData(a,"兴趣向量图");
       showBarChart(mBarChart1, mBarData1);


        BarChart mBarChart2=(BarChart)view.findViewById(R.id.chart2);
        int[] b=new int[]{categoty11,categoty12,categoty13,categoty14};

        BarData mBarData2=getBarData(b,"详细阅读");
        showBarChart(mBarChart2, mBarData2);

        BarChart mBarChart3=(BarChart)view.findViewById(R.id.chart3);
        int[] c=new int[]{categoty21,categoty22,categoty23,categoty24};

        BarData mBarData3=getBarData(c,"屏蔽");
        showBarChart(mBarChart3, mBarData3);


        BarChart mBarChart4=(BarChart)view.findViewById(R.id.chart4);
        int[] d=new int[]{categoty31,categoty32,categoty33,categoty34};

        BarData mBarData4=getBarData(d,"喜欢");
        showBarChart(mBarChart4, mBarData4);


        BarChart mBarChart5=(BarChart)view.findViewById(R.id.chart6);
        int[] e=new int[]{categoty41,categoty42,categoty43,categoty44};

        BarData mBarData5=getBarData(e,"稍后阅读");
        showBarChart(mBarChart5, mBarData5);

        return view;
    }


    private BarData getBarData(int[] a,String lable) {
        ArrayList<String> xValues = new ArrayList<String>();
        xValues.add("国内");
        xValues.add("科技");
        xValues.add("军事");
        xValues.add("AI");

        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();

        for (int i = 0; i < 4; i++) {

            yValues.add(new BarEntry(a[i], i));
        }

        // y轴的数据集合
        BarDataSet barDataSet = new BarDataSet(yValues, lable);

        barDataSet.setColor(Color.rgb(114, 188, 223));

        ArrayList<IBarDataSet> barDataSets = new ArrayList<IBarDataSet>();
        barDataSets.add(barDataSet); // add the datasets

        BarData barData = new BarData(xValues, barDataSets);

        return barData;
    }



    private void showBarChart(BarChart barChart, BarData barData) {
        barChart.setDrawBorders(false);  ////是否在折线图上添加边框

        barChart.setDescription("对应各项指标");// 数据描述

        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
        barChart.setNoDataTextDescription("You need to provide data for the chart.");

        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

        barChart.setTouchEnabled(true); // 设置是否可以触摸

        barChart.setDragEnabled(true);// 是否可以拖拽
        barChart.setScaleEnabled(true);// 是否可以缩放

        barChart.setPinchZoom(false);//

//		barChart.setBackgroundColor();// 设置背景

        barChart.setDrawBarShadow(true);

        barChart.setData(barData); // 设置数据

        Legend mLegend = barChart.getLegend(); // 设置比例图标示

        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.BLACK);// 颜色

//      X轴设定
//      XAxis xAxis = barChart.getXAxis();
//      xAxis.setPosition(XAxisPosition.BOTTOM);

        barChart.animateX(2500); // 立即执行的动画,x轴
    }






    private LineData getLineData(int count, float range) {
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            // x轴显示的数据，这里默认使用数字下标显示
            xValues.add("" + i);
        }

        // y轴的数据
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            float value = (float) (Math.random() * range) + 3;
            yValues.add(new Entry(value, i));
        }

        // create a dataset and give it a type
        // y轴的数据集合
        LineDataSet lineDataSet = new LineDataSet(yValues, "新闻类别对应操作次数图" /*显示在比例图上*/);
        // mLineDataSet.setFillAlpha(110);
        // mLineDataSet.setFillColor(Color.RED);

        //用y轴的集合来设置参数
        lineDataSet.setLineWidth(1.75f); // 线宽
        lineDataSet.setCircleSize(3f);// 显示的圆形大小
        lineDataSet.setColor(Color.WHITE);// 显示颜色
        lineDataSet.setCircleColor(Color.WHITE);// 圆形的颜色
        lineDataSet.setHighLightColor(Color.WHITE); // 高亮的线的颜色

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();
        lineDataSets.add(lineDataSet); // add the datasets

        // create a data object with the datasets
        LineData lineData = new LineData(xValues, lineDataSets);

        return lineData;
    }

    private void showChart(LineChart lineChart, LineData lineData, int color) {
        lineChart.setDrawBorders(false);  //是否在折线图上添加边框

        // no description text
        lineChart.setDescription("");// 数据描述
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable / disable grid background
        lineChart.setDrawGridBackground(false); // 是否显示表格颜色
        lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

        // enable touch gestures
        lineChart.setTouchEnabled(true); // 设置是否可以触摸

        // enable scaling and dragging
        lineChart.setDragEnabled(true);// 是否可以拖拽
        lineChart.setScaleEnabled(true);// 是否可以缩放

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);//

        lineChart.setBackgroundColor(color);// 设置背景

        // add data
        lineChart.setData(lineData); // 设置数据

        // get the legend (only possible after setting data)
        Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的

        // modify the legend ...
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.WHITE);// 颜色
//      mLegend.setTypeface(mTf);// 字体

        lineChart.animateX(2500); // 立即执行的动画,x轴
    }




}
