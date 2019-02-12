package com.example.learn.delegate.admin;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.learn.config.UrlConfig;
import com.example.learn.delegate.admin.content.UserInfoAdapter;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.entiry.admin.UserInfoBean;
import com.example.learn.entiry.common.CommonStatusBean;
import com.example.learn.entiry.response.RespListBean;
import com.example.learn.entiry.response.RespObjBean;
import com.example.learn.learningonline.R;
import com.example.learn.net.version1.OKHttp;
import com.example.learn.net.version1.RequestCallback;
import com.example.learn.view.LoadingDialog;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class EditUserInfoDelegate extends BaseDelegate implements View.OnClickListener {
    @BindView(R.id.tv_label1)
    TextView tvUserId;
    @BindView(R.id.et_edit1)
    EditText etUserId;
    @BindView(R.id.et_edit2)
    EditText etUserName;
    @BindView(R.id.et_edit3)
    EditText etPasswd;
    @BindView(R.id.et_edit4)
    EditText etBirth;
    @BindView(R.id.et_edit5)
    EditText etCollege;
    @BindView(R.id.et_edit6)
    EditText etEmail;
    @BindView(R.id.rg_gender)
    RadioGroup rgGender;
    @BindView(R.id.rg_user_type)
    RadioGroup rgUserType;
    @BindView(R.id.btn_submit_userinfo)
    Button btnSubmit;
    private LoadingDialog mLoadingDialog;

    private UserInfoBean userInfoBean;

    @Override
    public Object getLayout() {
        return R.layout.item_edituserinfo_edittext;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        btnSubmit.setOnClickListener(this);
        mLoadingDialog = new LoadingDialog(getActivity());
        Bundle arguments = getArguments();
        String userId = arguments.getString("userId");
        getStudentInfo(userId);
    }

    private void getStudentInfo(String userId) {
        mLoadingDialog.show();
        final String url = UrlConfig.USERINFO + "?operateType=query&userId=" + userId;
        OKHttp.getInstance()
                .get(url, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        Logger.json(responseBody);
                        RespListBean<UserInfoBean> respListBean = JSONObject.parseObject(responseBody, new TypeReference<RespListBean<UserInfoBean>>() {
                        });
                        List<UserInfoBean> userInfoBeans = respListBean.getData();
                        userInfoBean = userInfoBeans.get(0);
                        setUserInfoUI(userInfoBean);
                        mLoadingDialog.cancel();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        mLoadingDialog.cancel();
                    }

                    @Override
                    public void onFail(Call call, IOException e) {
                        mLoadingDialog.cancel();

                    }
                });
    }

    private void setUserInfoUI(UserInfoBean userInfoBean) {
        String userId = userInfoBean.getUserId();
        String userName = userInfoBean.getUserName();
        String passwd = userInfoBean.getPasswd();
        String birth = userInfoBean.getBirth();
        String college = userInfoBean.getCollege();
        String email = userInfoBean.getEmail();
        String gender = userInfoBean.getGender();
        String userType = userInfoBean.getUserType();

        if ("2".equals(userType)) {
            tvUserId.setText("学号：");
            rgUserType.check(R.id.rb_student);
        } else if ("1".equals(userType)) {
            tvUserId.setText("工号：");
            rgUserType.check(R.id.rb_teacher);
        }
        etUserId.setText(userId);
        etUserName.setText(userName);
        etPasswd.setText(passwd);
        etBirth.setText(birth);
        etCollege.setText(college);
        etEmail.setText(email);
        if ("男".equals(gender)) {
            rgGender.check(R.id.rb_boy);
        } else if ("女".equals(gender)) {
            rgGender.check(R.id.rb_girl);
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit_userinfo:
                mLoadingDialog.show();
                String userId = etUserId.getText().toString().trim();
                String userName = etUserName.getText().toString().trim();
                String passwd = etPasswd.getText().toString().trim();
                String birth = etBirth.getText().toString().trim();
                String college = etCollege.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String gender = "";
                int genderId = rgGender.getCheckedRadioButtonId();
                if (genderId == R.id.rb_boy) {
                    gender = "男";
                } else if (genderId == R.id.rb_girl) {
                    gender = "女";
                }
                String userType = "2";
                int userTypeId = rgUserType.getCheckedRadioButtonId();
                if (userTypeId == R.id.rb_student) {
                    userType = "2";
                } else if (userTypeId == R.id.rb_teacher) {
                    userType = "1";
                }

                String id = userInfoBean.getId();
                final String url = UrlConfig.USERINFO +
                        "?operateType=update" +
                        "&id=" + id +
                        "&userId=" + userId +
                        "&userName=" + userName +
                        "&passwd=" + passwd +
                        "&birth=" + birth +
                        "&college=" + college +
                        "&email=" + email +
                        "&gender=" + gender +
                        "&userType=" + userType;
                OKHttp.getInstance()
                        .get(url, new RequestCallback() {
                            @Override
                            public void onSuccess(String responseBody) {
                                Logger.json(responseBody);
                                RespObjBean<CommonStatusBean> respObjBean = JSONObject.parseObject(responseBody, new TypeReference<RespObjBean<CommonStatusBean>>() {});
                                CommonStatusBean data = respObjBean.getData();
                                if (data.getCode() == 0) {
                                    pop();
                                } else {
                                    Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                                    mLoadingDialog.cancel();
                                }
                                mLoadingDialog.cancel();
                            }

                            @Override
                            public void onError(int code, String msg) {
                                mLoadingDialog.cancel();
                            }

                            @Override
                            public void onFail(Call call, IOException e) {
                                mLoadingDialog.cancel();

                            }
                        });


                break;
            default:

                break;


        }
    }
}
