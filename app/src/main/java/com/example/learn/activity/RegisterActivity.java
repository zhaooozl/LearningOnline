package com.example.learn.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.learn.activity.base.BaseActivity;
import com.example.learn.config.UrlConfig;
import com.example.learn.learningonline.R;
import com.example.learn.net.version1.OKHttp;
import com.example.learn.net.version1.RequestCallback;
import com.example.learn.view.LoadingDialog;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    // 用户id
    @BindView(R.id.et_userid)
    EditText etUserid;
    // 昵称
    @BindView(R.id.et_username)
    EditText etUserName;
    // 密码
    @BindView(R.id.et_passwd)
    EditText etPasswd;
    // 再输入一次密码
    @BindView(R.id.et_repasswd)
    EditText etRepasswd;
    // 出生年月
    @BindView(R.id.et_birth)
    EditText etBirth;
    // 学院
    @BindView(R.id.et_college)
    EditText etCollege;
    // 邮箱
    @BindView(R.id.et_email)
    EditText etEmail;
    // 性别
    @BindView(R.id.rg_gender)
    RadioGroup rgGender;
    // 男生button
    @BindView(R.id.rb_boy)
    RadioButton rbBoy;
    // 女生button
    @BindView(R.id.rb_girl)
    RadioButton rbGirl;
    // 用户类别
    @BindView(R.id.rg_user_type)
    RadioGroup rgUserType;
    // 学生button
    @BindView(R.id.rb_student)
    RadioButton rbStudent;
    // 教师button
    @BindView(R.id.rb_teacher)
    RadioButton rbTeacher;
    // 注册button
    @BindView(R.id.btn_register)
    Button btnRegister;
    // 返回button
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @Override
    public int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void onBindView(Bundle savedInstanceState) {
        // 设置注册button点击响应事件
        btnRegister.setOnClickListener(this);
        // 返回button监听
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_register: //注册点击，执行注册请求
                register();
                break;

            case R.id.iv_back: //返回
                finish();
                break;
            default:
                break;
        }
    }


    private void register() {
        final String userId = etUserid.getText().toString().trim();
        final String userName = etUserName.getText().toString().trim();
        final String passwd = etPasswd.getText().toString().trim();
        final String rePasswd = etRepasswd.getText().toString().trim();
        final String college = etCollege.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String birth = etBirth.getText().toString().trim();

        int userType = -1;
        switch (rgUserType.getCheckedRadioButtonId()) {
            case R.id.rb_student:
                userType = 2;
                break;
            case R.id.rb_teacher:
                userType = 1;
                break;
            default:
                break;
        }

        String gender = null;
        switch (rgGender.getCheckedRadioButtonId()) {
            case R.id.rb_boy:
                gender = "男";
                break;

            case R.id.rb_girl:
                gender = "女";
                break;

            default:
                gender = "保密";
                break;
        }

        if ("".equals(userId)) {
            Toast.makeText(this, "请输入学号（工号）！", Toast.LENGTH_SHORT).show();
            return;
        }

        if ("".equals(passwd) || "".equals(rePasswd) || !passwd.equals(rePasswd)) {
            Toast.makeText(this, "请确认密码！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userType != 2 && userType != 1) {
            Toast.makeText(this, "请选择学生或者教师!", Toast.LENGTH_SHORT).show();
            return;
        }

        final LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.show();

        String url = UrlConfig.REGISTER + "?userId=" + userId +
                "&userName=" + userName +
                "&passwd=" + passwd +
                "&userType=" + userType +
                "&gender=" + gender +
                "&birth=" + birth +
                "&college=" + college +
                "&email=" + email;
        OKHttp.getInstance()
                .get(url, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        loadingDialog.cancel();
                        finish();
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

    }
}
