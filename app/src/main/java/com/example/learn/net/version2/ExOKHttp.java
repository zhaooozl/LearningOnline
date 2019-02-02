package com.example.learn.net.version2;

import android.os.Handler;

import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.learningonline.R;
import com.example.learn.net.interceptor.DebugInterceptor;
import com.example.learn.net.interceptor.ExCacheInterceptor;
import com.example.learn.net.interceptor.LoggingInterceptor;
import com.example.learn.net.version2.callback.ExCallback;
import com.example.learn.net.version2.callback.IError;
import com.example.learn.net.version2.callback.IFailure;
import com.example.learn.net.version2.callback.ISuccess;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

public class ExOKHttp {
    // 播放地址
    private String url;
    // 请求头
    private Map<String, String> header;
    // 请求体
    private String body;
    // 是否使用缓存
    private boolean isCache;
    // 请求成功回调
    private ISuccess iSuccess;
    // 请求失败回调
    private IError iError;
    // 请求超时回调
    private IFailure iFailure;
    // handler
    private Handler handler;
    // 连接超时
    private static final long CONNECT_TIMEOUT = 8000;
    // 读取超时
    private static final long READ_TIMEOUT = 12000;

    private OkHttpClient mClient;

    private ExOKHttp(String url,
                     Map<String, String> header,
                     String body,
                     boolean isCache,
                     ISuccess iSuccess,
                     IError iError,
                     IFailure iFailure,
                     Handler handler) {
        this.url = url;
        this.body = body;
        this.isCache = isCache;
        this.header = header;
        this.iSuccess = iSuccess;
        this.iError = iError;
        this.iFailure = iFailure;
        this.handler = handler;
        initClient();
    }

    private void initClient() {
        mClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(getLogInterceptor())
                .addInterceptor(new DebugInterceptor("learn", R.raw.learn))
                .addInterceptor(new DebugInterceptor("exam", R.raw.exam))
                .addInterceptor(new DebugInterceptor("courses", R.raw.courses))
                .addInterceptor(new DebugInterceptor("student", R.raw.student))
                .build();
    }



    public void post() {

        if (isCache) {
            mClient = mClient.newBuilder()
//                    .cache(getCache())
                    .addNetworkInterceptor(new ExCacheInterceptor())
                    .build();
        }

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .headers(getHeaders());

        requestBuilder.post(getRequestBody());

        // 创建request
        Request request = requestBuilder.build();

        // 发起请求
        mClient.newCall(request).enqueue(getCallback());
    }

    public void get() {
        if (isCache) {
            mClient = mClient.newBuilder()
//                    .cache(getCache())
                    .addNetworkInterceptor(new ExCacheInterceptor())
                    .build();
        }

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .headers(getHeaders());

        requestBuilder.get();

        // 创建request
        Request request = requestBuilder.build();

        // 发起请求
        mClient.newCall(request).enqueue(getCallback());
    }

    public void downLoad() {

    }

    private ExCallback getCallback() {
        return new ExCallback(iSuccess, iError, iFailure, handler);
    }

    /**
     * 获取Cache
     * @return
     */
//    private Cache getCache() {
//        // 缓存路径
//        Context context = Configurator.getInstance().getApplicationContext();
//        long maxCacheSize = 10 * 1024 * 1024;
//        Cache cache = new Cache(context.getCacheDir(), maxCacheSize);
//        return cache;
//    }

    private Headers getHeaders() {
        Headers.Builder builder = new Headers.Builder();
        if (header != null) {
            // 请求头
            Set<Map.Entry<String, String>> headerSet = header.entrySet();
            Iterator<Map.Entry<String, String>> headerIterator = headerSet.iterator();
            while (headerIterator.hasNext()) {
                Map.Entry<String, String> next = headerIterator.next();
                builder.add(next.getKey(), next.getValue());
            }
        }
        return builder.build();
    }

    private RequestBody getRequestBody() {
        // 请求体
        return RequestBody.create(MediaType.get("application/json; charset=utf-8"), body);
    }

    private HttpLoggingInterceptor getLogInterceptor() {
        // 打印http请求日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new LoggingInterceptor());
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    /**
     * 建造者模式
     */
    public static class Builder {
        private String url;
        private Map<String, String> header = new HashMap<>();
        private String body;
        private boolean isCache = false;
        private ISuccess iSuccess;
        private IError iError;
        private IFailure iFailure;
        private Handler handler;

        // 代码块，初始化一些默认值
        {
            handler = Configurator.getInstance().get(ConfigType.HANDLER);
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder header(String key, String value) {
            this.header.put(key, value);
            return this;
        }

        public Builder addBody(String body) {
            this.body = body;
            return this;
        }

        public Builder cache(boolean cache) {
            this.isCache = cache;
            return this;
        }

        public Builder success(ISuccess iSuccess) {
            this.iSuccess = iSuccess;
            return this;
        }

        public Builder failure(IFailure iFailure) {
            this.iFailure = iFailure;
            return this;
        }

        public Builder error(IError iError) {
            this.iError = iError;
            return this;
        }

        public Builder handler(Handler handler) {
            this.handler = handler;
            return this;
        }

        /**
         * 创建
         * @return
         */
        public ExOKHttp build() {
            return new ExOKHttp(url,
                    header,
                    body,
                    isCache,
                    iSuccess,
                    iError,
                    iFailure,
                    handler);
        }
    }
}
