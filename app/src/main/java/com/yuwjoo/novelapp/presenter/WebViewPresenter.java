package com.yuwjoo.novelapp.presenter;

import android.annotation.SuppressLint;
import android.webkit.WebSettings;

import com.yuwjoo.novelapp.dsbridge.JSApi;

import wendu.dsbridge.DWebView;

public class WebViewPresenter implements IWebViewPresenter {
    private final DWebView webView;

    public WebViewPresenter(DWebView webView) {
        this.webView = webView;
        this.init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
//        DWebView.setWebContentsDebuggingEnabled(true); // 启用WebView调试模式
        webView.addJavascriptObject(new JSApi(), null);
        webView.loadUrl("file:///android_asset/web/index.html");
    }
}
