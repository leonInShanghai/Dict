package com.example.dict.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;


/**
 * Created by 公众号：IT波 on 2021/7/26 Copyright © Leon. All rights reserved.
 * Functions: SQLlite数据库操作
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context) {
        /**
         * name数据库的名称
         */
        super(context, "dict.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建拼音部首的表格
        String sql1 = "create table pywordtb(_id integer primary key autoincrement,id varchar(20),zi varchar(4) unique not null," +
                "py varchar(10),wubi varchar(10),pinyin varchar(10),bushou varchar(4), bihua integer)";
        db.execSQL(sql1);
        // 创建存储文字详情的表格
        String sql2 = "create table wordtb(_id integer primary key autoincrement,id varchar(20),zi varchar(4) unique not null," +
                "py varchar(10),wubi varchar(10),pinyin varchar(10),bushou varchar(4),bihua integer,jijie text, xiangjie text)";
        db.execSQL(sql2);

        // 存储成语表
        String sql3 = "create table cytb(_id integer primary key autoincrement, chengyu varchar(10) unique not null, " +
                "bushou varchar(4), head varchar(4), pinyin varchar(30), chengyujs varchar(150), from_ text, example " +
                "text, yufa varchat(60), ciyujs text, yinzhengjs text, tongyi text, fanyi text)";
        db.execSQL(sql3);

        // 存储收藏文字的表
        String sql = "create table collwordtb(_id integer primary key autoincrement, zi varchar(4) unique not null)";
        db.execSQL(sql);

        // 存储收藏成语的表
        String sql0 = "create table collcytb(_id integer primary key autoincrement, chengyu varchar(16) unique not null)";
        db.execSQL(sql0);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
