package com.example.learn.net.download;

import android.os.Environment;
import android.text.TextUtils;

import com.example.learn.net.interceptor.LoggingInterceptor;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OKHttpUtils {


    // 连接超时
    private static final long CONNECT_TIMEOUT = 8000;
    // 读取超时
    private static final long READ_TIMEOUT = 12000;

    ProgressListener progressListener;

    public OKHttpUtils setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
        return this;
    }

    /**
     * 下载
     *
     * @param url
     */
    public void downLoad(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(getLogInterceptor())
                .addNetworkInterceptor(new InterNetInterceptor(progressListener))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Logger.d("onFailure ");

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        write(response);
                    }
                });
    }

    public void upLoad(String url, String path, String fileName) {
        Logger.d("upload url: " + url);
        File file = new File(path);
        Logger.d("upLoad file is exists: " + file.exists());
        final String type = "application/octet-stream";
        RequestBody requestBody = RequestBody.create(MediaType.parse(type), file);

        ProgressRequestBody progressRequestBody = new ProgressRequestBody(requestBody, file, progressListener);

        MultipartBody multiBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName, progressRequestBody)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(multiBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }


    private void write(Response response) {
        String fileName = getHeaderFileName(response);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path, fileName);
        Logger.d("write file: " + file.getAbsolutePath());

        FileOutputStream fos = null;
        InputStream is = response.body().byteStream();

        try {
            fos = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
            is.close();
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static String getHeaderFileName(Response response) {
        String dispositionHeader = response.header("Content-Disposition");
        if (!TextUtils.isEmpty(dispositionHeader)) {
            dispositionHeader.replace("attachment;filename=", "");
            dispositionHeader.replace("filename*=utf-8", "");
            String[] strings = dispositionHeader.split("; ");
            if (strings.length > 1) {
                dispositionHeader = strings[1].replace("filename=", "");
                dispositionHeader = dispositionHeader.replace("\"", "");
                return dispositionHeader;
            }
            return "";
        }
        return "";
    }


    private HttpLoggingInterceptor getLogInterceptor() {
        // 打印http请求日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new LoggingInterceptor());
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
}
