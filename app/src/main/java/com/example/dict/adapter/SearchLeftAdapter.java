package com.example.dict.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.dict.R;
import com.example.dict.bean.PinBuBean;
import com.example.dict.utils.CommonUtils;

import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/7/24 Copyright © Leon. All rights reserved.
 * Functions: ExpandableListView 的适配器
 */
public class SearchLeftAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    // 表示分组的列表
    private List<String> groupDatas;
    // 子列表数据源
    private List<List<PinBuBean.ResultBean>> childDatas;

    private LayoutInflater inflater;
    private int type; // 因为拼音和部首都用此适配器，所以通过这个属性进行类型区分

    // 用户选中的 Group item（改变颜色）默认选中第0个
    private int selectGroupPosition = 0;

    // 用户选中的 Child item（改变颜色）默认选中第0个
    private int selectChildPosition = 0;


    public void setSelectGroupPosition(int selectGroupPosition) {
        // 注释了下面这句解决一个bug为啥用户展开新组item还没有选子类程序就已经帮用户选择了一个子item
        // this.selectGroupPosition = selectGroupPosition;
    }

    // 新方法代替 setSelectChildPosition(int selectGroupPosition, int selectChildPosition)
    // public void setSelectChildPosition(int selectChildPosition) {
    //     this.selectChildPosition = selectChildPosition;
    // }

    // 这样做解决一个bug为啥用户展开新组item还没有选子类程序就已经帮用户选择了一个子item
    public void setSelectChildPosition(int selectGroupPosition, int selectChildPosition) {
        this.selectGroupPosition = selectGroupPosition;
        this.selectChildPosition = selectChildPosition;
    }

    public SearchLeftAdapter(Context context, List<String> groupDatas, List<List<PinBuBean.ResultBean>> childDatas,
                              int type) {
        mContext = context;
        this.groupDatas = groupDatas;
        this.childDatas = childDatas;
        this.type = type;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 获取分组的个数
     * @return
     */
    @Override
    public int getGroupCount() {
        return groupDatas == null ? 0 : groupDatas.size();
    }

    /**
     * 获取指定子列表item个数
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        if (childDatas == null) {
            return 0;
        }
        return childDatas.get(groupPosition) == null ? 0 : childDatas.get(groupPosition).size();
    }

    /**
     * 获取分组指定位置的数据
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return groupDatas.get(groupPosition);
    }

    /**
     * 给出第几组第几个，求出对应的子对象
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childDatas.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_exlv_group, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        // 获取指定位置的数据并显示
        String word = groupDatas.get(groupPosition);
        if (type == CommonUtils.TYPE_PINYIN) {
            holder.groupTv.setText(word);
        } else if (type == CommonUtils.TYPE_BUSHOU) {
            holder.groupTv.setText(word + " 划");
        }


        // 选中位置改变颜色
        if (selectGroupPosition == groupPosition) {
            convertView.setBackgroundColor(Color.BLACK);
            holder.groupTv.setTextColor(Color.RED);
        } else {
            convertView.setBackgroundResource(R.color.grey_f3f3f3);
            holder.groupTv.setTextColor(Color.BLACK);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_exlv_child, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder)convertView.getTag();
        }

        PinBuBean.ResultBean bean = childDatas.get(groupPosition).get(childPosition);
        if (type == CommonUtils.TYPE_PINYIN) {
            holder.childTv.setText(bean.getPinyin());
        } else if (type == CommonUtils.TYPE_BUSHOU) {
            holder.childTv.setText(bean.getBushou());
        }
        
        // 设置改变选择子item的颜色
        if (selectGroupPosition == groupPosition && selectChildPosition == childPosition) {
            convertView.setBackgroundColor(Color.WHITE);
            holder.childTv.setTextColor(Color.RED);
        } else {
            convertView.setBackgroundResource(R.color.grey_f3f3f3);
            holder.childTv.setTextColor(Color.BLACK);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        // 子类是否可以被选中  false不可以 true可以
        return true;
    }

    static class GroupViewHolder {

        private TextView groupTv;

        public GroupViewHolder(View view) {
            this.groupTv = view.findViewById(R.id.item_group_tv);
        }
    }

    static class ChildViewHolder {

        private TextView childTv;

        public ChildViewHolder(View view) {
            childTv = view.findViewById(R.id.item_child_tv);
        }
    }
}
