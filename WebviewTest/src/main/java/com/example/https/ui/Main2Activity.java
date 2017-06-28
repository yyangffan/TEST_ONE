package com.example.https.ui;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.https.R;
import com.example.https.adapter.FilterAdapter;
import com.example.https.dbhelper.DButile;
import com.example.https.view.ScrollWebView;
import com.example.https.view.SlideInAnimationHandler;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.shamanland.fab.FloatingActionButton;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main2Activity extends Activity implements View.OnClickListener {
    private AVLoadingIndicatorView mLoadingIndicatorView;
    public AutoCompleteTextView mAutoCompleteTextView;
    private FloatingActionButton mActionButtonR;
    private FloatingActionButton mActionButtonL;
    private FloatingActionButton mActionButtonC;
    private FilterAdapter mFilterAdapter;
    public FlowingDrawer mFlowingDrawer;
    private LinearLayout mLayoutTTop;
    private LinearLayout mLayoutTop;
    private FrameLayout mFrameLayout;
    public ScrollWebView mWebView;
    private ImageView mImageViewT;
    private ImageView mImageView;
    private Random mRandom;
    private List mList;
    private String url;

    private LeftFragment mLeftFragment;
    private FragmentManager mFragmentManager;
    public DButile mDButile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        mLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.main2_loading);
        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.main2_textview);
        mFrameLayout = (FrameLayout) findViewById(R.id.id_container_menu);//用来存放Fragment
        mActionButtonR = (FloatingActionButton) findViewById(R.id.fabR);
        mActionButtonL = (FloatingActionButton) findViewById(R.id.fabL);
        mActionButtonC = (FloatingActionButton) findViewById(R.id.fabC);
        mFlowingDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        mWebView = (ScrollWebView) findViewById(R.id.main2_webview);
        mLayoutTTop = (LinearLayout) findViewById(R.id.main2_ll);
        mLayoutTop = (LinearLayout) findViewById(R.id.main2_llTop);
        mImageViewT = (ImageView) findViewById(R.id.main2_imgv2);
        mImageView = (ImageView) findViewById(R.id.main2_imgv);

        mDButile = new DButile(this);

        initWebView();
        init();

    }

    private void initMenu() {
        final ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(this.getResources().getDrawable(R.mipmap.add));
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        ImageView itemIcon1 = new ImageView(this);
        ImageView itemIcon2 = new ImageView(this);
        ImageView itemIcon3 = new ImageView(this);
        ImageView itemIcon4 = new ImageView(this);
        ImageView itemIcon5 = new ImageView(this);
        itemIcon1.setImageDrawable(this.getResources().getDrawable(R.mipmap.back));
        itemIcon2.setImageDrawable(this.getResources().getDrawable(R.mipmap.forward));
        itemIcon3.setImageDrawable(this.getResources().getDrawable(R.mipmap.reload));
        itemIcon4.setImageDrawable(this.getResources().getDrawable(R.mipmap.favoruite));
        itemIcon5.setImageDrawable(this.getResources().getDrawable(R.mipmap.home));
        SubActionButton button1 = itemBuilder.setContentView(itemIcon1).build();
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();
        SubActionButton button4 = itemBuilder.setContentView(itemIcon4).build();
        SubActionButton button5 = itemBuilder.setContentView(itemIcon5).build();
        int floatWidth = getResources().getDimensionPixelSize(R.dimen.floatMenu_width);
        int floatHeight = getResources().getDimensionPixelSize(R.dimen.floatMenu_height);
        int floatChildWidth = getResources().getDimensionPixelSize(R.dimen.floatMenuChild_width);
        int floatChildHeight = getResources().getDimensionPixelSize(R.dimen.floatMenuChild_height);
        int floatMargin = getResources().getDimensionPixelSize(R.dimen.floatMenu_margin);

        FrameLayout.LayoutParams blueContentParams = new FrameLayout.LayoutParams(floatChildWidth, floatChildHeight);
        blueContentParams.setMargins(floatMargin, floatMargin, floatMargin, floatMargin);
        button1.setLayoutParams(blueContentParams);
        button2.setLayoutParams(blueContentParams);
        button3.setLayoutParams(blueContentParams);
        button4.setLayoutParams(blueContentParams);
        button5.setLayoutParams(blueContentParams);

        com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.LayoutParams layoutParams = new
                com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.LayoutParams(floatWidth, floatHeight);

        com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton actionButton = new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(this)
                .setPosition(com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.POSITION_BOTTOM_RIGHT)
                .setLayoutParams(layoutParams)
                .setContentView(icon)
                .build();
        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
                .setAnimationHandler(new SlideInAnimationHandler())
                .addSubActionView(button2)
                .addSubActionView(button3)
                .addSubActionView(button5)
                .addSubActionView(button4)
                .addSubActionView(button1)
                .attachTo(actionButton)
                .setStartAngle(-90)
                .setEndAngle(-180)
                .build();
        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                icon.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(icon, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                icon.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(icon, pvhR);
                animation.start();
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//后退
                rightLowerMenu.close(true);
                mWebView.goBack();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//前进
                rightLowerMenu.close(true);
                mWebView.goForward();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//刷新
                rightLowerMenu.close(true);
                mWebView.reload();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {//收藏
            @Override
            public void onClick(View view) {
                rightLowerMenu.close(true);
                url = mAutoCompleteTextView.getText().toString();
                if (url.equals("") || url == null) {
                    url = mWebView.getUrl();
                }
                boolean b = mDButile.addUrl(url,"user");
                if (!b) {//如果连接不存在就添加进去
                    mLeftFragment.setNotiAdapter(url);
                }
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//主页
                rightLowerMenu.close(true);
                mWebView.clearHistory();
                url = (String) mList.get(mRandom.nextInt(mList.size()));
                mAutoCompleteTextView.setText(url);
                mWebView.loadUrl(url);
            }
        });


    }

    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSavePassword(true);
        webSettings.setSupportZoom(true);
        mWebView.requestFocusFromTouch();//如果需要输入帐号密码需要设置
        url = "https://tieba.baidu.com/";
        //当点击网页中的链接时在Webview中打开，而不是打开浏览器
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成
                    mLoadingIndicatorView.setVisibility(View.GONE);
                } else {
                    // 加载中
                    mLoadingIndicatorView.setVisibility(View.VISIBLE);
                }

            }
        });
        mWebView.loadUrl(url);
    }

    private void init() {
        mImageView.setOnClickListener(this);
        mImageViewT.setOnClickListener(this);
        mLeftFragment = new LeftFragment(this);
        mFragmentManager = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.id_container_menu, mLeftFragment);
        fragmentTransaction.commit();
        mList = new ArrayList();
        mActionButtonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.goForward();
            }
        });
        mActionButtonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.goBack();
            }
        });
        mActionButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebView.reload();
            }
        });

        mList.add("https://tieba.baidu.com/");
        mList.add("https://www.baidu.com/");
        mList.add("http://www.toutiao.com/");
        mList.add("http://www.toutiao.com/ch/news_hot/");
        mList.add("https://www.taobao.com/");
        mList.add("https://github.com/Trinea/android-open-project");
        mList.add("http://blog.csdn.net/lmj623565791?viewmode=contents");
        mList.add("http://blog.csdn.net/column/details/android110.html");
        mList.add("http://www.apkbus.com/forum-96-1.html");
        mList.add("http://www.apkbus.com/home.php?mod=space&uid=719059&do=blog&id=63185");
        mList.add("http://www.apkbus.com/");
        mList.add("http://www.apkbus.com/forum.php?mod=viewthread&tid=256632");
        mList.add("http://www.apkbus.com/forum.php?mod=viewthread&tid=267776");
        mList.add("http://www.jizhuomi.com/android/book/636.html");
        mList.add("http://blog.csdn.net/ranking.html");
        mList.add("http://www.ximalaya.com/16550049/album/291718");
        mList.add("http://news.sina.com.cn/");
        mList.add("http://news.qq.com/");
        mList.add("https://www.douyu.com/room/my_follow");
        mList.add("http://news.163.com/");
        mDButile.addMoreUrl(mList,"user");//添加目前集合数据到数据库中
        mFilterAdapter = new FilterAdapter(this, mList);
        mAutoCompleteTextView.setAdapter(mFilterAdapter);
        mAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                url = adapterView.getItemAtPosition(i).toString();
                mWebView.loadUrl(url);
            }
        });
        mRandom = new Random(mList.size());
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) mList);
        mLeftFragment.setArguments(bundle);

        mWebView.setOnScrollChangeListener(new ScrollWebView.OnScrollChangeListener() {
            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {
//                mLayoutTTop.setVisibility(View.VISIBLE);
//                mActionButtonR.setVisibility(View.VISIBLE);
//                mActionButtonL.setVisibility(View.VISIBLE);
                //暂时全部隐藏
                mActionButtonR.setVisibility(View.GONE);
                mActionButtonL.setVisibility(View.GONE);

            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {
//                mLayoutTTop.setVisibility(View.VISIBLE);
//                mActionButtonR.setVisibility(View.VISIBLE);
//                mActionButtonL.setVisibility(View.VISIBLE);
                //暂时全部隐藏
                mActionButtonR.setVisibility(View.GONE);
                mActionButtonL.setVisibility(View.GONE);
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
//                mLayoutTTop.setVisibility(View.GONE);
                mActionButtonR.setVisibility(View.GONE);
                mActionButtonL.setVisibility(View.GONE);
            }
        });
        initMenu();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main2_imgv:
                mAutoCompleteTextView.setText("");
                mImageView.setVisibility(View.GONE);
                break;
            case R.id.main2_imgv2:
                url = String.valueOf(mAutoCompleteTextView.getText());
                if (url.equals("") || url == null) {
                    url = "https://www.baidu.com";
                }
                mWebView.loadUrl(url);
                break;


        }
    }

    long startTime = 0;

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        mFlowingDrawer.closeMenu();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();//返回上一页面
                return true;
            } else {
                long endTime = System.currentTimeMillis();
                if (endTime - startTime >= 2000) {
                    Toast.makeText(Main2Activity.this, "双击退出", Toast.LENGTH_SHORT).show();
                    startTime = endTime;
                    return true;
                }
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
