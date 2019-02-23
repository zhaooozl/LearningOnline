package com.example.learn.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.learn.activity.base.BaseActivity;
import com.example.learn.config.UrlConfig;
import com.example.learn.entiry.common.CommonStatusBean;
import com.example.learn.entiry.response.RespObjBean;
import com.example.learn.learningonline.R;
import com.example.learn.net.version1.OKHttp;
import com.example.learn.net.version1.RequestCallback;
import com.example.learn.view.LoadingDialog;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;

public class ChangePwdActivity extends BaseActivity implements View.OnClickListener {
    // 旧密码
    @BindView(R.id.et_userid)
    AppCompatEditText etUserId;
    // 旧密码
    @BindView(R.id.et_oldPasswd)
    AppCompatEditText etOldPasswd;
    // 新密码
    @BindView(R.id.et_newPasswd)
    AppCompatEditText etNewPasswd;
    // 确认密码
    @BindView(R.id.et_confirmNewPasswd)
    AppCompatEditText etConfirmPasswd;
    // 确认
    @BindView(R.id.btn_confirm)
    AppCompatButton btnConfirm;
    // 返回button
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @Override
    public int getLayout() {
        return R.layout.activity_changepwd;
    }

    @Override
    public void onBindView(Bundle savedInstanceState) {
        // 确认button点击监听事件
        btnConfirm.setOnClickListener(this);
        // 返回button监听
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_confirm:
                changePasswd();
                break;
            case R.id.iv_back: //返回
                finish();
                break;
            default:
                break;
        }
    }

    private void changePasswd() {
        String userId = etUserId.getText().toString().trim();
        String oldPasswd = etOldPasswd.getText().toString().trim();
        String newPasswd = etNewPasswd.getText().toString().trim();
        String confirmPasswd = etConfirmPasswd.getText().toString().trim();

        if ("".equals(userId)) {
            Toast.makeText(this, "请输入学号（工号）！", Toast.LENGTH_SHORT).show();
            return;
        }

        if ("".equals(oldPasswd)) {
            Toast.makeText(this, "请输入旧密码！", Toast.LENGTH_SHORT).show();
            return;
        }

        if ("".equals(newPasswd) || "".equals(confirmPasswd) || !newPasswd.equals(confirmPasswd)) {
            Toast.makeText(this, "请确认新密码！", Toast.LENGTH_SHORT).show();
            return;
        }

        final LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.show();

        final String url = UrlConfig.CHANGEPASSWD + "?userId=" + userId + "&oldPasswd=" + oldPasswd + "&newPasswd=" + newPasswd;
        OKHttp.getInstance()
                .get(url, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        loadingDialog.cancel();
                        RespObjBean<CommonStatusBean> respObjBean = JSON.parseObject(responseBody, new TypeReference<RespObjBean<CommonStatusBean>>() {
                        });
                        CommonStatusBean passwdBean = respObjBean.getData();
                        int code = passwdBean.getCode();
                        String msg = passwdBean.getMsg();
                        // code == 0 表示修改密码成功
                        if (code == 0) {
                            finish();
                        } else {
                        }
                        Toast.makeText(ChangePwdActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        loadingDialog.cancel();
                        Toast.makeText(ChangePwdActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(Call call, IOException e) {
                        loadingDialog.cancel();
                        Toast.makeText(ChangePwdActivity.this, "请求超时", Toast.LENGTH_SHORT).show();
                    }
                });


    }


}
