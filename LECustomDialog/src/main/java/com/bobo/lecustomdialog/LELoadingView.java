package com.bobo.lecustomdialog;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by 公众号：IT波 on 2021/8/21 Copyright © Leon. All rights reserved.
 * Functions: 顺时针旋转加载中view
 */
public class LELoadingView extends AppCompatImageView {

    private float mDegress = 0;
    // 是否需要旋转默认是需要
    private boolean mNeedRotate = true;

    public LELoadingView(Context context) {
        this(context, null);
    }

    public LELoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LELoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setImageResource(R.mipmap.loading);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 开始旋转
        startRotate();
    }

    // 开始旋转
    private void startRotate() {
        mNeedRotate = true;
        post(new Runnable() {
            @Override
            public void run() {
                mDegress += 10;
                if (mDegress >= 360) {
                    mDegress = 0;
                }

                // 调用重绘方法
                invalidate();
                // 判断是否要继续旋转
                // 如果是不可见，或者已经DetachedFromWindow就不再旋转了
                if (getVisibility() == VISIBLE && mNeedRotate) {
                    postDelayed(this, 50);
                } else {
                    // 移除回调任务
                    removeCallbacks(this);
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 停止旋转
        stopRotate();
    }

    // 停止旋转
    private void stopRotate() {
        // this已经不可见了，不在需要旋转
        mNeedRotate = false;
        Log.d("LELoadingView", "startRotate " + mNeedRotate);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(mDegress, getWidth() / 2, getHeight() / 2);
        super.onDraw(canvas);
    }
}
