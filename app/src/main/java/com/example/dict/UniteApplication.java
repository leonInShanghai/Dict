package com.example.dict;

import android.app.Application;

import com.example.dict.db.DBManager;

import org.xutils.x;

/**
 * Created by 公众号：IT波 on 2021/7/25 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class UniteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this); // 初始化xUtils模块数据
        // 初始化数据库对象
        DBManager.initDB(this);
    }
}
