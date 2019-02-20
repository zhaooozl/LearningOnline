package com.example.learn.delegate.student.content;

import android.os.Bundle;
import android.view.View;

import com.example.learn.activity.MainActivity;
import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.config.UrlConfig;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.delegate.student.ExamDelegate;
import com.example.learn.delegate.teacher.ExamPaperDelegate;
import com.example.learn.learningonline.R;
import com.example.learn.net.download.OKHttpUtils;
import com.example.learn.net.download.ProgressListener;
import com.orhanobut.logger.Logger;

public class ContentItemOnClickListener implements View.OnClickListener, ProgressListener{

    String url1 = "https://publicobject.com/helloworld.txt";

    String url2 = "http://aikanvod.miguvideo.com/video/p/pg/100041/miguaikan_v300/common.wdml";

    private BaseDelegate delegate;

    private ContentHolder holder;
    private int subjectId = -1;

    public ContentItemOnClickListener(ContentHolder holder) {
        this.holder = holder;
    }

    public ContentItemOnClickListener(int subjectId, BaseDelegate delegate) {
        this.delegate = delegate;
        this.subjectId = subjectId;
        Logger.d("ContentItemOnClickListener subjectId: " + subjectId);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_download:
                Logger.d("download: +++++++++++++++++++");
                String fileName = (String) view.getTag();
                final String url = UrlConfig.SUBJECT + "?operateType=download&fileName=" + fileName;
                new OKHttpUtils()
                        .setProgressListener(this)
                        .downLoad(url);
                break;
            case R.id.iv_exam:
                MainActivity ac = (MainActivity) delegate.getActivity();
                Bundle bundle = new Bundle();
                bundle.putInt("subjectId", subjectId);
                int userType = Configurator.getInstance().get(ConfigType.USER_TYPE);
                if (userType == 2) {
                    if (delegate != null) {
                        ExamDelegate examDelegate = new ExamDelegate();
                        examDelegate.setArguments(bundle);
                        ac.start(examDelegate);
                    }
                } else if (userType == 1) {
                    if (delegate != null) {
                        ExamPaperDelegate examPaperDelegate = new ExamPaperDelegate();
                        examPaperDelegate.setArguments(bundle);
                        ac.start(examPaperDelegate);
                    }
                }

                break;
            default:

                break;

        }
    }

    @Override
    public void onProgress(int mProgress, long contentSize) {
        Logger.d("onProgress: " + mProgress);
        holder.pbProgress.setProgress(mProgress);
    }

    @Override
    public void onDone(long totalSize) {

    }
}
