package com.yuwjoo.novelapp.utils.dsbridge.jsapi;

import android.util.Base64;
import android.webkit.JavascriptInterface;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yuwjoo.novelapp.module.Manifest;
import com.yuwjoo.novelapp.okhttp.OkHttpUtils;
import com.yuwjoo.novelapp.utils.dsbridge.module.RequestOptions;

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

public class CommonJSApi {

    @JavascriptInterface
    public void request(@NonNull Object object, CompletionHandler<Object> handler) throws JSONException {
        final RequestOptions requestOptions = new Gson().fromJson((JsonObject)object, RequestOptions.class); // 请求json数据
//        final Map<String, Object> responseData = new HashMap<>(); // 响应数据
//
//        try {
//            String url = requestJSON.getString("url"); // 请求url
//            String method = requestJSON.getString("method"); // 请求method
//            JSONObject headers = requestJSON.getJSONObject("headers"); // 请求头数据
//            String bodyData = requestJSON.optString("body", ""); // 请求body数据
//            String responseType = requestJSON.optString("responseType", ""); // 需要响应的数据类型
//            String contentType = ""; // 数据类型
//
//            Request.Builder rb = new Request.Builder();
//
//            rb.url(url); // 设置url
//
//            Iterator<String> iterator = headers.keys();
//            while (iterator.hasNext()) {
//                String key = iterator.next();
//                String value = headers.getString(key);
//                String lKey = key.toLowerCase();
//                if (lKey.equals("cookie")) {
//                    //使用CookieJar统一管理cookie
//                    continue;
//                }
//                if (lKey.equals("content-type")) {
//                    contentType = value;
//                }
//                rb.header(key, value); //设置请求头
//            }
//
//            if (!bodyData.equals("null")) {
//                RequestBody requestBody = RequestBody
//                        .create(bodyData, MediaType.parse(contentType));
//                if (method.equalsIgnoreCase("post")) {
//                    rb.post(requestBody); // 设置请求body
//                } else {
//                    rb.put(requestBody); // 设置请求body
//                }
//            }
//
//            Callback callback = new Callback() {
//                @Override
//                public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                    responseData.put("statusCode", 0);
//                    responseData.put("responseText", e.getMessage());
//                    handler.complete(new JSONObject(responseData).toString());
//                }
//
//                @Override
//                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                    Map<String, List<String>> responseHeaders = response.headers().toMultimap(); // 响应头
//                    String data = null;
//
//                    if (responseType.equals("stream")) {
//                        if (response.body() != null) {
//                            data = Base64.encodeToString(response.body().bytes(), Base64.DEFAULT); // 对响应结果进行base64编码
//                        }
//                    } else {
//                        if (response.body() != null) {
//                            data = response.body().string(); // 获取响应结果字符串
//                        }
//                    }
//                    responseData.put("statusCode", response.code());
//                    responseData.put("statusMessage", response.message());
//                    responseData.put("responseText", data);
//                    responseData.put("headers", responseHeaders);
//                    handler.complete(new JSONObject(responseData).toString());
//                }
//            };
//
//            OkHttpUtils.getOkHttpClient().newCall(rb.build()).enqueue(callback); // 发送http请求
//        } catch (Exception e) {
//            responseData.put("statusCode", 0);
//            responseData.put("responseText", e.getMessage());
//            handler.complete(responseData.toString());
//        }
    }

}
