package com.yuwjoo.novelapp.utils.okhttp;

public interface ProgressListener {
    void onProgress(long bytesRead, long contentLength, boolean done);
}
