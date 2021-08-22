package com.example.dict.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;

import com.example.dict.R;
import com.example.dict.db.DBManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/8/14 Copyright © Leon. All rights reserved.
 * Functions: 成语查询activity
 */
public class SearchChengyuActivity extends BaseActivity {

    private EditText cyEt;
    private GridView cyGv;
    private ArrayAdapter mAdapter;

    // GridView's data source.
    private List<String> mDatas;

    @Override
    protected void onResume() {
        super.onResume();
        // 清空输入框中上一次查询的记录
        cyEt.setText("");
        initDatas();
    }

    /**
     * 从数据库中查找历史记录
     */
    private void initDatas() {
        if (mDatas != null) {
            mDatas.clear();
        }
        mDatas.addAll(DBManager.queryAllCyFromCyutb());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_chengyu);
        cyEt = findViewById(R.id.searchcy_et);
        cyGv = findViewById(R.id.searchcy_gv);
        mDatas = new ArrayList<>();
        // 创建适配对象
        mAdapter = new ArrayAdapter<String>(this, R.layout.item_searchcy_gv, R.id.item_searchcy_tv, mDatas);
        cyGv.setAdapter(mAdapter);
        // GridView's click listener.
        setGVListener();
    }

    /**
     * GridView's click listener.
     */
    private void setGVListener() {
        cyGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Jump to chengyu info activity.
                String msg = mDatas.get(position);
                startPage(msg);
            }
        });
    }

    /**
     * 在xml中设置的点击事件
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchcy_iv_back:
                finish();
                break;
            case R.id.searchcy_iv_search:
                String text = cyEt.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    // TODO:TIP USER
                    return;
                }
                // Jump to idiom details page.
                startPage(text);
                break;
        }
    }

    /**
     * 跳转（activity）页面方法
     * @param text 跳转时需要传递的参数
     */
    private void startPage(String text) {
        Intent intent = new Intent(this, ChengyuInfoActivity.class);
        intent.putExtra("chengyu", text);
        startActivity(intent);
    }
}