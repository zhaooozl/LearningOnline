package com.example.learn.net.download;

import android.os.Handler;
import android.util.Log;

import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressResponseBody extends ResponseBody {

    private ResponseBody responseBody;
    private BufferedSource bufferedSource;
    private ProgressListener listener;

    private Handler mHandler = Configurator.getInstance().get(ConfigType.HANDLER);

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener listener) {
        this.responseBody = responseBody;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(getSource(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source getSource(Source source) {

        return new ForwardingSource(source) {
            long totalSize = 0;
            long sum = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                if (totalSize == 0) {
                    totalSize = contentLength();
                }
                long len = super.read(sink, byteCount);
                sum += (len == -1 ? 0 : len);
                final int press = (int) ((sum * 1.0f / totalSize) * 100);
                Log.i("///////////", String.valueOf(totalSize));
                if (len == -1) {
                    if (mHandler != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onDone(totalSize);

                            }
                        });
                    }
                } else {
                    if (mHandler != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onProgress(press, totalSize);
                            }
                        });
                    }
                }
                return len;
            }
        };
    }
}
