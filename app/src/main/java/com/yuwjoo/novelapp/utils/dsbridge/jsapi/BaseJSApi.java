package com.yuwjoo.novelapp.utils.dsbridge.jsapi;

import android.webkit.JavascriptInterface;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yuwjoo.novelapp.okhttp.OkHttpUtils;
import com.yuwjoo.novelapp.utils.dsbridge.model.RequestOptionsModel;
import com.yuwjoo.novelapp.utils.okhttp.OkHttpHelper;
import com.yuwjoo.novelapp.utils.okhttp.ProgressListener;
import com.yuwjoo.novelapp.utils.okhttp.RequestBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
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
    public void request(@NonNull Object object, CompletionHandler<Object> handler) throws JSONException {
        RequestOptionsModel requestOptionsModel = new Gson().fromJson(object.toString(), RequestOptionsModel.class); // 请求配置
        String contentType = null; // body数据类型
        RequestBuilder requestBuilder = OkHttpHelper.newRequest();

        requestBuilder.setUrl(requestOptionsModel.getUrl()); // 设置url

        requestBuilder.setMethod(requestOptionsModel.getMethod()); // 设置方法

        if (requestOptionsModel.getParams() != null) {
            Set<Map.Entry<String, JsonElement>> paramsEntrySet = requestOptionsModel.getParams().entrySet(); // 查询参数set
            for (Map.Entry<String, JsonElement> entry : paramsEntrySet) {
                String key = entry.getKey();
                String value = entry.getValue().getAsString();
                requestBuilder.addParams(key, value); //设置查询参数
            }
        }

        if (requestOptionsModel.getHeaders() != null) {
            Set<Map.Entry<String, JsonElement>> headerEntrySet = requestOptionsModel.getHeaders().entrySet(); // 请求头set
            for (Map.Entry<String, JsonElement> entry : headerEntrySet) {
                String key = entry.getKey();
                String value = entry.getValue().getAsString();
                requestBuilder.addHeader(key, value); //设置请求头
                if (key.equalsIgnoreCase("content-type")) {
                    contentType = value;
                }
            }
        }

        Object dataObject = requestOptionsModel.getData();
        RequestBody requestBody = RequestBody.create(dataObject.toString(), MediaType.get("application/json"));
        requestBuilder.setBody(requestBody); // 设置body

//        Object dataObject = requestOptionsModel.getData();
//        if (dataObject != null) {
//            switch (requestOptionsModel.getDataType()) {
//                case "json": {
//                    RequestBody requestBody = RequestBody.create(dataObject.toString(), MediaType.get("application/json"));
//                    requestBuilder.setBody(requestBody); // 设置body
//                    break;
//                }
//                case "form": {
//                    FormBody.Builder formBodyBuilder = new FormBody.Builder();
//                    JSONObject dataJson = new JSONObject(dataObject.toString());
//                    for (Iterator<String> it = dataJson.keys(); it.hasNext(); ) {
//                        String key = it.next();
//                        String value = dataJson.getO
//                    }
//                    requestBuilder.setBody(requestBody); // 设置body
//                    break;
//                }
//            }
//        }

        requestBuilder.setTimeout(requestOptionsModel.getTimeout() / 1000); // 设置超时时间

        if (requestOptionsModel.isEnableUploadProgressListener()) {
            requestBuilder.setProgressUploadListener((bytesRead, contentLength, done) -> {
                JSONObject result = new JSONObject();
                try {
                    result.put("type", "uploadProgress");
                    JSONObject data = new JSONObject();
                    data.put("loaded", bytesRead);
                    data.put("total", contentLength);
                    data.put("lengthComputable", done);
                    data.put("upload", true);
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }
                handler.setProgressData(result.toString());
            }); // 设置上传进度监听器
        }

        if (requestOptionsModel.isEnableDownloadProgressListener()) {
            requestBuilder.setProgressDownloadListener((bytesRead, contentLength, done) -> {
                JSONObject result = new JSONObject();
                try {
                    result.put("type", "downloadProgress");
                    JSONObject data = new JSONObject();
                    data.put("loaded", bytesRead);
                    data.put("total", contentLength);
                    data.put("lengthComputable", done);
                    data.put("download", true);
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }
                handler.setProgressData(result.toString());
            }); // 设置下载进度监听器
        }

        Call call = requestBuilder.call();

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
                        Pair<String, String> pair = iteratorHeaders.next();
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
