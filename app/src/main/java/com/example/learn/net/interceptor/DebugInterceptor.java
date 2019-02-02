package com.example.learn.net.interceptor;

import com.example.learn.learningonline.R;
import com.example.learn.utils.FileUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DebugInterceptor implements Interceptor {

    private String deBugUrl;

    private int rawId;

    public DebugInterceptor(String deBugUrl, int rawId) {
        this.deBugUrl = deBugUrl;
        this.rawId = rawId;
    }

    /**
     * 拦截
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        // 拦截测试url，用本地的测试数据
        String url = chain.request().url().toString();
        Logger.d("DebugInterceptor: " + url);
        if (deBugUrl != null && url.contains(deBugUrl)) {
            return getResponse(chain, rawId);
        }
        Logger.d("DebugInterceptor:222");
        return chain.proceed(chain.request());
    }

    /**
     * 测试用来获取响应数据
     * @return
     */
    private Response getResponse(Chain chain, int rawId) {
        String json = FileUtils.getRawFile(rawId);
        Response response = new Response.Builder()
                .code(200)
                .message("ok")
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();

        return response;
    }
}
