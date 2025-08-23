package com.yuwjoo.myhome.ui.webview

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.yuwjoo.myhome.ui.webview.bridge.WebViewBridge

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun MyWebView(modifier: Modifier = Modifier) {
    val mUrl = "http://192.168.1.138:9000/#/test-page"

    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                WebViewBridge(this)
                loadUrl(mUrl)
            }
        },
        update = {
            it.loadUrl(mUrl)
        },
        modifier = modifier
    )

    WebView.setWebContentsDebuggingEnabled(true)
}