package com.zhaoyanyang.multiplenewsreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhaoyanyang.multiplenewsreader.ContextUtils.LabelLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private String lableName[] = {"军事科技", "国内新闻",
            "农业", "人工智能", "国外", "校园"
    };
    private String lableName1[] = {"热门",
            "桂林", "城市", "娱乐"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initData();
        initView();
    }
    public void initData() {
    }

    LabelLayout labelLayout, labelLayoutall;
    ViewGroup.MarginLayoutParams lp;

    private void initView() {

        lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;

        labelLayout = (LabelLayout) findViewById(R.id.flow);
        labelLayoutall = (LabelLayout) findViewById(R.id.flow_all);
        for (String aLableName : lableName) {
            TextView view = new TextView(this);
            view.setText(aLableName);
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            view.setTag("up");
            labelLayout.addView(view, lp);
            view.setOnClickListener(this);
        }

        for (String aLableName : lableName1) {
            TextView view = new TextView(this);
            view.setText(aLableName);
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            view.setTag("down");
            labelLayoutall.addView(view, lp);
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        String txt = ((TextView) view).getText().toString();
        if (view.getTag().equals("up")) {
            labelLayout.removeView(view);
            labelLayout.invalidate();

            TextView view1 = new TextView(this);
            view1.setText(txt);
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            view1.setTag("down");
            view1.setOnClickListener(this);

            labelLayoutall.addView(view1, lp);
            labelLayoutall.invalidate();

        } else if (view.getTag().equals("down")) {
            labelLayoutall.removeView(view);
            labelLayoutall.invalidate();

            TextView view1 = new TextView(this);
            view1.setText(txt);
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            view1.setTag("up");
            view1.setOnClickListener(this);

            labelLayout.addView(view1, lp);
            labelLayout.invalidate();
        }
    }


}
