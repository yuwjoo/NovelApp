package com.yuwjoo.novelapp.okhttp;

import java.io.IOException;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new BaseInterceptor()).build();
    private static final MediaType JSON = MediaType.get("application/json");

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

    /**
     * 发送post请求
     *
     * @param url      请求url
     * @param data     数据
     * @param type     数据类型
     * @param callback 请求回调
     */
    public static void post(String url, Object data, MediaType type, Callback callback) {
        Request.Builder builder = new Request.Builder().url(url);
        if (data != null) {
            RequestBody body = RequestBody.create(data.toString(), type != null ? type : JSON);
            builder.post(body);
        }
        okHttpClient.newCall(builder.build()).enqueue(callback);
    }

    /**
     * 发送同步get请求
     *
     * @param url      请求url
     */
    public static Response getSync(String url) {
        Request request = new Request.Builder().url(url).build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送同步post请求
     *
     * @param url      请求url
     * @param data     数据
     * @param type     数据类型
     */
    public static Response postSync(String url, Object data, MediaType type) {
        Request.Builder builder = new Request.Builder().url(url);
        if (data != null) {
            RequestBody body = RequestBody.create(data.toString(), type != null ? type : JSON);
            builder.post(body);
        }
        try (Response response = okHttpClient.newCall(builder.build()).execute()) {
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
