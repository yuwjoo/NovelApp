package com.yuwjoo.novelapp.utils.dsbridge.model;

import com.google.gson.JsonObject;

public class RequestOptionsModel {
    private String url; // 请求url
    private String method; // 请求方法
    private JsonObject headers; // 请求头
    private JsonObject params; // 查询参数
    private Object data; // body数据
    private String dataType; // body数据类型
    private int timeout; // 超时时间
    private String responseType; // 响应数据类型
    private boolean cancelable; // 该请求是否需要取消
    private boolean enableUploadProgressListener; // 启用上传进度监听器
    private boolean enableDownloadProgressListener; // 启用下载进度监听器

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

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isEnableUploadProgressListener() {
        return enableUploadProgressListener;
    }

    public void setEnableUploadProgressListener(boolean enableUploadProgressListener) {
        this.enableUploadProgressListener = enableUploadProgressListener;
    }

    public boolean isEnableDownloadProgressListener() {
        return enableDownloadProgressListener;
    }

    public void setEnableDownloadProgressListener(boolean enableDownloadProgressListener) {
        this.enableDownloadProgressListener = enableDownloadProgressListener;
    }
}
