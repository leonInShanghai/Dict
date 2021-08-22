package com.example.dict.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bobo.lecustomdialog.LEAlertContentLoadingView;
import com.example.dict.R;
import com.example.dict.bean.ChengyuBean;
import com.example.dict.db.DBManager;
import com.example.dict.utils.URLUtils;
import com.example.dict.view.MyGridView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/8/14 Copyright © Leon. All rights reserved.
 * Functions: 成语解释activity
 */
public class ChengyuInfoActivity extends BaseActivity {

    private TextView ziTv1, ziTv2, ziTv3, ziTv4, pyTv, jsTv, fromTv, exampleTv, yufaTv, yinzhengTv, yinghanTv;
    private MyGridView tyGv, fyGv;
    private ImageView collectIv;
    private String mChengyu;
    // MyGridView数据源
    private List<String> tongyiList;
    private List<String> fanyiList;
    private ArrayAdapter<String> mTongyiAdapter;
    private ArrayAdapter<String> mFanyiAdapter;
    private TextView synonym;
    private TextView antonym;

    // 设一个标记为表示该成语是否被用户收藏 ,两个标记为是因为用户恶意按收藏按钮按的快，写数据库不需要那么频繁。
    private boolean isCollect = false;
    private boolean isExist = false;

    // loading弹窗2021-8-21增加
    protected AlertDialog mAlterDiaglog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chengyu_info);
        initView();
        initAdapter();
        // 获取上一个页面传递的数据
        mChengyu = getIntent().getStringExtra("chengyu");
        String url = URLUtils.getChengyuUrl(mChengyu);
        loadData(url);
        isExist = DBManager.isExistCyuInCollcytb(mChengyu);
        isCollect = isExist;
        setCollectIvStyle();

        // 2021-8-21增加logding弹窗出现
        if (mAlterDiaglog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog)
                    .setCancelable(false)
                    .setView(new LEAlertContentLoadingView(this));
            mAlterDiaglog = builder.show();
        } else {
            // 已经创建了直接显示
            mAlterDiaglog.show();
        }
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
     * MyGridView设置适配器
     */
    private void initAdapter() {
        tongyiList = new ArrayList<>();
        fanyiList = new ArrayList<>();
        mTongyiAdapter = new ArrayAdapter<>(this, R.layout.item_word_jslv, R.id.item_wordlv_tv, tongyiList);
        mFanyiAdapter = new ArrayAdapter<>(this, R.layout.item_word_jslv, R.id.item_wordlv_tv, fanyiList);
        tyGv.setAdapter(mTongyiAdapter);
        fyGv.setAdapter(mFanyiAdapter);
    }

    /**
     * 网络请求成功
     * @param result 获取到的json数据
     */
    @Override
    public void onSuccess(String result) {
        super.onSuccess(result);
        Log.d("ChengyuInfoActivity", "onSuccess result: " + result);
        ChengyuBean bean = new Gson().fromJson(result, ChengyuBean.class);
        ChengyuBean.ResultBean cyBean = bean.getResult();

        if (cyBean != null) {
            // 因为数据源当中不包括数据本身，但是后期要插入数据库必不可少的需要成语本身。
            cyBean.setChengyu(mChengyu);

            // 插入数据库
            DBManager.insterCyToCyutb(cyBean);
            // 显示数据
            showDataToView(cyBean);
        } else {
            // TODO: tosat 无法查询到您输入的成语！请修改后查询。
        }

        // 2021-8-21新增加数据加载中loadding...
        if (mAlterDiaglog != null && mAlterDiaglog.isShowing()) {
            mAlterDiaglog.dismiss();
        }
    }

    /**
     * 网络请求失败
     * @param ex
     * @param isOnCallback
     */
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        super.onError(ex, isOnCallback);
        // TODO:1.提示用户请求失败了，即将为您显示缓存数据
        // 获取数据库中缓存的数据
        ChengyuBean.ResultBean bean = DBManager.queryChengyuFromCytb(mChengyu);
        if (bean != null) {
            showDataToView(bean);
        } else {
            // TODO: 2.提示用户连缓存数据都没有
        }

        // 2021-8-21新增加数据加载中loadding...
        if (mAlterDiaglog != null && mAlterDiaglog.isShowing()) {
            mAlterDiaglog.dismiss();
        }
    }

    /**
     * 将获取到的数据显示在view上
     * @param cyBean
     */
    private void showDataToView(ChengyuBean.ResultBean cyBean) {
        String chengyu = cyBean.getChengyu();
        ziTv1.setText(String.valueOf(chengyu.charAt(0)));
        ziTv2.setText(String.valueOf(chengyu.charAt(1)));
        ziTv3.setText(String.valueOf(chengyu.charAt(2)));
        ziTv4.setText(String.valueOf(chengyu.charAt(3)));

        pyTv.setText("拼音: " + cyBean.getPinyin());
        jsTv.setText(cyBean.getChengyujs());
        fromTv.setText(cyBean.getFrom_());
        exampleTv.setText(cyBean.getExample());
        yufaTv.setText(cyBean.getYufa());
        yinzhengTv.setText(cyBean.getYinzhengjs());
        String ciyujs = cyBean.getCiyujs();
        if (!TextUtils.isEmpty(ciyujs)) {
            ciyujs = ciyujs.replace("[", "").replace("]", "\n");
            yinghanTv.setText(ciyujs);
        }

        List<String> tList = cyBean.getTongyi();
        if (tList != null && tList.size() > 0) {
            tongyiList.addAll(tList);
            mTongyiAdapter.notifyDataSetChanged();
            synonym.setVisibility(View.VISIBLE);
        } else {
            synonym.setVisibility(View.GONE);
        }
        List<String> fList = cyBean.getFanyi();
        if (fList != null && fList.size() > 0) {
            fanyiList.addAll(fList);
            mFanyiAdapter.notifyDataSetChanged();
            antonym.setVisibility(View.VISIBLE);
        } else {
            antonym.setVisibility(View.GONE);
        }
    }

    /**
     * 查找控件的方法
     */
    private void initView() {
        ziTv1 = findViewById(R.id.cyinfo_tv_zi1);
        ziTv2 = findViewById(R.id.cyinfo_tv_zi2);
        ziTv3 = findViewById(R.id.cyinfo_tv_zi3);
        ziTv4 = findViewById(R.id.cyinfo_tv_zi4);
        pyTv = findViewById(R.id.cyinfo_tv_py);
        jsTv = findViewById(R.id.cyinfo_tv_js);
        fromTv = findViewById(R.id.cyinfo_tv_from);
        exampleTv = findViewById(R.id.cyinfo_tv_example);
        yufaTv = findViewById(R.id.cyinfo_tv_yufa);
        yinzhengTv = findViewById(R.id.cyinfo_tv_yinzheng);
        yinghanTv = findViewById(R.id.cyinfo_yinghan);
        tyGv = findViewById(R.id.cyinfo_gv_tongyi);
        fyGv = findViewById(R.id.cyinfo_gv_fanyi);
        // 右上角的收藏
        collectIv = findViewById(R.id.cyinfo_iv_collection);
        synonym = findViewById(R.id.tv_synonym);
        antonym = findViewById(R.id.tv_antonym);
    }

    /**
     * 从xml中设置的点击事件
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cyinfo_iv_back:
                finish();
                break;
            case R.id.cyinfo_iv_collection:
                // 用户点击了收藏
                isCollect = !isCollect;
                setCollectIvStyle();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (isExist&&!isCollect) {
            // 原来收藏了该文字现在不想收藏了, 需要删除
            DBManager.deleteCyuToCollcytb(mChengyu);
        } else if (!isExist&&isCollect) {
            // 原来没有收藏，现在想要收藏, 需要插入数据库表
            DBManager.insertCyuToCollcytb(mChengyu);
        }

        // 2021-8-21新增加数据加载中loadding...
        if (mAlterDiaglog != null && mAlterDiaglog.isShowing()) {
            mAlterDiaglog.dismiss();
        }

        super.onDestroy();
    }
}