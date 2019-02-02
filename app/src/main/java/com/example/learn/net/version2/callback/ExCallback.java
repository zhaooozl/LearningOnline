package com.example.learn.net.version2.callback;

import android.app.Activity;
import android.os.Handler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ExCallback implements Callback {

    private Activity mActivity;

    private ISuccess iSuccess;
    private IError iError;
    private IFailure iFailure;

    private Handler handler;

    public ExCallback(ISuccess iSuccess, IError iError, IFailure iFailure, Handler handler) {
        this.iSuccess = iSuccess;
        this.iError = iError;
        this.iFailure = iFailure;
        this.handler = handler;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (iFailure != null) {
                    iFailure.onFailure();
                }
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (response.isSuccessful()) {
            // TODO: 2019/1/2  isExecuted 方法的作用
            if (call.isExecuted()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            iSuccess.onSuccess(response.body().string());
                        } catch (IOException e) {
//                            e.printStackTrace();
                            iError.onError();
                        }
                    }
                });
            }
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    iError.onError();
                }
            });
        }
    }
}
