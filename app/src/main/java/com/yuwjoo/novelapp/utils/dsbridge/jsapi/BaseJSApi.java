package com.yuwjoo.novelapp.utils.dsbridge.jsapi;

import android.util.Log;
import android.webkit.JavascriptInterface;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yuwjoo.novelapp.okhttp.OkHttpUtils;
import com.yuwjoo.novelapp.utils.dsbridge.module.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import kotlin.Pair;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import wendu.dsbridge.CompletionHandler;

public class BaseJSApi {

    @JavascriptInterface
    public void request(@NonNull Object object, CompletionHandler<Object> handler) {
        RequestOptions requestOptions = new Gson().fromJson(object.toString(), RequestOptions.class); // 请求配置
        String contentType = null; // body数据类型
        Request.Builder rb = new Request.Builder();

        HttpUrl.Builder httpUrlBuilder = Objects.requireNonNull(HttpUrl.parse(requestOptions.getUrl())).newBuilder();
        if (requestOptions.getParams() != null) {
            Set<Map.Entry<String, JsonElement>> paramsEntrySet = requestOptions.getParams().entrySet(); // 查询参数set
            for (Map.Entry<String, JsonElement> entry : paramsEntrySet) {
                String key = entry.getKey();
                String value = entry.getValue().getAsString();
                httpUrlBuilder.addQueryParameter(key, value); //设置查询参数
            }
        }
        rb.url(httpUrlBuilder.build()); // 设置url

        if (requestOptions.getHeaders() != null) {
            Set<Map.Entry<String, JsonElement>> headerEntrySet = requestOptions.getHeaders().entrySet(); // 请求头set
            for (Map.Entry<String, JsonElement> entry : headerEntrySet) {
                String key = entry.getKey();
                String value = entry.getValue().getAsString();
                if (key.equalsIgnoreCase("content-type")) {
                    contentType = value;
                }
                rb.header(key, value); //设置请求头
            }
        }

        switch (requestOptions.getMethod().toLowerCase()) {
            case "get":
                rb.get();
                break;
            case "post": {
                String bodyData = requestOptions.getData() != null ? requestOptions.getData().toString() : "";
                MediaType mediaType = contentType != null ? MediaType.get(contentType) : null;
                rb.post(RequestBody.create(bodyData, mediaType));
                break;
            }
            case "put": {
                String bodyData = requestOptions.getData() != null ? requestOptions.getData().toString() : "";
                MediaType mediaType = contentType != null ? MediaType.get(contentType) : null;
                rb.put(RequestBody.create(bodyData, mediaType));
                break;
            }
            case "delete": {
                String bodyData = requestOptions.getData() != null ? requestOptions.getData().toString() : "";
                MediaType mediaType = contentType != null ? MediaType.get(contentType) : null;
                rb.delete(RequestBody.create(bodyData, mediaType));
                break;
            }
        }

        Call call = OkHttpUtils.getOkHttpClient().newCall(rb.build());

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                JSONObject result = new JSONObject();
                try {
                    result.put("type", "response");
                    JSONObject data = new JSONObject();
                    data.put("status", -1);
                    data.put("statusText", e.getMessage());
                    result.put("data", data);
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }
                handler.complete(result.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Iterator<Pair<String, String>> iteratorHeaders = response.headers().iterator(); // 响应头
                String bodyData = null;
                JSONObject result = new JSONObject();

                if (response.body() != null) {
                    bodyData = response.body().string(); // 获取响应结果字符串
                }

                try {
                    result.put("type", "response");
                    JSONObject headers = new JSONObject();
                    while (iteratorHeaders.hasNext()) {
                        Pair<String, String> pair =  iteratorHeaders.next();
                        headers.put(pair.getFirst(), pair.getSecond());
                    }
                    JSONObject data = new JSONObject();
                    data.put("data", bodyData);
                    data.put("status", response.code());
                    data.put("statusText", response.message());
                    data.put("headers", headers);
                    result.put("data", data);
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }

                handler.complete(result.toString());
            }
        }); // 发送http请求
    }
}
