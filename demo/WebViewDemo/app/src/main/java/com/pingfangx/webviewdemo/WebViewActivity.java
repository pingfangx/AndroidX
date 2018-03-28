package com.pingfangx.webviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initViews();
    }

    private void initViews() {
        int id = getIntent().getIntExtra("id", 0);
        final int type = id == 0 ? 0 : id - R.id.btn_1 + 1;
        setTitle("按钮" + type);
        mWebView = findViewById(R.id.web_view);
        mWebView.loadUrl("http://v.qq.com/x/page/n0544h1oi7e.html");
        switch (type) {
            case 1:
                //不处理
                //调用系统打开
                break;
            case 2:
                //空的
                //正常
                mWebView.setWebViewClient(new WebViewClient());
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                // false
                mWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        switch (type) {
                            case 3:
                                //正常
                                return super.shouldOverrideUrlLoading(view, url);
                            case 4:
                                //不加载
                                return true;
                            case 5:
                                //正常
                                return false;
                            case 6:
                                //无法返回
                                view.loadUrl(url);
                                return true;
                            case 7:
                                //无法返回
                                view.loadUrl(url);
                                return false;
                            default:
                                return super.shouldOverrideUrlLoading(view, url);
                        }
                    }

                });
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }
}
