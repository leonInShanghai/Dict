package com.example.dict;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bobo.lecustomdialog.LEAlertContentLoadingView;
import com.example.dict.adapter.SearchLeftAdapter;
import com.example.dict.adapter.SearchRightAdapter;
import com.example.dict.bean.PinBuBean;
import com.example.dict.bean.PinBuWordBean;
import com.example.dict.db.DBManager;
import com.example.dict.utils.AssetsUtils;
import com.example.dict.utils.ChineseCharactersUtils;
import com.example.dict.utils.CommonUtils;
import com.example.dict.utils.URLUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/7/18 Copyright © Leon. All rights reserved.
 * Functions: 通过部首或拼音查找对应汉字
 */
public class BaseSearchActivity extends BaseActivity implements ChineseCharactersUtils.ChineseInTraversalSetListener {

    private static final String TAG = "SearchPinyinActivity";

    protected ExpandableListView exLv;
    private PullToRefreshGridView pullGv;
    protected TextView titleTv;

    // ExpandableListView外层数据源 [A, B, C, D...]
    private List<String> gropDatas;

    // ExpandableListView内层数据源 [A[a,ai,ao..]...] 二维的
    private List<List<PinBuBean.ResultBean>> childDatas;

    // 抽取成员变量快捷键 ctrl+alt+f
    private SearchLeftAdapter mAdapter;
    // 表示被点击的组的位置
    private int selGroupPos = 0;
    // 表示选中组中的子item位置
    private int selChildPos = 0;

    // 右侧的GridView的数据源
    protected List<PinBuWordBean.ResultBean.ListBean> gridDatas;
    // 右侧的GridView's 适配器
    private SearchRightAdapter gridAdapter;

    // 总页数
    protected int totalPage;

    // 当前获取的页数，默认1
    protected int page = 1;

    // 每页返回条数，默认10 最大50
    protected int pageSize = 50;

    // 表示我们点击了左侧的哪个拼音或者部首
    protected String word = "";

    // 网络请求的url 如：https://v.juhe.cn/xhzd/querybs?key=3022583457067131a719f84d10efd275&word=%E5%85%AB
    private String url = "";

