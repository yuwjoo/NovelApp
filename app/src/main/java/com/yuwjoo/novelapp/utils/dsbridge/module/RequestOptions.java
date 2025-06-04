package com.yuwjoo.novelapp.utils.dsbridge.module;

import com.google.gson.JsonObject;

public class RequestOptions {
    private String url;

    private String method;

    private JsonObject headers;

    private JsonObject params;

    private JsonObject data;

    private long timeout;

    private String responseType;

    private boolean cancelable;

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public JsonObject getParams() {
        return params;
    }

    public void setParams(JsonObject params) {
        this.params = params;
    }

    public JsonObject getHeaders() {
        return headers;
    }

    public void setHeaders(JsonObject headers) {
        this.headers = headers;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
