package com.example.learn.delegate.student.content;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.learn.activity.MainActivity;
import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.config.UrlConfig;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.delegate.bottom.UserType;
import com.example.learn.delegate.student.ExamDelegate;
import com.example.learn.delegate.teacher.ExamPaperDelegate;
import com.example.learn.entiry.common.CommonStatusBean;
import com.example.learn.entiry.response.RespObjBean;
import com.example.learn.learningonline.R;
import com.example.learn.net.download.OKHttpUtils;
import com.example.learn.net.download.ProgressListener;
import com.example.learn.net.version1.OKHttp;
import com.example.learn.net.version1.RequestCallback;
import com.example.learn.view.LoadingDialog;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Call;

public class ContentItemOnClickListener implements View.OnClickListener, ProgressListener, DialogInterface.OnClickListener {

    String url1 = "https://publicobject.com/helloworld.txt";

    String url2 = "http://aikanvod.miguvideo.com/video/p/pg/100041/miguaikan_v300/common.wdml";

    private BaseDelegate delegate;

    private ContentHolder holder;
    private int subjectId = -1;
    private String teacherId;
    EditText editText;

    public ContentItemOnClickListener(ContentHolder holder) {
        this.holder = holder;
    }

    public ContentItemOnClickListener(String teacherId, BaseDelegate delegate) {
        this.teacherId = teacherId;
        this.delegate = delegate;
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
                if (userType == UserType.STUDENT) {
                    if (delegate != null) {
                        ExamDelegate examDelegate = new ExamDelegate();
                        examDelegate.setArguments(bundle);
                        ac.start(examDelegate);
                    }
                } else if (userType == UserType.TEACHER || userType == UserType.ADMIN) {
                    if (delegate != null) {
                        ExamPaperDelegate examPaperDelegate = new ExamPaperDelegate();
                        examPaperDelegate.setArguments(bundle);
                        ac.start(examPaperDelegate);
                    }
                }

                break;
            case R.id.iv_comment:
                showInputDialog();
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

    private void showInputDialog() {
        /*@setView 装入一个EditView
         */
        editText = new EditText(delegate.getActivity());
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(delegate.getActivity());
        inputDialog.setTitle("评论").setView(editText);
        inputDialog.setPositiveButton("确定", this)
                .setNegativeButton("取消", this)
                .show();
    }

    @Override
    public void onClick(final DialogInterface dialog, int which) {
        switch (which) {
            case AlertDialog.BUTTON_POSITIVE:


                final LoadingDialog loadingDialog = new LoadingDialog(delegate.getActivity());
                loadingDialog.show();

                final String commentDesc = editText.getText().toString().trim();
                final String studentId = Configurator.getInstance().get(ConfigType.USERID);
                final String url = UrlConfig.COMMENT + "?operateType=insert&teacherId=" +
                        teacherId + "&studentId=" + studentId + "&commentDesc=" + commentDesc;

                OKHttp.getInstance()
                        .get(url, new RequestCallback() {
                            @Override
                            public void onSuccess(String responseBody) {
                                RespObjBean<CommonStatusBean> respObjBean = JSON.parseObject(responseBody, new TypeReference<RespObjBean<CommonStatusBean>>() {});
                                CommonStatusBean data = respObjBean.getData();
                                if (data.getCode() == 0) {
                                    dialog.cancel();
                                    loadingDialog.cancel();
                                    Toast.makeText(delegate.getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                                } else {
                                    loadingDialog.cancel();
                                }
                            }

                            @Override
                            public void onError(int code, String msg) {
                                loadingDialog.cancel();
                            }

                            @Override
                            public void onFail(Call call, IOException e) {
                                loadingDialog.cancel();
                            }
                        });

                break;
            case AlertDialog.BUTTON_NEGATIVE:
                dialog.cancel();
                break;
            default:
                dialog.cancel();
                break;
        }
    }
}
