package com.yuwjoo.novelapp.utils.dsbridge.jsapi;

import android.webkit.JavascriptInterface;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yuwjoo.novelapp.utils.dsbridge.model.RequestOptionsModel;
import com.yuwjoo.novelapp.utils.dsbridge.utils.RequestManager;
import com.yuwjoo.novelapp.utils.okhttp.OkHttpHelper;
import com.yuwjoo.novelapp.utils.okhttp.RequestBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import kotlin.Pair;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import wendu.dsbridge.CompletionHandler;

public class BaseJSApi {

    @JavascriptInterface
    public void request(@NonNull Object object, CompletionHandler<Object> handler) throws JSONException {
        RequestOptionsModel requestOptionsModel = new Gson().fromJson(object.toString(), RequestOptionsModel.class); // 请求配置
        String contentType = ""; // body数据类型
        RequestBuilder requestBuilder = OkHttpHelper.newRequest();

        requestBuilder.setUrl(requestOptionsModel.getUrl()); // 设置url

        requestBuilder.setMethod(requestOptionsModel.getMethod()); // 设置方法

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

        if (requestOptionsModel.getBodyText() != null) {
            RequestBody requestBody = RequestBody.create(requestOptionsModel.getBodyText(), MediaType.parse(contentType));
            requestBuilder.setBody(requestBody); // 设置body
        } else if (requestOptionsModel.getBodyBlobText() != null) {
            byte[] blobTextBytes = requestOptionsModel.getBodyBlobText().getBytes(StandardCharsets.UTF_8);
            RequestBody requestBody = RequestBody.create(blobTextBytes, MediaType.parse(contentType));
            requestBuilder.setBody(requestBody); // 设置body
        } else if (requestOptionsModel.getBodyMultipartList() != null) {
            MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
            multipartBodyBuilder.setType(Objects.requireNonNull(MediaType.parse(contentType)));
            for (JsonElement element : requestOptionsModel.getBodyMultipartList()) {
                JsonObject part = element.getAsJsonObject();
                if (part.get("type").getAsString().equals("field")) {
                    String name = part.get("name").getAsString();
                    String value = part.get("value").getAsString();
                    multipartBodyBuilder.addFormDataPart(name, value);
                } else {
                    String name = part.get("name").getAsString();
                    String filePath = part.get("filePath").getAsString();
                    String fileName = part.get("fileName").getAsString();
                    String mimeType = part.get("mimeType").getAsString();
                    String blobText = part.get("blobText").getAsString();
                    RequestBody fileRequestBody;
                    if (filePath.isEmpty()) {
                        fileRequestBody = RequestBody.create(blobText.getBytes(StandardCharsets.UTF_8), MediaType.parse(mimeType));
                    } else {
                        fileRequestBody = RequestBody.create(new File(filePath), MediaType.parse(mimeType));
                    }
                    multipartBodyBuilder.addFormDataPart(name, fileName, fileRequestBody);
                }
            }
            requestBuilder.setBody(multipartBodyBuilder.build()); // 设置body
        }

        requestBuilder.setTimeout(requestOptionsModel.getTimeout() / 1000); // 设置超时时间

        requestBuilder.setProgressUploadListener((bytesRead, contentLength, done) -> {
            JSONObject result = new JSONObject();
            try {
                result.put("type", "uploadProgress");
                JSONObject data = new JSONObject();
                data.put("loaded", bytesRead);
                data.put("total", contentLength);
                data.put("lengthComputable", true);
                result.put("data", data);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
            handler.setProgressData(result.toString());
        }); // 设置上传进度监听器

        requestBuilder.setProgressDownloadListener((bytesRead, contentLength, done) -> {
            JSONObject result = new JSONObject();
            try {
                result.put("type", "downloadProgress");
                JSONObject data = new JSONObject();
                data.put("loaded", bytesRead);
                data.put("total", contentLength);
                data.put("lengthComputable", true);
                result.put("data", data);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
            handler.setProgressData(result.toString());
        }); // 设置下载进度监听器

        Call call = requestBuilder.call();

        String uuid = RequestManager.addCall(call);
        JSONObject result = new JSONObject();
        result.put("type", "requestId");
        result.put("data", uuid);
        handler.setProgressData(result.toString());

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                JSONObject result = new JSONObject();
                try {
                    result.put("type", "error");
                    result.put("data", "other");
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }
                handler.complete(result.toString());
                RequestManager.deleteCall(uuid);
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
                    data.put("status", response.code());
                    data.put("statusText", response.message());
                    data.put("headers", headers);
                    data.put("body", bodyData);
                    data.put("url", response.request().url());
                    result.put("data", data);
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }

                handler.complete(result.toString());
                RequestManager.deleteCall(uuid);
            }
        }); // 发送http请求
    }

    @JavascriptInterface
    public void cancelRequest(@NonNull Object object, CompletionHandler<Object> handler) {
        RequestManager.cancelCall(object.toString());
    }

    @JavascriptInterface
    public void dsInit(@NonNull Object object, CompletionHandler<Object> handler) {
    }
}
