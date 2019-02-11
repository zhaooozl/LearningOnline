package com.example.learn.activity;

import android.content.Intent;
import android.os.Bundle;

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
import com.example.learn.timer.BaseTimerTask;
import com.example.learn.timer.OnTimerListener;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Timer;

import okhttp3.Call;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity implements OnTimerListener {

    private int cutDown = 3;
    private Timer mTimer;
    private boolean isLogin = false;
//    private int userType;

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void onBindView(Bundle savedInstanceState) {

        String userId = ExPreferences.getString(PrefKey.USERID.name());
        boolean isLogin = ExPreferences.getBoolean(PrefKey.ISLOGIN.name());
        int userType = ExPreferences.getInt(PrefKey.USERTYPE.name());
        Configurator
                .getInstance()
                .with(ConfigType.USERID, userId)
                .with(ConfigType.ISLOGIN, isLogin)
                .with(ConfigType.USER_TYPE, userType);

//        loginRequest();
        mTimer = new Timer();
        mTimer.schedule(new BaseTimerTask(this), 0, 1000);
    }

    /**
     * 倒计时3秒进入主页
     */
    @Override
    public void onTimer() {
        Logger.d("onTimer: " + cutDown);
        if (cutDown < 0) {
            mTimer.cancel();
            Intent intent = null;
            boolean isLogin = Configurator.getInstance().get(ConfigType.ISLOGIN);
            if (isLogin) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        } else {
            cutDown--;
        }
    }

    private void loginRequest() {
        OKHttp.getInstance()
                .get(UrlConfig.LOGIN, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        Logger.json(responseBody);
                        RespObjBean<LoginBean> respObjBean = JSON.parseObject(responseBody, new TypeReference<RespObjBean<LoginBean>>() {
                        });
                        LoginBean data = respObjBean.getData();
                        int loginCode = data.getLoginCode();
                        if (loginCode == 0) {


                        } else {


                        }
                        int userType = data.getUserType();
                        Configurator.getInstance().with(ConfigType.USER_TYPE, userType);
                        isLogin = true;
                    }

                    @Override
                    public void onError(int code, String msg) {
                        isLogin = false;
                    }

                    @Override
                    public void onFail(Call call, IOException e) {
                        isLogin = false;
                    }
                });

    }
}
