package com.yuwjoo.novelapp.utils.okhttp;

import com.yuwjoo.novelapp.okhttp.BaseInterceptor;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpHelper {
    private static final OkHttpClient okHttpClient;

    static {
        okHttpClient = new OkHttpClient.Builder().addInterceptor(new BaseInterceptor()).build();
    }

    /**
     * 获取okhttp客户端实例
     *
     * @return okhttp客户端实例
     */
    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    /**
     * 发送get请求
     *
     * @param url      请求url
     * @param callback 请求回调
     */
    public static void get(String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
