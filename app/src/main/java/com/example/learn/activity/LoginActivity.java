package com.example.learn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.learn.activity.base.BaseActivity;
import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.config.UrlConfig;
import com.example.learn.entiry.common.LoginBean;
import com.example.learn.entiry.response.RespObjBean;
import com.example.learn.learningonline.R;
import com.example.learn.net.version1.OKHttp;
import com.example.learn.net.version1.RequestCallback;
import com.example.learn.view.LoadingDialog;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_username)
    AppCompatEditText etUserName;
    @BindView(R.id.et_passwd)
    AppCompatEditText etPasswd;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.btn_go_changpwd)
    AppCompatButton goChangePwd;
    @BindView(R.id.btn_go_register)
    AppCompatButton goRegister;

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void onBindView(Bundle savedInstanceState) {
        btnLogin.setOnClickListener(this);
        goChangePwd.setOnClickListener(this);
        goRegister.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_go_changpwd:
                break;

            case R.id.btn_go_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    private void login() {
        final LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        String userName = etUserName.getText().toString().trim();
        String passwd = etPasswd.getText().toString().trim();

        String url = UrlConfig.LOGIN + "?userId=" + userName + "&passwd=" + passwd;
        OKHttp.getInstance()
                .get(url, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        Logger.json(responseBody);
                        RespObjBean<LoginBean> respObjBean = JSON.parseObject(responseBody, new TypeReference<RespObjBean<LoginBean>>(){});
                        LoginBean data = respObjBean.getData();
                        int userType = data.getUserType();
                        int loginCode = data.getLoginCode();
                        String loginMsg = data.getLoginMsg();
                        if (loginCode == 0) {
                            Configurator.getInstance().with(ConfigType.USER_TYPE, userType);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), loginMsg, Toast.LENGTH_SHORT).show();
                        }
//                        loadingDialog.cancel();
                    }

                    @Override
                    public void onError(int code, String msg) {
//                        loadingDialog.cancel();
                        Toast.makeText(getApplicationContext(), "登录错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(Call call, IOException e) {
//                        loadingDialog.cancel();
                        Toast.makeText(getApplicationContext(), "登录请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
