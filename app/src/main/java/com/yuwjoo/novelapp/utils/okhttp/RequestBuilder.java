package com.yuwjoo.novelapp.utils.okhttp;

import java.io.File;
import java.util.Map;

import okhttp3.RequestBody;

public class RequestBuilder {
    private String url; // 请求地址
    private String method; // 请求方法
    private Map<String, String> headers; // 请求头
    private Map<String, String> params; // 查询参数
    private RequestBody requestBody; // 请求body
    private int connectTimeout = -1; // 连接超时时间（秒）
    private int readTimeout = -1; // 读取超时时间（秒）
    private int writeTimeout = -1; // 写入超时时间（秒）

    public RequestBuilder() {
        File file;
    }
}
