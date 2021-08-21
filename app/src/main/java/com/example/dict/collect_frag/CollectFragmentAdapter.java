package com.example.dict.collect_frag;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/8/21 Copyright © Leon. All rights reserved.
 * Functions:  收藏字和成语的viewPager适配器
 */
public class CollectFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;
    protected String[] titles;

    public CollectFragmentAdapter(FragmentManager fm, List<Fragment> list, String[] titles) {
        super(fm);
        mList = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.length > 0) {
            return titles[position];
        }
        return super.getPageTitle(position);
    }
}
