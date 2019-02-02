package com.example.learn.net.interceptor;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

public class LoggingInterceptor implements HttpLoggingInterceptor.Logger {


    @Override
    public void log(String message) {
        Log.d("HttpLogInfo", message);
//        Logger.d(message);
    }

}
