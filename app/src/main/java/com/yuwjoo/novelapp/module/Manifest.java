package com.yuwjoo.novelapp.module;

import com.google.gson.annotations.SerializedName;

public class Manifest {
    @SerializedName("novel-web-version")
    private String novelWebVersion;

    public String getNovelWebVersion() {
        return novelWebVersion;
    }

    public void setNovelWebVersion(String novelWebVersion) {
        this.novelWebVersion = novelWebVersion;
    }
}
