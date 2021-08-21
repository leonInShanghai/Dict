package com.example.dict.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by 公众号：IT波 on 2021/8/8 Copyright © Leon. All rights reserved.
 * Functions: 改变状态栏字体颜色工具类
 */
public class StatusBarFontColor {

    public static void changeFontColorBlack(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //获取窗口区域
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //设置显示为白色背景，黑色字体
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
