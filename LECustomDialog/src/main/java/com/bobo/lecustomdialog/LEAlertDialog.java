package com.bobo.lecustomdialog;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
public class LEAlertDialog extends Dialog implements View.OnClickListener {

    private String mTag = "LEAlertDialog";

    private Context mContext;
    private String mTitle;
    private String mDialogOkTitle;
    private String mDialogCancelTitle;
    // dialog上图片（logo）不转，正转，反转
    private State mRotation = State.NONE;
    private Button mDialogOk;
    private Button mDialogCancel;

    public enum State {
        NONE, // 不转
        CLOCKWISE, // 正转
        ANTI_CLOCKWISE // 反转
    }

    //要执行动画的imageview
    private ImageView le_image;

    private static final int ANIMA = 20218;

    // 使用这个dialog的类必须实现这个接口
    private ClickListenerInterface mListener;

    /***
     * @param context 上下文
     * @param title 标题例如：您胜利了
     * @param dialogOkTitle 左边按钮的标题  传 null 不显示本按钮
     * @param dialogCancelTitle 右边按钮的标题 传 null 不显示本按钮
     * @param rotation 旋转的方向true为正传 false为反转
     */
    public LEAlertDialog(Context context, String title,
                         String dialogOkTitle, String dialogCancelTitle, State rotation) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.mTitle = title;
        this.mDialogOkTitle = dialogOkTitle;
        this.mDialogCancelTitle = dialogCancelTitle;
        this.mRotation = rotation;
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
                    Animation tipAnim1; // 正转动画
                    Animation tipAnim2; // 倒转动画
                    if (mRotation == State.CLOCKWISE){
                        tipAnim1 = AnimationUtils.loadAnimation(mContext, R.anim.small_tip_animation);
                        le_image.startAnimation(tipAnim1);
                    } else if (mRotation == State.ANTI_CLOCKWISE) {
                        tipAnim2 = AnimationUtils.loadAnimation(mContext, R.anim.small_back_animation);
                        le_image.startAnimation(tipAnim2);
                    } else if (mRotation == State.NONE) {
                        // 不需要旋转
                        mHandler.removeCallbacksAndMessages(null);
                        return;
                    }
                    sendEmptyMessageDelayed(ANIMA,3000);
                    break;
            }
        }
    };

    private void init() {
        mHandler.sendEmptyMessage(ANIMA);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.le_alertdialog, null);
        setContentView(view);

        TextView dialogTitle = (TextView)findViewById( R.id.dialog_title);
        mDialogOk = (Button)findViewById( R.id.dialog_ok);
        mDialogCancel = (Button)findViewById( R.id.dialog_cancel);
        le_image = (ImageView)findViewById(R.id.le_image);

        if (TextUtils.isEmpty(mTitle)) {
            Log.d(mTag, " You have not set a title! ");
        } else {
            dialogTitle.setText(mTitle);
        }

        // 设置确定按钮
        if (!TextUtils.isEmpty(mDialogOkTitle)){
            mDialogOk.setVisibility(View.VISIBLE);
            mDialogOk.setText(mDialogOkTitle);
            mDialogOk.setOnClickListener(this);
        }else {
            mDialogOk.setVisibility(View.GONE);
        }

        // 设置取消按钮
        if (!TextUtils.isEmpty(mDialogCancelTitle)){
            mDialogCancel.setVisibility(View.VISIBLE);
            mDialogCancel.setText(mDialogCancelTitle);
            mDialogCancel.setOnClickListener(this);
        }else {
            mDialogCancel.setVisibility(View.GONE);
        }
    }

    /**
     * 点击事件的处理
     */
    @Override
    public void onClick(View view) {
        if (view == mDialogOk) {
            if (mListener != null) {
                mListener.doConfirm();
            }
        } else if (view == mDialogCancel) {
            if (mListener != null) {
                mListener.doCancel();
            }
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

    /**
     * 供外界设置的点击事件回调接口
     * @param clickListenerInterface
     */
    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.mListener = clickListenerInterface;
    }

    public interface ClickListenerInterface {

        /**
         * 用户点击了确定按钮
         */
        void doConfirm();

        /**
         * 用户点击了取消按钮
         */
        void doCancel();
    }

}
