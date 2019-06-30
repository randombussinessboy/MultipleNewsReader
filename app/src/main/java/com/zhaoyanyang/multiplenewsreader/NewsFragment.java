package com.zhaoyanyang.multiplenewsreader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private TabLayout mTablayout;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        mTablayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(3);

        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        TestFragment fg1 = new TestFragment();
        Bundle args1 = new Bundle();
        args1.putString("text", "7");
        fg1.setArguments(args1);

        TestFragment fg2 = new TestFragment();
        Bundle args2 = new Bundle();
        args2.putString("text", "22");
        fg2.setArguments(args2);

        TestFragment fg3 = new TestFragment();
        Bundle args3 = new Bundle();
        args3.putString("text", "27");
        fg3.setArguments(args3);

        TestFragment fg4 = new TestFragment();
        Bundle args4 = new Bundle();
        args4.putString("text", "29");
        fg4.setArguments(args4);

        RecommendFragement fg5 = new RecommendFragement();
        Bundle args5 = new Bundle();
        args5.putString("text", "101");
        fg5.setArguments(args5);

        adapter.addFragment(fg1, "国内");
        adapter.addFragment(fg2, "科技");
        adapter.addFragment(fg3, getString(R.string.jokes));
        adapter.addFragment(fg4, "AI");
        adapter.addFragment(fg5, "推荐");

        mViewPager.setAdapter(adapter);


        mTablayout.addTab(mTablayout.newTab().setText(R.string.top));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.nba));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.cars));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.jokes));
        mTablayout.setupWithViewPager(mViewPager);
        return view;
    }




    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
