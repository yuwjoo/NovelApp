package com.yuwjoo.novelapp.dsbridge;

import android.util.Base64;
import android.util.Log;
import android.webkit.JavascriptInterface;

import androidx.annotation.NonNull;

import com.yuwjoo.novelapp.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import wendu.dsbridge.CompletionHandler;

public class JSApi {
    private final String TAG = "JSApi";

    @JavascriptInterface
    public void onAjaxRequest(Object requestData, CompletionHandler<Object> handler) throws JSONException {
        Log.i(TAG, "onAjaxRequest:");
        JSONObject requestJSON = new JSONObject(requestData.toString());
        //定义响应结构
        final Map<String, Object> responseData = new HashMap<>();
        responseData.put("statusCode", 0);

        try {
            //判断是否需要将返回结果编码，responseType为stream时应编码
            String contentType = "";
            boolean encode = false;
            String responseType = requestJSON.optString("responseType", "");
            if (responseType.equals("stream")) {
                encode = true;
            }

            Request.Builder rb = new Request.Builder();
            rb.url(requestJSON.getString("url"));
            JSONObject headers = requestJSON.getJSONObject("headers");

            //设置请求头
            Iterator<String> iterator = headers.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = headers.getString(key);
                String lKey = key.toLowerCase();
                if (lKey.equals("cookie")) {
                    //使用CookieJar统一管理cookie
                    continue;
                }
                if (lKey.equals("content-type")) {
                    contentType = value;
                }
//                rb.header(key, value);
            }

            //创建请求体
            if (requestJSON.getString("method").equals("POST")) {
                RequestBody requestBody = RequestBody
                        .create(requestJSON.getString("data"), MediaType.parse(contentType));
//                rb.post(requestBody);
            }
            //创建并发送http请求
            Call call = OkHttpUtils.getInstance().newCall(rb.build());
            final boolean finalEncode = encode;
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    responseData.put("responseText", e.getMessage());
                    handler.complete(new JSONObject(responseData).toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String data = null;
                    //如果需要编码，则对结果进行base64编码后返回
                    if (finalEncode) {
                        if (response.body() != null) {
                            data = Base64.encodeToString(response.body().bytes(), Base64.DEFAULT);
                        }
                    } else {
                        if (response.body() != null) {
                            data = response.body().string();
                        }
                    }
                    responseData.put("responseText", data);
                    responseData.put("statusCode", response.code());
                    responseData.put("statusMessage", response.message());
                    Map<String, List<String>> responseHeaders = response.headers().toMultimap();
                    responseData.put("headers", responseHeaders);
                    handler.complete(new JSONObject(responseData).toString());
                }
            });
        } catch (Exception e) {
            responseData.put("responseText", e.getMessage());
            handler.complete(new JSONObject(responseData).toString());
        }
    }

    //同步API
    @JavascriptInterface
    public String testSyn(Object msg) {
        Log.i(TAG, "testSyn:" + msg);
        return msg + "［syn call］";
    }

    //异步API
    @JavascriptInterface
    public void testAsync(Object msg, CompletionHandler<String> handler) {
        handler.complete(msg + " [ async call]");
    }
}
