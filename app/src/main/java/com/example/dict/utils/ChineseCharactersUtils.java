package com.example.dict.utils;

import android.util.Log;

import com.example.dict.activity.BaseSearchActivity;
import com.example.dict.bean.PinBuWordBean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 公众号：IT波 on 2021/8/21 Copyright © Leon. All rights reserved.
 * Functions: 汉字工具类， 判断是否是汉字
 */
public class ChineseCharactersUtils {

    // 静态内部类单例 《Effective Java》上所推荐的。
    private static class SingletonHolder {
        private static final ChineseCharactersUtils INSTANCE = new ChineseCharactersUtils();
    }

    private ChineseCharactersUtils() {
        Log.d("ChineseCharactersUtils", " 被实例化了 此类是单例只能打印一次!");
    }

    public static final ChineseCharactersUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private ChineseInTraversalSetListener mListener;

    public void setListener(ChineseInTraversalSetListener listener) {
        mListener = listener;
    }

    /**
     * 判断是否是汉字（这里会把生僻字判断为不是汉字）
     * @param countname
     * @return
     */
    public boolean checkChineseCharacters(String countname) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(countname);
        if (m.find()) {
            return true;
        }
        return false;
    }


    /**
     * 有些汉字安卓系统和微软系统都显示不出来如： "zi":"", 这个方法就是将它们过滤掉 子线程中操作完成后自动回到主线程
     * @param list
     * @param activity
     */
    public void chineseInTraversalSet(List<PinBuWordBean.ResultBean.ListBean> list, BaseSearchActivity activity) {

        final List<PinBuWordBean.ResultBean.ListBean> tempList = new ArrayList<>();

        Log.d("ChineseCharactersUtils", "before 线程名称：" + Thread.currentThread().getName());
        for (PinBuWordBean.ResultBean.ListBean bean : list) {
            if (bean.getZi() != null && checkChineseCharacters(bean.getZi())) {
                tempList.add(bean);
            } else {
                Log.d("ChineseCharactersUtils", "过滤掉不可显示汉字 --> " + bean.getZi());
            }
        }

        if (mListener != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("ChineseCharactersUtils", "after 线程名称：" + Thread.currentThread().getName());
                    mListener.onTraversalComplete(tempList);
                }
            });
        }

    }

    public interface ChineseInTraversalSetListener {
        void onTraversalComplete(List<PinBuWordBean.ResultBean.ListBean> list);
    }

}
