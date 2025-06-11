package com.yuwjoo.novelapp.utils.okhttp.interceptor;

import androidx.annotation.NonNull;

import com.yuwjoo.novelapp.utils.okhttp.ProgressResponseBody;
import com.yuwjoo.novelapp.utils.okhttp.model.RequestTagModel;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ProgressDownloadInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        RequestTagModel tag = (RequestTagModel) request.tag();

        try (Response originalResponse = chain.proceed(request)) {
            if (tag != null && tag.getProgressDownloadListener() != null) {
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), tag.getProgressDownloadListener())) // 添加下载监听器
                        .build();
            } else {
                return originalResponse;
            }
        }
    }
}
