package com.yuwjoo.novelapp.utils.okhttp;

import com.yuwjoo.novelapp.utils.okhttp.model.RequestTagModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestBuilder {
    private String url; // 请求地址
    private String method; // 请求方法
    private Map<String, String> headers; // 请求头
    private Map<String, String> params; // 查询参数
    private RequestBody body; // 请求body
    private int connectTimeout = -1; // 连接超时时间（秒）
    private int readTimeout = -1; // 读取超时时间（秒）
    private int writeTimeout = -1; // 写入超时时间（秒）
    private ProgressListener progressUploadListener; // 上传进度监听器
    private ProgressListener progressDownloadListener; // 下载进度监听器

    public RequestBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public RequestBuilder setMethod(String method) {
        this.method = method;
        return this;
    }

    public RequestBuilder setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public RequestBuilder setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public RequestBuilder setBody(RequestBody body) {
        this.body = body;
        return this;
    }

    public RequestBuilder setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public RequestBuilder setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public RequestBuilder setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public RequestBuilder setTimeout(int timeout) {
        this.connectTimeout = timeout;
        this.readTimeout = timeout;
        this.writeTimeout = timeout;
        return this;
    }

    public void setProgressUploadListener(ProgressListener progressUploadListener) {
        this.progressUploadListener = progressUploadListener;
    }

    public void setProgressDownloadListener(ProgressListener progressDownloadListener) {
        this.progressDownloadListener = progressDownloadListener;
    }

    public RequestBuilder addHeader(String key, String value) {
        if (this.headers == null) {
            this.headers = new HashMap<>();
        }
        this.headers.put(key, value);
        return this;
    }

    public RequestBuilder addParams(String key, String value) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }
        this.params.put(key, value);
        return this;
    }

    public Request build() {
        Request.Builder requestBuilder = new Request.Builder();

        if (this.params != null && !this.params.isEmpty()) {
            HttpUrl.Builder httpUrlBuilder = Objects.requireNonNull(HttpUrl.parse(this.url)).newBuilder();
            for (Map.Entry<String, String> entry : this.params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpUrlBuilder.addQueryParameter(key, value); //设置查询参数
            }
            requestBuilder.url(httpUrlBuilder.build()); // 设置url
        } else {
            requestBuilder.url(this.url); // 设置url
        }

        if (this.headers != null && !this.headers.isEmpty()) {
            for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                requestBuilder.header(key, value); //设置请求头
            }
        }

        String method = this.method == null || this.method.isEmpty() ? "get" : this.method;
        RequestBody data = this.body != null ? this.body : RequestBody.create("", null);
        if (this.progressUploadListener != null) {
            data = new ProgressRequestBody(data, this.progressUploadListener); // 添加上传进度监听器
        }
        requestBuilder.method(method, data); // 设置方法和请求体

        RequestTagModel requestTagModel = new RequestTagModel(this.connectTimeout, this.readTimeout, this.writeTimeout, this.progressDownloadListener);
        requestBuilder.tag(requestTagModel); // 附加信息

        return requestBuilder.build();
    }

    public Call call() {
        return OkHttpHelper.getOkHttpClient().newCall(this.build());
    }

    public void enqueue(Callback callback) {
        this.call().enqueue(callback);
    }

    public Response execute() {
        try (Response response = this.call().execute()) {
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
