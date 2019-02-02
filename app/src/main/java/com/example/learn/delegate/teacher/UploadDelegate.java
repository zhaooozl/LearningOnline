package com.example.learn.delegate.teacher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.learningonline.R;
import com.example.learn.net.download.OKHttpUtils;
import com.example.learn.net.download.ProgressListener;
import com.joanzapata.iconify.widget.IconButton;
import com.joanzapata.iconify.widget.IconTextView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

public class UploadDelegate extends BaseDelegate implements View.OnClickListener, ProgressListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_subject_name)
    EditText etSubjName;
    @BindView(R.id.et_subject_desc)
    EditText etSubjDesc;
    @BindView(R.id.itv_courseware_file)
    IconTextView itvCourseFile;
    @BindView(R.id.ib_add_courseware)
    IconButton itvAddCourseWare;
    @BindView(R.id.btn_submit_course)
    Button btnSubmit;

    String path;

    ProgressDialog mDialog;

    @Override
    public Object getLayout() {
        return R.layout.delegate_upload;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        Logger.d("onBindView: ");
        ivBack.setOnClickListener(this);
        itvAddCourseWare.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Logger.d("onClick id: " + id);
        switch (id) {
            case R.id.iv_back:
                pop();
                break;
            case R.id.ib_add_courseware:
                Logger.d("onClick courseware_file");
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
                break;

            case R.id.btn_submit_course:
//                /storage/emulated/0
                String path2 = "/storage/emulated/0/update.zip";
                OKHttpUtils okHttpUtils = new OKHttpUtils();
                okHttpUtils.setProgressListener(this);
                okHttpUtils.upLoad(path2, "update.zip");
                showProgressDialog();
//                String url = "http://192.168.2.1:8080/LearningOnline/upload";
//                new OKHttp()
//                        .post(url, "", new RequestCallback() {
//                            @Override
//                            public void onSuccess(String responseBody) {
//
//                            }
//
//                            @Override
//                            public void onError(int code, String msg) {
//
//                            }
//
//                            @Override
//                            public void onFail(Call call, IOException e) {
//
//                            }
//                        });
                break;
            default:
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
//            String path = "";
            Uri uri = data.getData();
            Logger.d("path: " + uri.getPath());
            String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            Logger.d("absolutePath: " + absolutePath);
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                path = uri.getPath();
                itvCourseFile.setText("{fa-file}" + path);
                return;
            } else {
                path = uri.getPath();
                itvCourseFile.setText("{fa-file}" + path);
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {

            } else {

            }
        }
    }

    @SuppressLint("NewApi")
    public String getPath(final Context context, Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {


        }

        return null;
    }

    @Override
    public void onProgress(int mProgress, long contentSize) {
        Logger.d("onProgress: " + mProgress + "\ncontentSize: " + contentSize);
        if (mDialog != null) {
            mDialog.setProgress(mProgress);
        }
    }

    @Override
    public void onDone(long totalSize) {
        Logger.d("onDone: " + totalSize);
        mDialog.cancel();
    }


    private void showProgressDialog() {
        mDialog = new ProgressDialog(getActivity());
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setProgress(0);
        mDialog.setTitle("上传进度");
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setMax(100);
        mDialog.show();
    }
}