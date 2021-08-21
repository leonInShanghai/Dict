package com.example.dict.collect_frag;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.dict.ChengyuInfoActivity;
import com.example.dict.R;
import com.example.dict.WordInfoActivity;
import com.example.dict.db.DBManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/8/15 Copyright © Leon. All rights reserved.
 * Functions: 收藏字和成语的fragment
 */
public class ZiFragment extends Fragment {

    private boolean isFirstClick;

    private String mType;
    private GridView mGridView;

    // GridView的数据源
    private List<String> mDatas;
    // GridView的适配器
    private ArrayAdapter<String> mAdapter;

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        isFirstClick = false;
    }

    // 加载数据
    private void loadData() {
        List<String> list;
        mDatas.clear();
        if (mType.equals("汉字")) {
            list = DBManager.queryAllInCollwordtb();
        } else {
            list = DBManager.queryAllCyuInCollcyutb();
        }
        if (list != null) {
            mDatas.addAll(list);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zi, container, false);
        Bundle bundle = getArguments();
        mType = bundle.getString("type"); // 获取当前fragment显示的数据类型，分为文字和成语
        mGridView = view.findViewById(R.id.zifrag_gv);
        mDatas = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(getContext(), R.layout.item_search_gv, R.id.item_sgv_tv, mDatas);
        mGridView.setAdapter(mAdapter);
        // 设置GridView的点击事件
        setGVListener();
        return view;
    }

    // GridView的点击事件
    private void setGVListener() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstClick) {
                    return;
                }
                isFirstClick = true;
                Intent intent;
                if (mType.equals("汉字")) {
                    String zi = mDatas.get(position);
                    intent = new Intent(getActivity(), WordInfoActivity.class);
                    intent.putExtra("zi", zi);
                } else {
                    String cy = mDatas.get(position);
                    intent = new Intent(getActivity(), ChengyuInfoActivity.class);
                    intent.putExtra("chengyu", cy);
                }
                startActivity(intent);
            }
        });
    }
}