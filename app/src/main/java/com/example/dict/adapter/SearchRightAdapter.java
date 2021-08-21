package com.example.dict.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dict.R;
import com.example.dict.bean.PinBuWordBean;

import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/7/25 Copyright © Leon. All rights reserved.
 * Functions: 拼音查询/部首查询Activity（右边）PullToRefreshGridView的适配器
 */
public class SearchRightAdapter extends BaseAdapter {

    private Context mContext;
    private List<PinBuWordBean.ResultBean.ListBean> mDatas;

    private LayoutInflater mInflater;

    public SearchRightAdapter(Context context, List<PinBuWordBean.ResultBean.ListBean> datas) {
        mContext = context;
        mDatas = datas;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_search_gv, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PinBuWordBean.ResultBean.ListBean bean = mDatas.get(position);
        String zi = bean.getZi();
        holder.tv.setText(zi);
        return convertView;
    }

    static class ViewHolder {
        private TextView tv;
        public ViewHolder(View view) {
            tv = view.findViewById(R.id.item_sgv_tv);
        }
    }
}
