package com.yuwjoo.novelapp.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.yuwjoo.novelapp.module.Manifest;
import com.yuwjoo.novelapp.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CheckUpdatePresenter implements ICheckUpdatePresenter {

    public CheckUpdatePresenter() {
        getManifest();
    }

    private void getManifest() {
        OkHttpUtils.get("https://yuwjoo-private-cloud-storage.oss-cn-shenzhen.aliyuncs.com/novel/manifest.json", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    String res = response.body().string();
                    Gson gson = new Gson();
                    Manifest manifest = gson.fromJson(res, Manifest.class);
                    Log.i("aa", manifest.getNovelWebVersion());
                }
            }
        });
    }
}
