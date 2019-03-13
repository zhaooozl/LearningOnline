package com.example.learn.delegate.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.learn.activity.ChangePwdActivity;
import com.example.learn.activity.LoginActivity;
import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.config.UrlConfig;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.entiry.admin.UserInfoBean;
import com.example.learn.entiry.response.RespListBean;
import com.example.learn.entiry.response.RespObjBean;
import com.example.learn.entiry.student.Student;
import com.example.learn.learningonline.R;
import com.example.learn.net.version1.OKHttp;
import com.example.learn.net.version1.RequestCallback;
import com.example.learn.net.version2.ExOKHttp;
import com.example.learn.net.version2.callback.IError;
import com.example.learn.net.version2.callback.IFailure;
import com.example.learn.net.version2.callback.ISuccess;
import com.example.learn.storage.ExPreferences;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class MyDelegate extends BaseDelegate implements View.OnClickListener {
    // 头像
    @BindView(R.id.iv_profile_pic)
    ImageView ivProfilePic;
    // 用户id
    @BindView(R.id.tv_student_id)
    TextView tvId;
    // 昵称
    @BindView(R.id.tv_username)
    TextView tvName;
    // 性别
    @BindView(R.id.tv_gender)
    TextView tvGender;
    // 出生年月
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    // 学院
    @BindView(R.id.tv_collage)
    TextView tvCollage;
    // 专业
    @BindView(R.id.tv_major)
    TextView tvMajor;
    // 修改密码button
    @BindView(R.id.btn_changepwd)
    TextView tvChangePwd;
    // 退出登录button
    @BindView(R.id.btn_logout)
    TextView tvLogout;

    @Override
    public Object getLayout() {
        return R.layout.delegate_my;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        // 设置监听事件
        tvLogout.setOnClickListener(this);
        tvChangePwd.setOnClickListener(this);
        // 获取个人信息
        getMyData();
    }

    private void getMyData() {
        final String url = UrlConfig.USERINFO + "?operateType=query&userId=" + Configurator.getInstance().get(ConfigType.USERID);
        OKHttp.getInstance()
                .get(url, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        Logger.d(responseBody);
                        RespListBean<UserInfoBean> baseBean = JSON.parseObject(responseBody, new TypeReference<RespListBean<UserInfoBean>>() {
                        });
                        List<UserInfoBean> userInfoBeans = baseBean.getData();
                        UserInfoBean userInfoBean = userInfoBeans.get(0);
                        tvId.setText("学号: " + userInfoBean.getUserId());
                        tvName.setText(userInfoBean.getUserName());
                        tvGender.setText("性别: " + userInfoBean.getGender());
                        tvBirth.setText("出生年月: " + userInfoBean.getBirth());
                        tvCollage.setText("学院: " + userInfoBean.getCollege());
                        tvMajor.setText("专业: " + userInfoBean.getCollege());
                    }

                    @Override
                    public void onError(int code, String msg) {

                    }

                    @Override
                    public void onFail(Call call, IOException e) {

                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 退出登录，跳转登录页面
            case R.id.btn_logout:
                Configurator.getInstance().clearProfile();
                ExPreferences.clear();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.btn_changepwd: // 跳转修改密码页面
                Intent intent2 = new Intent(getActivity(), ChangePwdActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
}
