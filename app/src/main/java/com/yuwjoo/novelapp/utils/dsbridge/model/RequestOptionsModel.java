package com.yuwjoo.novelapp.utils.dsbridge.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class RequestOptionsModel {
    private String url; // 请求url
    private String method; // 请求方法
    private JsonObject headers; // 请求头
    private int timeout; // 超时时间
    private String bodyText; // body文本
    private String bodyBlobText; // body二进制文本
    private JsonArray bodyMultipartList; // body分块数据列表

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public JsonObject getHeaders() {
        return headers;
    }

    public void setHeaders(JsonObject headers) {
        this.headers = headers;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getBodyBlobText() {
        return bodyBlobText;
    }

    public void setBodyBlobText(String bodyBlobText) {
        this.bodyBlobText = bodyBlobText;
    }

    public JsonArray getBodyMultipartList() {
        return bodyMultipartList;
    }

    public void setBodyMultipartList(JsonArray bodyMultipartList) {
        this.bodyMultipartList = bodyMultipartList;
    }
}
