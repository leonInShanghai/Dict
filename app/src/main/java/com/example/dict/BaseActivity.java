package com.example.dict;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dict.utils.StatusBarFontColor;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by 公众号：IT波 on 2021/7/25 Copyright © Leon. All rights reserved.
 * Functions: 封装加载网络数据的过程
 */
public class BaseActivity extends AppCompatActivity implements Callback.CommonCallback<String> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 改变改变状态栏字体颜色为黑色
        StatusBarFontColor.changeFontColorBlack(this);
    }

    public void loadData(String path) {
        // 创建请求参数体
        RequestParams params = new RequestParams(path);
        x.http().get(params, this);
    }

    /**
     * 请求成功回调
     * @param result 获取到的json数据
     */
    @Override
    public void onSuccess(String result) {

    }

    /**
     * 网络请求失败回调
     * @param ex
     * @param isOnCallback
     */
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    /**
     * 取消回调
     * @param cex
     */
    @Override
    public void onCancelled(CancelledException cex) {

    }

    /**
     * 请求完成
     */
    @Override
    public void onFinished() {

    }
}
