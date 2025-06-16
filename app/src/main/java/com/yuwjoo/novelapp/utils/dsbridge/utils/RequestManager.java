package com.yuwjoo.novelapp.utils.dsbridge.utils;

import okhttp3.Call;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RequestManager {
    private static final Map<String, Call> callMap = new ConcurrentHashMap<>();

    // 添加请求并返回UUID
    public static String addCall(Call call) {
        String uuid = UUID.randomUUID().toString();
        callMap.put(uuid, call);
        return uuid;
    }

    // 根据UUID取消请求
    public static void cancelCall(String uuid) {
        Call call = callMap.get(uuid);
        if (call != null && !call.isCanceled()) {
            call.cancel();
            callMap.remove(uuid);
        }
    }

    // 获取请求对象
    public static Call getCall(String uuid) {
        return callMap.get(uuid);
    }

    // 删除请求对象
    public static void deleteCall(String uuid) {
        callMap.remove(uuid);
    }

    // 批量取消所有请求
    public static void cancelAll() {
        callMap.values().forEach(call -> {
            if (!call.isCanceled()) call.cancel();
        });
        callMap.clear();
    }
}
