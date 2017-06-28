package com.example.https.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.https.R;
import com.example.https.entity.Databean;
import com.example.https.ui.LeftFragment;

import java.util.List;
import java.util.Random;

/**
 * Created by yangfan on 2017/5/9.
 */

public class MyExpanAdapter extends BaseExpandableListAdapter {
    private LeftFragment mLeftFragment;
    private LayoutInflater mInflater;
    private List<Databean> mDatabeanList;
    private int[] mPicInt;
    private Random mRandom;

    public MyExpanAdapter(LeftFragment leftFragment, List<Databean> databeanList) {
        mLeftFragment = leftFragment;
        mDatabeanList = databeanList;
        mPicInt=new int[]{R.mipmap.buluke,R.mipmap.hongtoufa,R.mipmap.ji,R.mipmap.lufei,R.mipmap.luobin,
                R.mipmap.miao,R.mipmap.namei,R.mipmap.nvdi,R.mipmap.shanzhi,R.mipmap.shen,R.mipmap.suolong
                ,R.mipmap.wusuopu,R.mipmap.yan};
        mRandom=new Random(mPicInt.length);
        mInflater = LayoutInflater.from(leftFragment.getActivity());
    }

    public void refresh(){

    }

    @Override
    public int getGroupCount() {
        return mDatabeanList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mDatabeanList.get(i).getList().size();
    }

    @Override
    public Object getGroup(int i) {
        return mDatabeanList.get(i).getName();
    }

    @Override
    public Object getChild(int i, int i1) {
        return mDatabeanList.get(i).getList().get(i1).getNetContent();
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;

    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ParentViewHolder parentVh = null;
        if (view == null) {
            parentVh = new ParentViewHolder();
            view = mInflater.inflate(R.layout.parent_group, viewGroup, false);
            parentVh.mImageView = (ImageView) view.findViewById(R.id.parent_imgv);
            parentVh.mTextView = (TextView) view.findViewById(R.id.parent_tv);
            view.setTag(parentVh);
        } else {
            parentVh = (ParentViewHolder) view.getTag();
        }
        parentVh.mTextView.setText(mDatabeanList.get(i).getName());
        parentVh.mImageView.setImageResource(mDatabeanList.get(i).getImgvId());
        return view;
    }

    @Override
    public View getChildView(int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder = null;
        if (view == null) {
            childViewHolder = new ChildViewHolder();
            view = mInflater.inflate(R.layout.child_group, viewGroup, false);
            childViewHolder.mTextView = (TextView) view.findViewById(R.id.child_tv);
            childViewHolder.mImageView = (ImageView) view.findViewById(R.id.child_imgvDelete);
            childViewHolder.mTextNum= (TextView) view.findViewById(R.id.child_tvNum);
            childViewHolder.mImageViewTitle= (ImageView) view.findViewById(R.id.child_imgvTitle);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        childViewHolder.mTextView.setText(mDatabeanList.get(i).getList().get(i1).getNetContent());
        childViewHolder.mTextView.setTag(mDatabeanList.get(i).getList().get(i1).getNetContent());
        childViewHolder.mImageViewTitle.setImageResource(mPicInt[mRandom.nextInt(mPicInt.length)]);
        childViewHolder.mTextNum.setText(i1+"");
        childViewHolder.mTextView.setOnClickListener(mLeftFragment);
        childViewHolder.mImageView.setTag(mDatabeanList.get(i).getList().get(i1).getNetContent());
        childViewHolder.mImageView.setOnClickListener(mLeftFragment);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    private class ParentViewHolder {
        private ImageView mImageView;
        private TextView mTextView;
    }

    private class ChildViewHolder {
        private ImageView mImageView;
        private ImageView mImageViewTitle;
        private TextView mTextView;
        private TextView mTextNum;
    }


}

