package com.example.https.ui;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.example.https.R;
import com.example.https.adapter.MyExpanAdapter;
import com.example.https.entity.ChildMsg;
import com.example.https.entity.Databean;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeftFragment extends Fragment implements View.OnClickListener {
    private ExpandableListView mExpandableListView;
    private ImageView mImageView_sina;
    private ImageView mImageView_wangyi;
    private ImageView mImageView_tencent;
    private ImageView mImageView_douyu;
    private ImageView mImageView_MusicOrVideo;
    private ImageView mImageView_Menu;
    private ImageView mImageView_up;
    private ImageView mImageView_playOrPause;
    private ImageView mImageView_down;

    private boolean mIsMusic = true;
    private boolean mIsPlay = true;

    public MyExpanAdapter mAdapter;
    private String[] mStringsParent;
    private Main2Activity mActivity;
    private int[] mInts;
    private List mList;

    private List<Databean> parentList;
    private List<ChildMsg> childListOne;
    private List<ChildMsg> childListTwo;
    private List<ChildMsg> childListThree;
    private List<String> mStringList;

    private Databean mDatabean;
    private ChildMsg mChildMsg;
    private View mView;
    private int msizeCount = 0;
    private String url = "";

    public LeftFragment() {
    }

    @SuppressLint("ValidFragment")
    public LeftFragment(Main2Activity activity) {
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_left, container, false);

        initView();
        init();
        return mView;
    }

    /**
     * 各种初始化View
     */
    private void initView() {
        mExpandableListView = (ExpandableListView) mView.findViewById(R.id.fragment_left_expanlv);
        mImageView_sina = (ImageView) mView.findViewById(R.id.fragment_left_sina);
        mImageView_wangyi = (ImageView) mView.findViewById(R.id.fragment_left_wangyi);
        mImageView_tencent = (ImageView) mView.findViewById(R.id.fragment_left_tencent);
        mImageView_douyu = (ImageView) mView.findViewById(R.id.fragment_left_douyu);
        mImageView_MusicOrVideo = (ImageView) mView.findViewById(R.id.fragment_left_MusicOrVideo);
        mImageView_Menu = (ImageView) mView.findViewById(R.id.fragment_left_menu);
        mImageView_up = (ImageView) mView.findViewById(R.id.fragment_left_up);
        mImageView_playOrPause = (ImageView) mView.findViewById(R.id.fragment_left_play_pause);
        mImageView_down = (ImageView) mView.findViewById(R.id.fragment_left_down);

        mImageView_sina.setOnClickListener(this);
        mImageView_wangyi.setOnClickListener(this);
        mImageView_tencent.setOnClickListener(this);
        mImageView_douyu.setOnClickListener(this);
        mImageView_MusicOrVideo.setOnClickListener(this);
        mImageView_Menu.setOnClickListener(this);
        mImageView_up.setOnClickListener(this);
        mImageView_playOrPause.setOnClickListener(this);
        mImageView_down.setOnClickListener(this);
        mList = (List) this.getArguments().get("data");
    }

    /**
     * 初始化数据
     */
    private void init() {
        mStringsParent = new String[]{"喜爱", "偶尔", "never"};
        mInts = new int[]{R.mipmap.always, R.mipmap.ornever, R.mipmap.hzw};
        childListOne = new ArrayList<>();
        childListTwo = new ArrayList<>();
        childListThree = new ArrayList<>();
        mDatabean = new Databean();
        mStringList = mActivity.mDButile.queryResult("user");
        msizeCount = mStringList.size();
        getAllList();
        parentList = new ArrayList<>();
        for (int i = 0; i < mStringsParent.length; i++) {
            mDatabean = new Databean();
            mDatabean.setImgvId(mInts[i]);
            mDatabean.setName(mStringsParent[i]);
            switch (i) {
                case 0:
                    mDatabean.setList(childListOne);//为每一个group添加child
                    break;
                case 1:
                    mDatabean.setList(childListTwo);//为每一个group添加child
                    break;
                case 2:
                    mDatabean.setList(childListThree);//为每一个group添加child
                    break;
            }

            parentList.add(mDatabean);
        }
        mAdapter = new MyExpanAdapter(this, parentList);
        mExpandableListView.setAdapter(mAdapter);
        //当组展开时的动作
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < mExpandableListView.getCount(); i++) {
                    if (i != groupPosition) {
                        mExpandableListView.collapseGroup(i);
                    }
                }
            }
        });
    }

    /**
     * click点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.child_tv:
                String content = (String) view.getTag();
                url = content;
                mActivity.mFlowingDrawer.closeMenu();//关闭侧滑
                break;
            case R.id.child_imgvDelete:
                String urlD = (String) view.getTag();
                mActivity.mDButile.deleteUrl(urlD, "user");
                for (int i = childListThree.size() - 1; i >= 0; i--) {
                    ChildMsg cm = childListThree.get(i);
                    String str = cm.getNetContent();
                    if (urlD.equals(str)) {
                        childListThree.remove(cm);
                    }
                }
                mAdapter.notifyDataSetChanged();
                mActivity.mFlowingDrawer.closeMenu();
                break;
            case R.id.fragment_left_sina:
                url = "http://news.sina.com.cn/";
                mActivity.mFlowingDrawer.closeMenu();
                break;
            case R.id.fragment_left_wangyi:
                url = "http://news.163.com/";
                mActivity.mFlowingDrawer.closeMenu();
                break;
            case R.id.fragment_left_tencent:
                url = "http://news.qq.com/";
                mActivity.mFlowingDrawer.closeMenu();
                break;
            case R.id.fragment_left_douyu:
                url = "https://www.douyu.com/room/my_follow";
                mActivity.mFlowingDrawer.closeMenu();
                break;
            case R.id.fragment_left_MusicOrVideo://切换
                if (mIsMusic) {
                    mImageView_MusicOrVideo.setImageResource(R.mipmap.video);
                } else {
                    mImageView_MusicOrVideo.setImageResource(R.mipmap.music);
                }
                mIsMusic = !mIsMusic;
                break;
            case R.id.fragment_left_menu://菜单
                if (mIsMusic) {

                } else {

                }

                break;
            case R.id.fragment_left_up://上一首
                if (mIsMusic) {

                } else {

                }
                break;
            case R.id.fragment_left_play_pause://暂停/开始
                if (mIsPlay) {
                    mImageView_playOrPause.setImageResource(R.mipmap.pause);
                } else {
                    mImageView_playOrPause.setImageResource(R.mipmap.play);
                }
                mIsPlay = !mIsPlay;
                break;
            case R.id.fragment_left_down://下一首
                if (mIsMusic) {

                } else {

                }
                break;


        }
        mActivity.mWebView.loadUrl(url);
        mActivity.mAutoCompleteTextView.setText(url);

    }

    /**
     * 主界面添加数据时进行的界面数据更新
     *
     * @param url
     */
    public void setNotiAdapter(String url) {
        mChildMsg = new ChildMsg();
        mChildMsg.netContent = url;
        childListThree.add(0, mChildMsg);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 进行的集合获取--用来给二级列表进行展示
     */
    public void getAllList() {
        childListOne.clear();
        childListTwo.clear();
        childListThree.clear();
        for (int i = 0; i < mList.size(); i++) {
            mChildMsg = new ChildMsg();
            mChildMsg.netContent = (String) mList.get(i);
            childListOne.add(mChildMsg);
        }
        for (int i = 0; i < 3; i++) {
            mChildMsg = new ChildMsg();
            mChildMsg.netContent = (String) mList.get(i);
            childListTwo.add(mChildMsg);
        }
        for (int i = 0; i < mStringList.size(); i++) {
            mChildMsg = new ChildMsg();
            String str = mStringList.get(i);
            str = str.replaceAll("\r|\n", "");
            mChildMsg.netContent = str;
            childListThree.add(mChildMsg);
        }
    }


}
