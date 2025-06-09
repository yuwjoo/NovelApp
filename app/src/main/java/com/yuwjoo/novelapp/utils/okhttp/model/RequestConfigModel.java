package com.yuwjoo.novelapp.utils.okhttp.model;

import com.google.gson.JsonObject;

public class RequestConfigModel {
    private String url;

    private String method;

    private JsonObject headers;

    private JsonObject params;

    private JsonObject data;

    private long timeout;

    public RequestConfigModel(String url, String method) {
        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public RequestConfigModel setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public RequestConfigModel setMethod(String method) {
        this.method = method;
        return this;
    }

    public JsonObject getHeaders() {
        return headers;
    }

    public RequestConfigModel setHeaders(JsonObject headers) {
        this.headers = headers;
        return this;
    }

    public JsonObject getParams() {
        return params;
    }

    public RequestConfigModel setParams(JsonObject params) {
        this.params = params;
        return this;
    }

    public JsonObject getData() {
        return data;
    }

    public RequestConfigModel setData(JsonObject data) {
        this.data = data;
        return this;
    }

    public long getTimeout() {
        return timeout;
    }

    public RequestConfigModel setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }
}
