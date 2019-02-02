package com.example.learn.net.download;

import com.orhanobut.logger.Logger;

public class ProgBar implements ProgressListener {

    @Override
    public void onProgress(int mProgress, long contentSize) {
        Logger.d("onProgress={ " + "mProgress: " + mProgress + ", contentSize: " + contentSize + "}");
    }

    @Override
    public void onDone(long totalSize) {
        Logger.d("onDone={ " + "totalSize: " + totalSize + "}");
    }
}
