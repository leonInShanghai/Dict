package com.example.dict.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dict.R;

/**
 * Created by 公众号：IT波 on 2021/8/15 Copyright © Leon. All rights reserved.
 * Functions: 设置页面
 */
public class SettingActivity extends BaseActivity {

    // 避免快速点击开启两个界面
    private boolean mIsFirstClick;

    @Override
    protected void onResume() {
        super.onResume();
        mIsFirstClick = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    /**
     * xml布局中设置点击事件
     * @param view
     */
    public void onClick(View view) {

        // 避免快速点击开启两个界面
        if (mIsFirstClick) {
            return;
        }
        mIsFirstClick = true;

        switch (view.getId()) {
            case R.id.setting_iv_back:
                finish();
                break;
            case R.id.setting_tv_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.setting_tv_collect:
                Intent intent = new Intent(this, CollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_tv_feedback:
                mIsFirstClick = false;
                break;
            case R.id.setting_tv_good:
                mIsFirstClick = false;
                break;
            case R.id.setting_tv_share:
                shareSoftware();
                break;
        }
    }

    private void shareSoftware() {
        // 分享这个软件到其他用户
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String msg = "想随时查找汉字和成语详细内容吗？快来下载「波波字典」APP吧...";
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(Intent.createChooser(intent, "分享到..."));
    }
}