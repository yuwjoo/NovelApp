package com.yuwjoo.myhome.ui.webview.bridge.api

import android.util.Log
import com.yuwjoo.myhome.ui.webview.bridge.BridgeChannel
import org.json.JSONObject

fun request(payload: Any?, channel: BridgeChannel) {
    Log.i("request", payload.toString())
}

fun test(payload: Any?, channel: BridgeChannel) {
    Log.i("test send", payload.toString())
    channel.send("message", payload.toString())
    channel.done("done", payload = true, isError = true)
}
