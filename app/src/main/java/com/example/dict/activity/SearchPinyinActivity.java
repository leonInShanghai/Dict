package com.example.dict.activity;


import android.os.Bundle;

import com.example.dict.bean.PinBuWordBean;
import com.example.dict.db.DBManager;
import com.example.dict.utils.CommonUtils;
import com.example.dict.utils.URLUtils;

import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/7/18 Copyright © Leon. All rights reserved.
 * Functions: 通过拼音查找对应汉字
 */
public class SearchPinyinActivity extends BaseSearchActivity {

    // 获取指定拼音对应的网址
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(CommonUtils.FILE_PINYIN, CommonUtils.TYPE_PINYIN);
        setExlvListener(CommonUtils.TYPE_PINYIN);
        exLv.expandGroup(0); // 默认展开第一组
        // 默认进去时获取的第一个是a
        word = "a";
        url = URLUtils.getPinyinUrl(word, page, pageSize);
        // 加载网络数据
        loadData(url);
        setGVListener(CommonUtils.TYPE_PINYIN);
    }

    /**
     * 重写父类 请求失败回调
     * 网络获取失败时会调用的接口
     * 因为拼音查询和部首查询使用的获取数据方法不一样，所以需要两个子类分开写
     * @param ex
     * @param isOnCallback
     */
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        super.onError(ex, isOnCallback);
        // 2021-8-21新增加数据加载中loadding...
        if (mAlterDiaglog != null && mAlterDiaglog.isShowing()) {
            mAlterDiaglog.dismiss();
        }
        List<PinBuWordBean.ResultBean.ListBean> list = DBManager.queryPyWordFromPywordtb(word, page, pageSize);
        // 数据的显示
        refreshDataByGv(list);
    }
}