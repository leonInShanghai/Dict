package com.example.dict;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dict.bean.WordBean;
import com.example.dict.db.DBManager;
import com.example.dict.utils.URLUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 公众号：IT波 on 2021/7/26 Copyright © Leon. All rights reserved.
 * Functions: 文字详情Activity
 */
public class WordInfoActivity extends BaseActivity {

    private TextView ziTv;
    private TextView pyTv;
    private TextView wubiTv;
    private TextView bihuaTv;
    private TextView bushouTv;
    private TextView jsTv;
    private TextView xxjsTv;

    private ListView jsLv;
    private ImageView collectIv;
    private String mZi;

    // ListView的数据源
    private List<String> mDatas;

    // ListView适配器注意和以往用的不一样
    private ArrayAdapter mAdapter;
    private List<String> mJijie;
    private List<String> mXiangjie;

    // 设一个标记为表示该汉字是否被用户收藏 ,两个标记为是因为用户恶意按收藏按钮按的快，写数据库不需要那么频繁。
    private boolean isCollect = false;
    private boolean isExist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_info);

        // 接收上一个页面传递过来的对象
        Intent intent = getIntent();
        mZi = intent.getStringExtra("zi");
        String url = URLUtils.getWordUrl(mZi); // 拼接网址
        initView();

        // 初始化ListView显示的数据源
        mDatas = new ArrayList<>();
        mAdapter = new ArrayAdapter<String>(this, R.layout.item_word_jslv, R.id.item_wordlv_tv, mDatas);
        jsLv.setAdapter(mAdapter);
        // 加载网络数据
        loadData(url);

        // 调用判断是否已经收藏的方法
        isExist = DBManager.isExistZiInCollwordtb(mZi);
        isCollect = isExist; // 记录初始状态
        setCollectIvStyle();
    }

    /**
     * 设置收藏按钮的颜色
     */
    private void setCollectIvStyle() {
        if (isCollect) {
            collectIv.setImageResource(R.mipmap.ic_collection_fs);
        } else {
            collectIv.setImageResource(R.mipmap.ic_collection);
        }
    }

    /**
     * 获取数据成功时的回调
     * @param json 获取到的json数据
     */
    @Override
    public void onSuccess(String json) {
        super.onSuccess(json);
        WordBean wordBean = new Gson().fromJson(json, WordBean.class);
        WordBean.ResultBean resultBean = wordBean.getResult();
        // 1.插入数据库,因为这里就插入一个数据所以没有开启子线程
        DBManager.insertWordToWordtb(resultBean);
        // 2.将数据显示在view上
        notifyView(resultBean);
    }

    /**
     * 更新视图
     * @param resultBean
     */
    private void notifyView(WordBean.ResultBean resultBean) {
        ziTv.setText(resultBean.getZi());
        pyTv.setText(resultBean.getPinyin());
        wubiTv.setText("五笔：" + resultBean.getWubi());
        bihuaTv.setText("笔画：" + resultBean.getBihua());
        bushouTv.setText("部首：" + resultBean.getBushou());
        mJijie = resultBean.getJijie();
        mXiangjie = resultBean.getXiangjie();
        // 默认一进去，就显示基本释义
        // mDatas.clear();
        mDatas.addAll(mJijie);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 获取数据失败时会调用的方法
     * @param ex
     * @param isOnCallback
     */
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        super.onError(ex, isOnCallback);
        // 联网失败或服务器端返回异常显示缓存数据
        WordBean.ResultBean bean = DBManager.queryWordFromWordtb(mZi);
        if (bean != null) {
            notifyView(bean);
        }
    }

    private void initView() {
        ziTv = findViewById(R.id.word_tv_zi);
        wubiTv = findViewById(R.id.word_tv_wubi);
        pyTv = findViewById(R.id.word_tv_pinyin);
        bihuaTv = findViewById(R.id.word_tv_buhua);
        bushouTv = findViewById(R.id.word_tv_bushou);
        jsTv = findViewById(R.id.word_tv_js);
        xxjsTv = findViewById(R.id.word_tv_xx_js);
        jsLv = findViewById(R.id.word_lv_js);
        collectIv = findViewById(R.id.wordinfo_iv_collection);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wordinfo_iv_back:
                finish();
                break;
            case R.id.wordinfo_iv_collection:
                // 1.将收藏的状态取反
                isCollect = !isCollect;
                setCollectIvStyle();
                break;
            case R.id.word_tv_js:
                jsTv.setTextColor(Color.RED);
                xxjsTv.setTextColor(Color.BLACK);
                // 清空之前数据源
                mDatas.clear();
                mDatas.addAll(mJijie);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.word_tv_xx_js:
                xxjsTv.setTextColor(Color.RED);
                jsTv.setTextColor(Color.BLACK);
                // 清空之前数据源
                mDatas.clear();
                mDatas.addAll(mXiangjie);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (isExist&&!isCollect) {
            // 原来收藏了该文字现在不想收藏了, 需要删除
            DBManager.deleteZiToCollwordtb(mZi);
        } else if (!isExist&&isCollect) {
            // 原来没有收藏，现在想要收藏, 需要插入数据库表
            DBManager.insertZiToCollwordtb(mZi);
        }
        super.onDestroy();
    }
}