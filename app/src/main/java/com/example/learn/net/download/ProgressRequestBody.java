package com.example.learn.net.download;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class ProgressRequestBody extends RequestBody {

    private RequestBody requestBody;
    private File file;
    private ProgressListener listener;

    public ProgressRequestBody(RequestBody requestBody, File file, ProgressListener listener) {
        this.requestBody = requestBody;
        this.file = file;
        this.listener = listener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Source source = Okio.source(file);
        Buffer buffer = new Buffer();
        long curCount = 0;
        long readCount = -1;
        long totalCount = contentLength();
//        readCount = source.read(buffer, 2048);

        while ((readCount = source.read(buffer, 2048)) != -1) {
            sink.write(buffer, readCount);
            curCount += readCount;
            int press = (int) ((curCount * 1.0F / totalCount) * 100);
            Log.i("upload>>>>>>: ", String.valueOf(press));
            listener.onProgress(press, totalCount);
        }

        listener.onDone(totalCount);
    }
}
