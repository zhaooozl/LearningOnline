package com.example.learn.net.version1;

import com.example.learn.config.UrlConfig;
import com.example.learn.learningonline.R;
import com.example.learn.net.ExCookieJar;
import com.example.learn.net.interceptor.DebugInterceptor;
import com.example.learn.net.interceptor.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

public class OKHttp {

    private static final long CONNECT_TIMEOUT = 8000;

    private static final long READ_TIMEOUT = 12000;

    private OkHttpClient mClient;

    public OKHttp() {

        mClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS) // 毫秒
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .cookieJar(new ExCookieJar()) //cookie
                .addNetworkInterceptor(getLogInterceptor())
                .addInterceptor(new DebugInterceptor(UrlConfig.LOACL_LEARN, R.raw.learn))
                .addInterceptor(new DebugInterceptor(UrlConfig.LOACL_EXAM, R.raw.exam))
                .addInterceptor(new DebugInterceptor(UrlConfig.LOCAL_LOGIN, R.raw.login))
                .addInterceptor(new DebugInterceptor(UrlConfig.LOACL_COURSES, R.raw.courses))
                .addInterceptor(new DebugInterceptor(UrlConfig.LOCAL_STUDENT, R.raw.student))
                .build();
    }

    // 单例模式
    private static class Holder {
        private static final OKHttp INSTANCE = new OKHttp();
    }
    // 获取单例
    public static OKHttp getInstance() {
        return Holder.INSTANCE;
    }

    public void get(String url, RequestCallback callback) {
        Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

        mClient.newCall(request)
                .enqueue(callback);
    }


    public void post(String url, String body, RequestCallback callback) {
        // application/x-www-form-urlencoded
        RequestBody requestBody = RequestBody
                .create(MediaType.get("application/x-www-form-urlencoded; charset=utf-8"), body);
        Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
        mClient.newCall(request)
                .enqueue(callback);

    }


    private HttpLoggingInterceptor getLogInterceptor() {
        // 设置http请求日志
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new LoggingInterceptor());
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }



}