    // loading弹窗2021-8-21增加
    protected AlertDialog mAlterDiaglog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pinyin);
        exLv = findViewById(R.id.searchpy_elv);
        pullGv = findViewById(R.id.searchpy_gv);
        titleTv = findViewById(R.id.searchpy_tv);
        // 初始化GridView的数据源内容
        initGridDatas();
    }

    /**
     * 初始化GridView的数据源
     */
    public void initGridDatas() {
        gridDatas = new ArrayList<>();
        // GridView设置适配器  .field会自动生成成员变量
        gridAdapter = new SearchRightAdapter(this, gridDatas);
        pullGv.setAdapter(gridAdapter);
    }

    /**
     * 设置PullToRefreshGridView上拉加载更多监听，item点击listener
     */
    protected void setGVListener(final int type) {
        // 上拉加载更多
        pullGv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        pullGv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                // 判断当前加载的页数，是否小于等于总页数
                if (page < totalPage) {
                    page++;
                    if (type == CommonUtils.TYPE_PINYIN) {
                        url = URLUtils.getPinyinUrl(word, page, pageSize);
                    } else if (type == CommonUtils.TYPE_BUSHOU) {
                        url = URLUtils.getBushouUrl(word, page, pageSize);
                    }
                    loadData(url);
                } else {
                    // 关闭上拉加载更多
                    pullGv.onRefreshComplete();
                    pullGv.setPullLabel("没有更多数据...");
                }
            }
        });

        // 点击每一项能够跳转到详情页面的监听
        pullGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 跳转到文字查询详情页面
                PinBuWordBean.ResultBean.ListBean bean = gridDatas.get(position);
                String zi = bean.getZi();
                Intent intent = new Intent(getBaseContext(), WordInfoActivity.class);
                intent.putExtra("zi", zi);
                startActivity(intent);
            }
        });
    }

    /**
     * 重写父类 请求成功回调，拼音和部首返回的json数据格式是一样的解析到相同的集合里面
     * @param result 获取到的json数据
     */
    @Override
    public void onSuccess(String result) {
        super.onSuccess(result);
        // {"resultcode":"112","reason":"超过每日可允许请求次数!","result":null,"error_code":10012}
        PinBuWordBean bean = new Gson().fromJson(result, PinBuWordBean.class);
        PinBuWordBean.ResultBean resultBean = bean.getResult();
        // 将当前获取数据的总页数进行保存
        totalPage = resultBean.getTotalpage();
        final List<PinBuWordBean.ResultBean.ListBean> list = resultBean.getList();

        // 2021-8-21新增加数据加载中loadding...
        if (mAlterDiaglog != null && mAlterDiaglog.isShowing()) {
            mAlterDiaglog.dismiss();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                ChineseCharactersUtils.getInstance().setListener(BaseSearchActivity.this);
                ChineseCharactersUtils.getInstance()
                        .chineseInTraversalSet(list, BaseSearchActivity.this);
            }
        }).start();
    }

    /**
     * onSuccess 后 并将返回的集合做了处理，生僻字去除
     * @param list
     */
    @Override
    public void onTraversalComplete(List<PinBuWordBean.ResultBean.ListBean> list) {
        // 显示数据
        refreshDataByGv(list);
        // 将加载到的网络数据，写入到数据库中没有网络的时候就用数据库
        writeDBByThrend(list);
    }

    /**
     * 将网络数据保存到数据库中，为了避免ANR，就使用子线程，完成操作
     * @param list
     */
    private void writeDBByThrend(final List<PinBuWordBean.ResultBean.ListBean> list) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Log.d(TAG, "writeDBByThrend 线程名称：" + Thread.currentThread().getName());
                DBManager.insterListToPywordtb(list);
            }
        }).start();
    }

    /**
     * 更新GridView当中的数据，提示适配器重新加载
     * @param list
     */
    protected void refreshDataByGv(List<PinBuWordBean.ResultBean.ListBean> list) {
        if (page == 1) {
            // 加载了新的拼音或者部首对应的集合
            gridDatas.clear();
            gridDatas.addAll(list);
            gridAdapter.notifyDataSetChanged();
        } else {
            // 进行上拉加载获取到的数据,要保留原来的数据
            gridDatas.addAll(list);
            gridAdapter.notifyDataSetChanged();
            // 停止显示加载条
            pullGv.onRefreshComplete();
        }
    }

    /**
     * 设置ExpandListView item 点击事件
     */
    protected void setExlvListener(final int type) {
        exLv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long l) {
                mAdapter.setSelectGroupPosition(groupPosition);
                // 获取被点击位置的内容
                selGroupPos = groupPosition;
                // int childSize = childDatas.get(selGroupPos).size();
                // if (childSize <= selChildPos) {
                //     selChildPos = childSize -1;
                //     mAdapter.setSelectGroupPosition(selChildPos);
                // }
                mAdapter.notifyDataSetInvalidated(); // 数据没有改变，只是布局背景改变了用这个方法刷新
                // 获取数据信息
                // getDataAlterWord(type);
                return false;
            }
        });

        exLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition
                    , long id) {
                // mAdapter.setSelectGroupPosition(groupPosition);
                // mAdapter.setSelectChildPosition(childPosition);
                mAdapter.setSelectChildPosition(groupPosition, childPosition);
                mAdapter.notifyDataSetInvalidated(); // 数据没有改变，只是布局背景改变了用这个方法刷新
                selGroupPos = groupPosition;
                selChildPos = childPosition;
                // 网络加载右边GridView显示的内容
                getDataAlterWord(type);
                return false;
            }
        });
    }

    /**
     * 改变了左边的选中，从网络获取对应选中的结果，显示在右边
     */
    private void getDataAlterWord(int type) {

//        List<PinBuBean.ResultBean> groupList = childDatas.get(selGroupPos);
//        page = 1;
//        PinBuBean.ResultBean bean = groupList.get(selChildPos);
//        if (type == CommonUtils.TYPE_PINYIN) {
//            word = bean.getPinyin();
//            url = URLUtils.getPinyinUrl(word, page, pageSize);
//        } else if (type == CommonUtils.TYPE_BUSHOU) {
//            word = bean.getBihua();
//            url = URLUtils.getBushouUrl(word, page, pageSize);
//        }
//        loadData(url);

        page = 1;
        PinBuBean.ResultBean bean = childDatas.get(selGroupPos).get(selChildPos);
        if (type == CommonUtils.TYPE_PINYIN) {
            word = bean.getPinyin();
            url = URLUtils.getPinyinUrl(word, page, pageSize);
        } else if (type == CommonUtils.TYPE_BUSHOU) {
            word = bean.getBushou();
            url = URLUtils.getBushouUrl(word, page, pageSize);
        }

        if (mAlterDiaglog != null) {
            mAlterDiaglog.show();
        }
        loadData(url);
    }

    /**
     * 读取Assets文件夹中的数据，使用Gson解析
     * @param assetsName 文件名例如 "pinyin.text"
     * @param type 类型 有拼音0 部首1
     */
    public void initData(String assetsName, int type) {

        // 2021-8-21增加logding弹窗出现
        if (mAlterDiaglog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog)
                    .setCancelable(false)
                    .setView(new LEAlertContentLoadingView(this));
            mAlterDiaglog = builder.show();
        } else {
            // 已经创建了直接显示
            mAlterDiaglog.show();
        }

        gropDatas = new ArrayList<>();
        childDatas = new ArrayList<>();
        // Assets文件夹中获取数据
        String json = AssetsUtils.getAssetsContent(this, assetsName);
        if (!TextUtils.isEmpty(json)) {
            PinBuBean pinBuBean = new Gson().fromJson(json, PinBuBean.class);
            List<PinBuBean.ResultBean> list = pinBuBean.getResult();
            // 整理数据
            List<PinBuBean.ResultBean> groupList = new ArrayList<>(); // 声明每组包含的元素集合
            for (int i = 0; i < list.size(); i++) {
                PinBuBean.ResultBean bean = list.get(i);
                if (type == CommonUtils.TYPE_PINYIN) {
                    String pinyin_key = bean.getPinyin_key(); // 获取一维数组的大写字母
                    if (!gropDatas.contains(pinyin_key)) { // 判断是否存在于分组的列表当中
                        gropDatas.add(pinyin_key);
                        // 说明上一个拼音的已经全部录入groupList当中了，可以将上一个拼音的集合添加到childDatas
                        if (groupList.size() > 0) {
                            childDatas.add(groupList);
                        }
                        // 新的一组，要创建对应子列表
                        groupList = new ArrayList<>();
                        groupList.add(bean);
                    } else {
                        groupList.add(bean); // 大写字母已经存在说明还在当前这组当中，可以直接添加
                    }
                } else if (type == CommonUtils.TYPE_BUSHOU) {
                    String bihua = bean.getBihua(); // 获取一维数组的笔画
                    if (!gropDatas.contains(bihua)) { // 判断是否存在于分组的列表当中
                        gropDatas.add(bihua);
                        // 说明上一个笔画的已经全部录入groupList当中了，可以将上一个笔画的集合添加到childDatas
                        if (groupList.size() > 0) {
                            childDatas.add(groupList);
                        }
                        // 新的一组，要创建对应子列表
                        groupList = new ArrayList<>();
                        groupList.add(bean);
                    } else {
                        groupList.add(bean); // 笔画已经存在说明还在当前这组当中，可以直接添加
                    }
                }
            }

            // 循环结束之后，最后一组并没有添加进去，所以需要手动添加
            childDatas.add(groupList);
            Log.d(TAG, "initData: assetsName:" + assetsName + " gropDatas size:" + gropDatas.size());
            Log.d(TAG, "initData: assetsName:" + assetsName + " childDatas size :" + childDatas.size());

            mAdapter = new SearchLeftAdapter(this, gropDatas, childDatas, type);
            exLv.setAdapter(mAdapter);
        }
    }

    /**
     * 在xml中设置的点击事件
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_iv_back:
                // 用户点击了左上角的返回按钮
                finish();
                break;
        }
    }
}

