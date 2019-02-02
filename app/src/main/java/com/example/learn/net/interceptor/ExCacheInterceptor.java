package com.example.learn.net.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ExCacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Response response = chain.proceed(request);

        int maxAge = 30;
        response.newBuilder()
                .header("Cache-Control", "public, max-age=" + maxAge)
                .removeHeader("Pragma")
                .build();

        return response;
    }
}
