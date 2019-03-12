package com.example.learn.delegate.teacher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.config.UrlConfig;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.learningonline.R;
import com.example.learn.net.download.OKHttpUtils;
import com.example.learn.net.download.ProgressListener;
import com.joanzapata.iconify.widget.IconButton;
import com.joanzapata.iconify.widget.IconTextView;
import com.orhanobut.logger.Logger;

import java.io.File;

import butterknife.BindView;

public class UploadDelegate extends BaseDelegate implements View.OnClickListener, ProgressListener {
    // 返回
    @BindView(R.id.iv_back)
    ImageView ivBack;
    // 科目名称
    @BindView(R.id.et_subject_name)
    EditText etSubjName;
    // 科目描述
    @BindView(R.id.et_subject_desc)
    EditText etSubjDesc;
    // 课件名
    @BindView(R.id.itv_courseware_file)
    IconTextView itvCourseFile;
    // 添加课件
    @BindView(R.id.ib_add_courseware)
    IconButton itvAddCourseWare;
    // 提交
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
                final String userId = Configurator.getInstance().get(ConfigType.USERID).toString().trim();
                String subjectName = etSubjName.getText().toString().trim();
                String subjectDesc = etSubjDesc.getText().toString().trim();
                if ("".equals(subjectName)) {
                    Toast.makeText(getActivity(), "科目名称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
//                String path2 = "/storage/emulated/0/common.lua";
                Logger.d("path: " + path);
                OKHttpUtils okHttpUtils = new OKHttpUtils();
                okHttpUtils.setProgressListener(this);
                final String url = UrlConfig.SUBJECT + "?operateType=upload&userId=" + userId + "&subjectName=" + subjectName + "&subjectDesc=" + subjectDesc;
                final String url2 = UrlConfig.UPLOAD + "?operateType=upload&userId=" + userId + "&subjectName=" + subjectName + "&subjectDesc=" + subjectDesc;
                okHttpUtils.upLoad(url2, path, new File(path).getName());
                showProgressDialog();
                break;
            default:
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                path = uri.getPath();
                Toast.makeText(getActivity(), path + "11111", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = getPath(getActivity(), uri);
                Toast.makeText(getActivity(), path, Toast.LENGTH_SHORT).show();
            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(uri);
                Toast.makeText(getActivity(), path + "222222", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if(null!=cursor&&cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
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
