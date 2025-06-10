package com.yuwjoo.novelapp.utils.okhttp.model;

public class RequestTagModel {
    private int connectTimeout = -1; // 连接超时时间（秒）
    private int readTimeout = -1; // 读取超时时间（秒）
    private int writeTimeout = -1; // 写入超时时间（秒）

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }
}
