package com.example.https.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.https.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mbt_con;
    private Button mbt_fin;
    private TextView mtv_cont;
    private ImageView mImg_cont;
    private WebView mWebView;
    private AVLoadingIndicatorView mLoadingIndicatorView;
//    private ProgressBar mProgressBar;
    private String url;
    private URL mURL;
    private HttpURLConnection mHttpURLConnection;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mLoadingIndicatorView.setVisibility(View.GONE);
            Bundle data = msg.getData();
            String msg1 = data.getString("msg");
            mtv_cont.setText(msg1);
            mImg_cont.setImageBitmap(mMbitmap);
            Log.e("网络数据",msg1);
            mWebView.loadDataWithBaseURL("about:blank", msg1, "text/html", "utf-8", null);
        }
    };
    private WebSettings mSettings;
    private Bitmap mMbitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbt_con = (Button) findViewById(R.id.main_btConnect);
        mbt_con.setOnClickListener(this);
        mbt_fin = (Button) findViewById(R.id.main_btFinish);
        mbt_fin.setOnClickListener(this);
        mtv_cont = (TextView) findViewById(R.id.main_tvContent);
        mImg_cont = (ImageView) findViewById(R.id.main_imgvContent);
        mWebView = (WebView) findViewById(R.id.main_wvHtml);
        mLoadingIndicatorView= (AVLoadingIndicatorView) findViewById(R.id.avloadingIndicatorView_BallPulse);
//        mProgressBar= (ProgressBar) findViewById(R.id.main_progressBar);
        mSettings = mWebView.getSettings();

//        mWebView.getSettings().setJavaScriptEnabled(true);//设置支持js，不然网页看不了==设置这个出问题
        mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存模式，使用默认
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);/* 设置为true表示支持使用js打开新的窗口 */
        mSettings.setDomStorageEnabled(true);/* 大部分网页需要自己保存一些数据,这个时候就的设置下面这个属性 */
        mSettings.setUseWideViewPort(false); /* 设置为使用webview推荐的窗口 */
        mSettings.setLoadWithOverviewMode(false);/* 设置网页自适应屏幕大小 ---这个属性应该是跟上面一个属性一起用 */
//        mSettings.setGeolocationEnabled(true);/* HTML5的地理位置服务,设置为true,启用地理定位 */
        mSettings.setBuiltInZoomControls(false);/* 设置是否允许webview使用缩放的功能,我这里设为false,不允许 */
        mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);/* 提高网页渲染的优先级 */
        mWebView.setHorizontalScrollBarEnabled(false);/* 设置显示水平滚动条,就是网页右边的滚动条.我这里设置的不显示 */
        mWebView.setVerticalScrollbarOverlay(true);/* 指定垂直滚动条是否有叠加样式 */
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);/* 设置滚动条的样式 */
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                mProgressBar.setProgress(newProgress);
                if(newProgress==100){
//                    mProgressBar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });/* 这个不用说了,重写WebChromeClient监听网页加载的进度,从而实现进度条 */
        mWebView.setWebViewClient(new WebViewClient());/* 同上,重写WebViewClient可以监听网页的跳转和资源加载等等... */
        mWebView.loadUrl("file:///android_asset/about.html");
//        mWebView.loadUrl("http://www.ximalaya.com/16550049/album/291718");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btConnect:
                url = "http://www.toutiao.com/";
                mbt_con.setEnabled(false);
                mLoadingIndicatorView.setVisibility(View.VISIBLE);
                mLoadingIndicatorView.setFocusable(true);
                mLoadingIndicatorView.setFocusableInTouchMode(true);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            mURL = new URL(url);
                            mHttpURLConnection = (HttpURLConnection) mURL.openConnection();
                            mHttpURLConnection.setRequestMethod("GET");
                            mHttpURLConnection.setConnectTimeout(9 * 1000);
                            mHttpURLConnection.setReadTimeout(9 * 1000);
                            int responseCode = mHttpURLConnection.getResponseCode();
                            if (responseCode == 200) {

                                InputStream inputStream = mHttpURLConnection.getInputStream();
                                mMbitmap = BitmapFactory.decodeStream(inputStream);
                                BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(inputStream));
                                String str = null;
                                StringBuilder stringBuilder = new StringBuilder();
                                while ((str = bufferedInputStream.readLine()) != null) {
                                    stringBuilder.append(str);
                                }
                                Message message = mHandler.obtainMessage();
                                Bundle bundle = new Bundle();
                                bundle.putString("msg", String.valueOf(stringBuilder));
                                message.setData(bundle);
                                mHandler.sendMessageDelayed(message,5000);
                                inputStream.close();
                            }
                            mHttpURLConnection.disconnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

                break;
            case R.id.main_btFinish:
                mtv_cont.setText("");
                mbt_con.setEnabled(true);
                mWebView.loadUrl("file:///android_asset/about.html");
                break;
        }
    }

    long timeStart = 0;

    @Override
    public void onBackPressed() {
        long timeEnd = System.currentTimeMillis();
        if (timeEnd - timeStart >= 2000) {
            timeStart = timeEnd;
            Toast.makeText(this, "双击退出", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
