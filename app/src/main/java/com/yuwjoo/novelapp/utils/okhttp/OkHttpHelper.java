package com.yuwjoo.novelapp.utils.okhttp;

import com.yuwjoo.novelapp.utils.okhttp.interceptor.ProgressDownloadInterceptor;
import com.yuwjoo.novelapp.utils.okhttp.interceptor.TimeoutInterceptor;

import okhttp3.OkHttpClient;

public class OkHttpHelper {
    private static final OkHttpClient okHttpClient;

    static {
        okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new TimeoutInterceptor())
                .addInterceptor(new ProgressDownloadInterceptor())
                .build();
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
     * 创建请求
     *
     * @return 请求建造器
     */
    public static RequestBuilder newRequest() {
        return new RequestBuilder();
    }
}
