package com.bobo.lecustomdialog;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 公众号：IT波 on 2021/8/21 Copyright © Leon. All rights reserved.
 * Functions: 自定义AlertDialog
 */
public class LEAutoHideDialog extends Dialog {

    private String mTag = "LEAlertDialog";

    private Context mContext;
    private String mTitle;
    private String mSubTitle;
    private int mResId;
    // dialog自动消失时长
    private int mDuration = 1600;

    //要执行动画的imageview
    private ImageView mImage;

    private static final int ANIMA = 20219;


    /***
     * @param context 上下文
     * @param resId 用户可以自定义设置自己所需icon
     * @param title 标题例如：警告
     * @param subTitle 副标题如：网络异常
     * @param duration 自动消失的时长，注意：如果设置>=0，按默认1600执行!
     */
    public LEAutoHideDialog(Context context, int resId, String title, String subTitle, int duration) {
        super(context, R.style.dialog);
        this.mResId = resId;
        this.mContext = context;
        this.mTitle = title;
        this.mSubTitle = subTitle;
        if (duration > 0) {
            Log.d(mTag, "The auto disappear time you set is: " + duration);
            this.mDuration = duration;
        } else {
            Log.d(mTag, "The duration of automatic disappearance must be greater than 0. your: --> " + duration);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    // 动画的处理
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ANIMA:
                    dismiss();
                    break;
            }
        }
    };

    private void init() {
        mHandler.sendEmptyMessageDelayed(ANIMA, mDuration);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.le_auto_hide_alert_dialog, null);
        setContentView(view);

        TextView dialogTitle = (TextView)findViewById( R.id.dialog_title);
        TextView subTitle = (TextView)findViewById( R.id.dialog_sub_title);
        mImage = (ImageView)findViewById(R.id.le_image);
        if (mResId != 0) {
            mImage.setImageResource(mResId);
        } else {
            Log.e(mTag, " Your resId is 0 ");
        }


        // 设置取消按钮
        if (!TextUtils.isEmpty(mTitle)){
            dialogTitle.setVisibility(View.VISIBLE);
            dialogTitle.setText(mTitle);
        }else {
            dialogTitle.setVisibility(View.GONE);
            Log.d(mTag, " You have not set a title! ");
        }

        // 设置确定按钮
        if (!TextUtils.isEmpty(mSubTitle)){
            subTitle.setVisibility(View.VISIBLE);
            subTitle.setText(mSubTitle);
        }else {
            subTitle.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
            if (mHandler != null){
                mHandler.removeMessages(ANIMA);
                mHandler.removeCallbacksAndMessages(null);
                mHandler = null;
            }
        super.onStop();
    }
}
