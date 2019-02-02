package com.example.learn.net.download;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class InterNetInterceptor implements Interceptor {

    private ProgressListener progressListener;

    public InterNetInterceptor(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(new ProgressResponseBody(response.body(), progressListener)).build();
    }
}
