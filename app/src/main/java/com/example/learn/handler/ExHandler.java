package com.example.learn.handler;

import android.os.Handler;
import android.os.Message;

public class ExHandler extends Handler {

    private OnHandleListener onHandleListener;

    // 设置监听事件
    public void setOnHandleListener(OnHandleListener onHandleListener) {
        this.onHandleListener = onHandleListener;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (onHandleListener != null) {
            onHandleListener.onHandleMessage(msg);
        }
    }

    /**
     * 监听接口
     */
    private interface OnHandleListener {
        void onHandleMessage(Message msg);
    }
}
