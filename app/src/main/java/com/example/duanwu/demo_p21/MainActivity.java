package com.example.duanwu.demo_p21;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager mVp;
    private TabLayout mTabLayout;
    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mTitles;
    private ArrayList<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        initView();

    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(new BlankFragment());
        mFragments.add(new BlankFragment2());


        mTitles = new ArrayList<>();
        mTitles.add("首页");
        mTitles.add("我的");


    }

    private void initView() {
        mVp = (ViewPager) findViewById(R.id.vp);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //viewpager
        VpAdapter vpAdapter = new VpAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mVp.setAdapter(vpAdapter);

        //TabLayout
        mTabLayout.setupWithViewPager(mVp);

    }

}
