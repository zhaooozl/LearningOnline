package com.example.learn.delegate.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.learn.activity.LoginActivity;
import com.example.learn.config.UrlConfig;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.entiry.response.RespObjBean;
import com.example.learn.entiry.student.Student;
import com.example.learn.learningonline.R;
import com.example.learn.net.version2.ExOKHttp;
import com.example.learn.net.version2.callback.IError;
import com.example.learn.net.version2.callback.IFailure;
import com.example.learn.net.version2.callback.ISuccess;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

public class MyDelegate extends BaseDelegate {
    @BindView(R.id.iv_profile_pic)
    ImageView ivProfilePic;
    @BindView(R.id.tv_student_id)
    TextView tvId;
    @BindView(R.id.tv_username)
    TextView tvName;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.tv_collage)
    TextView tvCollage;
    @BindView(R.id.tv_major)
    TextView tvMajor;
    // 修改密码button
    @BindView(R.id.btn_changepwd)
    TextView tvChangePwd;
    // 退出登录button
    @BindView(R.id.btn_logout)
    TextView tvLogout;


    String url2 = "http://aikanvod.miguvideo.com/video/p/pg/100041/miguaikan_v300/common.wdml";

    @Override
    public Object getLayout() {
        return R.layout.delegate_my;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        init();
        getMyData();
    }

    private void init() {
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        tvChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ExOKHttp.Builder()
                        .url(url2)
                        .success(new ISuccess() {
                            @Override
                            public void onSuccess(String responseJson) {
                                Logger.d("responseJson: " + responseJson);
                            }
                        })
                        .failure(new IFailure() {
                            @Override
                            public void onFailure() {
                                Logger.d("onFailure: ");

                            }
                        })
                        .error(new IError() {
                            @Override
                            public void onError() {
                                Logger.d("onError: ");

                            }
                        })
                        .build()
                        .get();
            }
        });

    }

    private void getMyData() {
        new ExOKHttp.Builder()
                .url(UrlConfig.STUDENT_INFO)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String responseJson) {
                        RespObjBean<Student> baseBean = JSON.parseObject(responseJson, new TypeReference<RespObjBean<Student>>() {});
                        Student student = baseBean.getData();

//                        ivProfilePic.setImageResource();
                        tvId.setText("学号: " + student.getId());
                        tvName.setText(student.getName());
                        tvGender.setText("性别: " + student.getGender());
                        tvBirth.setText("出生年月: " + student.getBirth());
                        tvCollage.setText("学院: " + student.getCollege());
                        tvMajor.setText("专业: " + student.getMajor());

                    }
                })
                .build()
                .get();

    }
}
