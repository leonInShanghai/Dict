package com.example.dict;


import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.dict.collect_frag.CollectFragmentAdapter;
import com.example.dict.collect_frag.ZiFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/8/15 Copyright © Leon. All rights reserved.
 * Functions: 收藏Activity
 */
public class CollectionActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager collectVp;
    private String[] titles = {"汉字", "成语"};
    private List<Fragment> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        mTabLayout = findViewById(R.id.collect_tabs);
        collectVp = findViewById(R.id.collect_vp);

        initPager();
    }

    /**
     * 初始化ViewPager
     */
    private void initPager() {
        mDatas = new ArrayList<>();
        for (String title : titles) {
            Fragment frat = new ZiFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", title);
            frat.setArguments(bundle);
            mDatas.add(frat);
        }
        CollectFragmentAdapter adapter = new CollectFragmentAdapter(getSupportFragmentManager(), mDatas, titles);
        collectVp.setAdapter(adapter);

        // tabLayout 和 ViewPager绑定
        mTabLayout.setupWithViewPager(collectVp);
    }

    /**
     * 从xml中加载点击事件
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.collect_iv_back:
                finish();
                break;
        }
    }
}