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
import com.example.learn.storage.ExPreferences;
import com.example.learn.storage.PrefKey;
import com.example.learn.view.LoadingDialog;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    // 用户id
    @BindView(R.id.et_userid)
    AppCompatEditText etUserId;
    // 密码
    @BindView(R.id.et_passwd)
    AppCompatEditText etPasswd;
    // 登录button
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    // 修改密码button
    @BindView(R.id.btn_go_changpwd)
    AppCompatButton goChangePwd;
    // 注册button
    @BindView(R.id.btn_go_register)
    AppCompatButton goRegister;

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void onBindView(Bundle savedInstanceState) {
        // 登录button点击监听
        btnLogin.setOnClickListener(this);
        // 修改密码button点击监听
        goChangePwd.setOnClickListener(this);
        // 注册button点击监听
        goRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_login: // 请求登录
                login();
                break;
            case R.id.btn_go_changpwd: // 跳转修改密码页面
                Intent intent1 = new Intent(LoginActivity.this, ChangePwdActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_go_register: // 跳转注册页面
                Intent intent2 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    /**
     * 登录请求
     */
    private void login() {
        // 显示loading
        final LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        final String userId = etUserId.getText().toString().trim();
        final String passwd = etPasswd.getText().toString().trim();

        String url = UrlConfig.LOGIN + "?userId=" + userId + "&passwd=" + passwd;
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

                            Configurator.getInstance()
                                    .with(ConfigType.USER_TYPE, userType)
                                    .with(ConfigType.USERID, userId)
                                    .with(ConfigType.PASSWD, passwd)
                                    .with(ConfigType.ISLOGIN, true);

                            ExPreferences.putString(PrefKey.USERID.name(), userId);
                            ExPreferences.putString(PrefKey.PASSWD.name(), passwd);
                            ExPreferences.putBoolean(PrefKey.ISLOGIN.name(), true);
                            ExPreferences.putInt(PrefKey.USERTYPE.name(), userType);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), loginMsg, Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.cancel();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        loadingDialog.cancel();
                        Toast.makeText(getApplicationContext(), "登录错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(Call call, IOException e) {
                        loadingDialog.cancel();
                        Toast.makeText(getApplicationContext(), "登录请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
