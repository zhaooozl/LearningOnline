package com.example.learn.net.version1;

import android.app.Activity;
import android.os.Handler;

import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class RequestCallback implements Callback {

    private Handler mHandler = Configurator.getInstance().get(ConfigType.HANDLER);

    public void onResponse(Call call, final Response response) throws IOException {
        final String responseBody = response.body().string();
        if (mHandler != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (response.isSuccessful()) {
                        onSuccess(responseBody);
                    } else {
                        onError(response.code(), response.message());
                    }
                }
            });

        } else {
            if (response.isSuccessful()) {

                onSuccess(responseBody);

            } else {
                onError(response.code(), response.message());
            }
        }
    }

    @Override
    public void onFailure(final Call call, final IOException e) {
        if (mHandler != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFail(call, e);
                }
            });
        } else {
            onFail(call, e);
        }
    }

    public abstract void onSuccess(String responseBody);

    public abstract void onError(int code, String msg);

    public abstract void onFail(Call call, IOException e);
}
