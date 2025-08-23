package com.yuwjoo.myhome.ui.webview.bridge

import android.webkit.WebView
import com.yuwjoo.myhome.ui.webview.bridge.api.request
import com.yuwjoo.myhome.ui.webview.bridge.api.test

class WebViewBridge(val webView: WebView, val config: BridgeConfig = BridgeConfig()) {
    val channelMap = HashMap<String, BridgeChannel>()
    val webInterface = WebInterface(this)
    val router = BridgeRouter()
    val globalChannel = BridgeChannel(this, config.globalEventChannelId)

    init {
        webView.addJavascriptInterface(WebViewInterface(this), config.webViewInterfaceKey)
        channelMap[config.globalEventChannelId] = globalChannel
        router.register("net/request", ::request)
        router.register("net/test", ::test)
    }
}
