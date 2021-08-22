package com.example.dict.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.example.dict.R;

public class AboutActivity extends BaseActivity {

    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mFrameLayout = findViewById(R.id.fl_container);

        mFrameLayout.removeAllViews();

        // 加载webview
        WebView webView = new WebView(this);

        // 使用Java代码设置宽高布局
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(layoutParams);

        // 允许js运行
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // 加载本地assets文件夹中自己写的H5页面
        webView.loadUrl("file:///android_asset/about/波波字典.html");

        // 帧布局再添加webview进来
        mFrameLayout.addView(webView);
    }

    public void onClick(View view) {
        switch (view.getId()) {
           case  R.id.about_iv_back:
               finish();
               break;
        }
    }
}