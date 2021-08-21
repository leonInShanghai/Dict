package com.example.dict.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * Created by 公众号：IT波 on 2021/8/14 Copyright © Leon. All rights reserved.
 * Functions: 因为我们需要再ScrollView中嵌套GridView只显示一行， 此类用于解决这个问题
 * 之前我写过类似：https://github.com/leonInShanghai/NewsReader/blob/master/app/src/main/java/com/bobo520/newsreader/weiget/FitHeightGridView.java
 */
public class MyGridView extends GridView {

    public MyGridView(Context context) {
        this(context, null);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 重写onMeasure方法，可以使其不出现滚动条，直接显示全部数据
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int spec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, spec);
    }
}
