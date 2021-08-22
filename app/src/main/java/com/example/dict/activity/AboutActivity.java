package com.example.dict.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;

import com.bobo.lecustomdialog.LEAlertContentLoadingView;
import com.bobo.lecustomdialog.LEAutoHideDialog;
import com.example.dict.R;

import static android.webkit.WebSettings.LOAD_CACHE_ELSE_NETWORK;

/**
 * Created by 公众号：IT波 on 2021/8/22 Copyright © Leon. All rights reserved.
 * Functions: 关于我们页面加载的是本地html5
 */
public class AboutActivity extends BaseActivity {

    private FrameLayout mFrameLayout;

    // 是否要显示加载错的页面的变量
    private boolean mLoadError = false;

    // loading弹窗2021-8-21增加
    protected AlertDialog mAlterDiaglog;
    private WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mFrameLayout = findViewById(R.id.fl_container);

        mFrameLayout.removeAllViews();

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

        // 加载webview
        mWebView = new WebView(this);

        // 使用Java代码设置宽高布局
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(layoutParams);

        // 允许js运行
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setCacheMode(LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptEnabled(true);

        // 加载本地assets文件夹中自己写的H5页面
        mWebView.loadUrl("https://mp.weixin.qq.com/s?__biz=MzIzNjU5NDk1MQ==&mid=2247483776&idx=1&sn=65937520854bf8e0a75b" +
                "d5718acff241&chksm=e8d433e8dfa3bafe911520b2c3ba8f3e25930992c9240559f17a1d93e0fb91d7e71c90f70e69&token=" +
                "881862043&lang=zh_CN#rd");

        /**
         * 發現網易的url會重定向-即你輸入一個鏈接他會自動跳轉到另外一個鏈接
         * 重定向解決方法: mWebView.setWebViewClient(new WebViewClient());
         */
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {

                //加载失败loadError = true
                super.onReceivedError(view, errorCode, description, failingUrl);
                // view.setVisibility(View.GONE);
                // mErrorView.setVisibility(View.VISIBLE);
                mLoadError = true;
            }

            //低版本手机会走这个（过时的）方法
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                // view.setVisibility(View.GONE);
                // mErrorView.setVisibility(View.VISIBLE);
                mLoadError = true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {//加载成功
                super.onPageFinished(view, url);

                // 2021-8-21新增加数据加载中loadding...
                if (mAlterDiaglog != null && mAlterDiaglog.isShowing()) {
                    mAlterDiaglog.dismiss();
                }

                // 严谨起见还是在做判断
                if (mLoadError == true) {
                    // 显示错误页面
                    LEAutoHideDialog dialog = new LEAutoHideDialog(AboutActivity.this, 0,
                            "警告", "网络异常!", 0);
                    dialog.show();
                }

            }
        });

        // 帧布局再添加webview进来
        mFrameLayout.addView(mWebView);
    }

    public void onClick(View view) {
        switch (view.getId()) {
           case  R.id.about_iv_back:
               // 判斷webview能夠返回上一個網頁
               if (mWebView.canGoBack()){
                   // 返回上一個網頁
                   mWebView.goBack();
               }else {
                   // 返回上一頁finish();
                   finish();
               }
               break;
        }
    }

    @Override
    protected void onDestroy() {
        // 2021-8-21新增加数据加载中loadding...
        if (mAlterDiaglog != null && mAlterDiaglog.isShowing()) {
            mAlterDiaglog.dismiss();
        }
        super.onDestroy();
    }
}