package com.yuwjoo.novelapp.utils.okhttp.interceptor;

import androidx.annotation.NonNull;

import com.yuwjoo.novelapp.utils.okhttp.model.RequestTagModel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TimeoutInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        RequestTagModel tag = (RequestTagModel) request.tag();

        if (tag != null) {
            if (tag.getConnectTimeout() > -1) {
                chain = chain.withConnectTimeout(tag.getConnectTimeout(), TimeUnit.SECONDS);
            }
            if (tag.getReadTimeout() > -1) {
                chain = chain.withReadTimeout(tag.getReadTimeout(), TimeUnit.SECONDS);
            }
            if (tag.getWriteTimeout() > -1) {
                chain = chain.withWriteTimeout(tag.getWriteTimeout(), TimeUnit.SECONDS);
            }
        }

        return chain.proceed(request);
    }
}
