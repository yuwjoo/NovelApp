package com.yuwjoo.myhome.ui.webview

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.yuwjoo.myhome.MainActivity
import com.yuwjoo.myhome.ui.webview.bridge.WebViewBridge

private var lastBackPressTime = -1L // 上次点击返回键的时间

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun MyWebView(mainActivity: MainActivity, modifier: Modifier = Modifier) {
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
                onBackPressed(mainActivity, this)
            }
        },
        update = {
            it.loadUrl(mUrl)
        },
        modifier = modifier
    )

    WebView.setWebContentsDebuggingEnabled(true)
}

/**
 * 监听返回事件
 */
private fun onBackPressed(mainActivity: MainActivity, webView: WebView) {
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (webView.canGoBack()) {
                webView.goBack()
                return
            }
            val currentTime = System.currentTimeMillis()
            if (lastBackPressTime == -1L || currentTime - lastBackPressTime >= 2000) {
                // 显示提示信息
                Toast.makeText(mainActivity, "再按一次退出", Toast.LENGTH_SHORT).show()
                // 记录时间
                lastBackPressTime = currentTime
            } else {
                //退出应用
                mainActivity.finish()

                // android.os.Process.killProcess(android.os.Process.myPid())
                // System.exit(0) // exitProcess(0)
                // moveTaskToBack(false)
            }
        }
    }
    mainActivity.onBackPressedDispatcher.addCallback(mainActivity, callback)
}