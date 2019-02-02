package com.example.learn.delegate.student.content;

import android.view.View;

import com.example.learn.activity.MainActivity;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.delegate.student.ExamDelegate;
import com.example.learn.net.download.OKHttpUtils;
import com.example.learn.net.download.ProgressListener;
import com.orhanobut.logger.Logger;

public class ContentItemOnClickListener implements View.OnClickListener, ProgressListener{

    String url1 = "https://publicobject.com/helloworld.txt";

    String url2 = "http://aikanvod.miguvideo.com/video/p/pg/100041/miguaikan_v300/common.wdml";

    private BaseDelegate delegate;

    private ContentHolder holder;

    public ContentItemOnClickListener(ContentHolder holder) {
        this.holder = holder;
    }

    public ContentItemOnClickListener(BaseDelegate delegate) {
        this.delegate = delegate;
    }


    @Override
    public void onClick(View view) {
        ClickType tag = (ClickType) view.getTag();
        switch (tag) {
            case DOWN_LOAD:
                Logger.d("download: +++++++++++++++++++");
                new OKHttpUtils()
                        .setProgressListener(this)
                        .downLoad(url2);
//                new ExOKHttp.Builder()
//                        .url(url2)
//                        .success(new ISuccess() {
//                            @Override
//                            public void onSuccess(String responseJson) {
//                                Logger.d("responseJson: " + responseJson);
//                            }
//                        })
//                        .failure(new IFailure() {
//                            @Override
//                            public void onFailure() {
//                                Logger.d("onFailure: ");
//
//                            }
//                        })
//                        .error(new IError() {
//                            @Override
//                            public void onError() {
//                                Logger.d("onError: ");
//
//                            }
//                        })
//                        .build()
//                        .get();

                break;
            case TAKE_EXAM:
                if (delegate != null) {
                    MainActivity ac = (MainActivity) delegate.getActivity();
                    ac.start(new ExamDelegate());
                }
                break;
            default:

                break;

        }
    }

    @Override
    public void onProgress(int mProgress, long contentSize) {
        holder.pbProgress.setProgress(mProgress);
    }

    @Override
    public void onDone(long totalSize) {

    }
}
