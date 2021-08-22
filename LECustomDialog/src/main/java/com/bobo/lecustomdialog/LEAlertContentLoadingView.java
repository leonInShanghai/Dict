package com.bobo.lecustomdialog;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * Created by 公众号：IT波 on 2021/1/24 Copyright © Leon. All rights reserved.
 * Functions: 自定义加载view
 */
public class LEAlertContentLoadingView extends LinearLayout {

    private String mTag = "LEAlertContentLoadingView";

    // 显示波波加载中的textView
    private TextView mTextView;
    // 显示加载图片旋转imageView
    private LELoadingView mLELoadingView;

    public LEAlertContentLoadingView(Context context) {
        this(context, null);
    }

    public LEAlertContentLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LEAlertContentLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // viewGrep加载布局不要有返回值反例：
        // LayoutInflater inflater LayoutInflater.from(context).inflate(R.layout.content_loading_view, this);
        LayoutInflater.from(context).inflate(R.layout.content_loading_view, this);
        mTextView = findViewById(R.id.tv_loading_text);
        mLELoadingView = findViewById(R.id.lv_loading_image);
    }

    /**
     * 设置加载时显示的文字 如：波波加载中...
     * @param text
     */
    public void setLoadingText(String text) {
        if (TextUtils.isEmpty(text)) {
            Log.e(mTag, "setLoadingText text is empty");
        } else {
            mTextView.setText(text);
        }
    }

    /**
     * 设置加载时旋转的图片
     * @param res
     */
    public void setLELoadingViewResource(int res) {
        mLELoadingView.setImageResource(res);
    }
}
