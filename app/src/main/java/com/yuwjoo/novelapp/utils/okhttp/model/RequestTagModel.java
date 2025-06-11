package com.yuwjoo.novelapp.utils.okhttp.model;

import com.yuwjoo.novelapp.utils.okhttp.ProgressListener;

public class RequestTagModel {
    private int connectTimeout = -1; // 连接超时时间（秒）
    private int readTimeout = -1; // 读取超时时间（秒）
    private int writeTimeout = -1; // 写入超时时间（秒）
    private ProgressListener progressDownloadListener; // 下载进度监听器

    public RequestTagModel() {
    }

    public RequestTagModel(int connectTimeout, int readTimeout, int writeTimeout, ProgressListener progressDownloadListener) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
        this.progressDownloadListener = progressDownloadListener;
    }

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

    public ProgressListener getProgressDownloadListener() {
        return progressDownloadListener;
    }

    public void setProgressDownloadListener(ProgressListener progressDownloadListener) {
        this.progressDownloadListener = progressDownloadListener;
    }
}
