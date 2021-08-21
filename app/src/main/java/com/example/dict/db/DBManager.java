package com.example.dict.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.example.dict.bean.ChengyuBean;
import com.example.dict.bean.PinBuWordBean;
import com.example.dict.bean.WordBean;
import com.example.dict.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/7/26 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class DBManager {

    private static SQLiteDatabase db;

    public static void initDB(Context context) {
        DBOpenHelper helper = new DBOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    /**
     * 执行插入数据到 pywordtb 当中
     * 插入很多对象所在的集合
     */
    public static void insterListToPywordtb(List<PinBuWordBean.ResultBean.ListBean> list) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                // 获取集合当中的每一个对象
                PinBuWordBean.ResultBean.ListBean bean = list.get(i);
                try {
                    // 调用单个对象插入的方法
                    insertWordToPywordtb(bean);
                } catch (Exception e) {
                    Log.e("DBManager", "insterListToPywordtb bean 已经存在:" + bean.toString());
                    Log.e("DBManager", "insterListToPywordtb e:" + e.toString());
                }
            }
        }
    }

    /**
     * 执行插入数据到 pywordtb 当中
     * 插入一个对象的方法
     */
    public static void insertWordToPywordtb(PinBuWordBean.ResultBean.ListBean bean) {
        ContentValues valuses = new ContentValues();
        Log.d("DBManager", "insertWordToPywordtb bean:" + bean.toString());
        valuses.put("id", bean.getId());
        valuses.put("zi", bean.getZi());
        valuses.put("py", bean.getPy());
        valuses.put("wubi", bean.getWubi());
        valuses.put("pinyin", bean.getPinyin());
        valuses.put("bushou", bean.getBushou());
        valuses.put("bihua", bean.getBihua());
        db.insert(CommonUtils.TABLE_PYWORDTB, null, valuses);
        // 这里用不要用replace replace 数据库中已经存在就插入否则替换，这里我们只要插入有了就不要再插入了
        // db.replace(CommonUtils.TABLE_PYWORDTB, null, valuses);
    }

    /**
     * 执行查询pywordtb 当中的指定拼音数据
     */
    public static List<PinBuWordBean.ResultBean.ListBean> queryPyWordFromPywordtb(String py, int page, int pagesize) {
        List<PinBuWordBean.ResultBean.ListBean> list = new ArrayList<>();
        // 0-50 50-100
        String sql = "SELECT * FROM pywordtb where py =? or py like ? or py like ? or py like ? LIMIT ?,?";
        int start = (page -1) * pagesize;
        int end = page * pagesize;
        String type1 = py + ",%";
        String type2 = "%," + py + ",%";
        String type3 = "%," + py;
        Cursor cursor = db.rawQuery(sql,new String[]{py, type1, type2, type3, start+"", end+""});
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String zi = cursor.getString(cursor.getColumnIndex("zi"));
            String py1 = cursor.getString(cursor.getColumnIndex("py"));
            String wubi = cursor.getString(cursor.getColumnIndex("wubi"));
            String pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
            String bushou = cursor.getString(cursor.getColumnIndex("bushou"));
            String bihua = cursor.getString(cursor.getColumnIndex("bihua"));
            PinBuWordBean.ResultBean.ListBean bean = new PinBuWordBean.ResultBean.ListBean(id, zi, py1, wubi,
                    pinyin, bushou, bihua);
            list.add(bean);
        }
        return list;
    }

    /**
     * 执行查询pywordtb 当中的指定部首数据
     */
    public static List<PinBuWordBean.ResultBean.ListBean> queryBsWordFromPywordtb(String bs, int page, int pagesize) {
        List<PinBuWordBean.ResultBean.ListBean> list = new ArrayList<>();
        // 0-50 50-100
        String sql = "SELECT * FROM pywordtb where bushou =? LIMIT ?,?";
        int start = (page -1) * pagesize;
        int end = page * pagesize;
        Cursor cursor = db.rawQuery(sql,new String[]{bs, start+"", end+""});
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String zi = cursor.getString(cursor.getColumnIndex("zi"));
            String py1 = cursor.getString(cursor.getColumnIndex("py"));
            String wubi = cursor.getString(cursor.getColumnIndex("wubi"));
            String pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
            String bushou = cursor.getString(cursor.getColumnIndex("bushou"));
            String bihua = cursor.getString(cursor.getColumnIndex("bihua"));
            PinBuWordBean.ResultBean.ListBean bean = new PinBuWordBean.ResultBean.ListBean(id, zi, py1, wubi,
                    pinyin, bushou, bihua);
            list.add(bean);
        }
        return list;
    }

    /**
     * 插入汉字到汉字详情表
     */
    public static void insertWordToWordtb(WordBean.ResultBean bean) {
        ContentValues values = new ContentValues();
        values.put("id", bean.getId());
        values.put("zi", bean.getZi());
        values.put("py", bean.getPy());
        values.put("wubi", bean.getWubi());
        values.put("pinyin", bean.getPinyin());
        values.put("bushou", bean.getBushou());
        values.put("bihua", bean.getBihua());
        // 将List<String>转换成字符串类型,进行插入
        String jijie = listToString(bean.getJijie());
        values.put("jijie", jijie);
        String xiangjie = listToString(bean.getXiangjie());
        values.put("xiangjie", xiangjie);
        db.insert(CommonUtils.TABLE_WORDTB, null, values);
    }

    /**
     * 根据传入的汉字，查找对应信息
     */
    public static WordBean.ResultBean queryWordFromWordtb(String word) {
        String sql = "select * from wordtb where zi =?";
        Cursor cursor = db.rawQuery(sql, new String[]{word});
        while (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String zi = cursor.getString(cursor.getColumnIndex("zi"));
            String py = cursor.getString(cursor.getColumnIndex("py"));
            String wubi = cursor.getString(cursor.getColumnIndex("wubi"));
            String pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
            String bushou = cursor.getString(cursor.getColumnIndex("bushou"));
            String bihua = cursor.getString(cursor.getColumnIndex("bihua"));
            String jijie = cursor.getString(cursor.getColumnIndex("jijie"));
            String xiangjie = cursor.getString(cursor.getColumnIndex("xiangjie"));
            List<String> jijieList = stringToList(jijie);
            List<String> xiangjieList = stringToList(xiangjie);
            return new WordBean.ResultBean(id, zi, py, wubi, pinyin, bushou, bihua, jijieList, xiangjieList);
        }
        return null;
    }

    /**
     * 将成语对象插入到成语表中
     * @param bean
     */
    public static void insterCyToCyutb(ChengyuBean.ResultBean bean) {
        ContentValues values = new ContentValues();
        values.put("chengyu", bean.getChengyu());
        values.put("bushou", bean.getBushou());
        values.put("head", bean.getHead());
        values.put("pinyin", bean.getPinyin());
        values.put("chengyujs", bean.getChengyujs());
        values.put("from_", bean.getFrom_());
        values.put("example", bean.getExample());
        values.put("yufa", bean.getYufa());
        values.put("ciyujs", bean.getCiyujs());
        values.put("yinzhengjs", bean.getYinzhengjs());
        String tongYi = listToString(bean.getTongyi());
        values.put("tongyi", tongYi);
        String fanYi = listToString(bean.getFanyi());
        values.put("fanyi", fanYi);
        db.insert(CommonUtils.TABLE_CYTB, null, values);
    }

    /**
     * 根据传入的数据查看详情内容
     */
    public static ChengyuBean.ResultBean queryChengyuFromCytb(String cyu) {
        String sql = "select * from " + CommonUtils.TABLE_CYTB + " where chengyu =?";
        Cursor cursor = db.rawQuery(sql, new String[]{cyu});
        while (cursor.moveToFirst()) {
            String chengyu = cursor.getString(cursor.getColumnIndex("chengyu"));
            String bushou = cursor.getString(cursor.getColumnIndex("bushou"));
            String head = cursor.getString(cursor.getColumnIndex("head"));
            String pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
            String chengyujs = cursor.getString(cursor.getColumnIndex("chengyujs"));
            String from_ = cursor.getString(cursor.getColumnIndex("from_"));
            String example = cursor.getString(cursor.getColumnIndex("example"));
            String yufa = cursor.getString(cursor.getColumnIndex("yufa"));
            String ciyujs = cursor.getString(cursor.getColumnIndex("ciyujs"));
            String yinzhengjs = cursor.getString(cursor.getColumnIndex("yinzhengjs"));
            String tongyi = cursor.getString(cursor.getColumnIndex("tongyi"));
            String fanyi = cursor.getString(cursor.getColumnIndex("fanyi"));
            List<String> tongYi = stringToList(tongyi);
            List<String> fanYi = stringToList(fanyi);
            return new ChengyuBean.ResultBean(chengyu, bushou, head, pinyin, chengyujs, from_, example, yufa, ciyujs, yinzhengjs,
                    tongYi, fanYi);
        }
        return null;
    }

    /**
     * 插入收藏文字到collwordtb表中
     */
    public static void insertZiToCollwordtb (String zi) {
        ContentValues values = new ContentValues();
        values.put("zi", zi);
        db.insert(CommonUtils.TABLE_COLLECT_WORDTB, null, values);
    }

    /**
     * 删除字收藏表中的字
     * @param zi
     */
    public static void deleteZiToCollwordtb(String zi) {
        String sql = "delete from " + CommonUtils.TABLE_COLLECT_WORDTB + " where zi =?";
        // db.rawQuery(sql, new String[]{zi});
        db.execSQL(sql, new Object[]{zi});
    }

    /**
     * 查找此汉字是否存在于收藏文字表中
     */
    public static boolean isExistZiInCollwordtb(String zi) {
        String sql = "select * from " + CommonUtils.TABLE_COLLECT_WORDTB + " where zi =?";
        Cursor cursor = db.rawQuery(sql, new String[]{zi});
        // 大于0说明有数据否则反之
        return cursor.getCount() > 0;
    }

    /**
     * SQL查找用户收藏的所有汉字
     * @return
     */
    public static List<String> queryAllInCollwordtb() {
        String sql = "select * from " + CommonUtils.TABLE_COLLECT_WORDTB;
        Cursor cursor = db.rawQuery(sql, null);
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String zi = cursor.getString(cursor.getColumnIndex("zi"));
            list.add(zi);
        }
        return list;
    }

    /**
     * 插入收藏成语到collcytb表中
     */
    public static void insertCyuToCollcytb (String cy) {
        ContentValues values = new ContentValues();
        values.put("chengyu", cy);
        db.insert(CommonUtils.TABLE_COLLECT_CYTB, null, values);
    }

    /**
     * 删除字收藏表中的字
     */
    public static void deleteCyuToCollcytb(String cy) {
        String sql = "delete from " + CommonUtils.TABLE_COLLECT_CYTB + " where chengyu =?";
        // db.rawQuery(sql, new String[]{zi});
        db.execSQL(sql, new Object[]{cy});
    }

    /**
     * 查找此汉字是否存在于收藏文字表中
     */
    public static boolean isExistCyuInCollcytb(String cy) {
        String sql = "select * from " + CommonUtils.TABLE_COLLECT_CYTB + " where chengyu =?";
        Cursor cursor = db.rawQuery(sql, new String[]{cy});
        // 大于0说明有数据否则反之
        return cursor.getCount() > 0;
    }

    /**
     * SQL查找用户收藏的所有成语
     * @return
     */
    public static List<String> queryAllCyuInCollcyutb() {
        String sql = "select * from " + CommonUtils.TABLE_COLLECT_CYTB;
        Cursor cursor = db.rawQuery(sql, null);
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String chengyu = cursor.getString(cursor.getColumnIndex("chengyu"));
            list.add(chengyu);
        }
        return list;
    }

    /**
     * 查询成语表当中所有的记录
     * @return
     */
    public static List<String> queryAllCyFromCyutb() {
        List<String> cyAllList = new ArrayList<>();
        String sql = "select chengyu from " + CommonUtils.TABLE_CYTB;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String chengyu = cursor.getString(cursor.getColumnIndex("chengyu"));
            cyAllList.add(chengyu);
        }
        return cyAllList;
    }

    /**
     * 将String转换为List<String>
     * @param msg
     * @return
     */
    private static List<String> stringToList(String msg) {
        List<String> list = new ArrayList<>();
        if (!TextUtils.isEmpty(msg)) {
            String[] arr = msg.split("\\|");
            for (int i = 0; i < arr.length; i++) {
                String s = arr[i].trim();
                if (!TextUtils.isEmpty(s)) {
                    list.add(s);
                }
            }
        }
        return list;
    }

    /**
     * 将List<String> 转换为 String
     * @param list
     * @return
     */
    private static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size();i++) {
                String msg = list.get(i);
                msg += "|";
                sb.append(msg);
            }
        }
        return sb.toString();
    }
}
