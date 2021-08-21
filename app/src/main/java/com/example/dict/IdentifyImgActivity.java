package com.example.dict;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.dict.utils.PatternUtils;

import java.util.ArrayList;

/**
 * Created by 公众号：IT波 on 2021/8/8 Copyright © Leon. All rights reserved.
 * Functions: 用于显示扫描图片上文字的结果
 */
public class IdentifyImgActivity extends BaseActivity {

    // 展示内容的网格视图
    private GridView mGv;
    // 数据源
    private ArrayList<String> mDatas;
    // 网格视图适配器
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_img);
        mGv = findViewById(R.id.iden_gv);
        // 获取上一个界面传递的数据
        Bundle bundle = getIntent().getExtras();
        mDatas = bundle.getStringArrayList("wordlist");
        mAdapter = new ArrayAdapter<>(this, R.layout.item_search_gv, R.id.item_sgv_tv, mDatas);
        mGv.setAdapter(mAdapter);
        setGVListener();
    }

    /**
     * 设置网格视图点击事件
     */
    private void setGVListener() {
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String word = mDatas.get(position);
                // 剔除全部数字,字母,标点符号.
                String res = PatternUtils.removeAll(word);
                if (TextUtils.isEmpty(res)) {
                    Toast.makeText(IdentifyImgActivity.this, "点击汉字才能查看解释", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(IdentifyImgActivity.this, WordInfoActivity.class);
                    intent.putExtra("zi", res);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * xml中设置点击事件
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iden_iv_back:
                finish();
                break;
        }
    }
}