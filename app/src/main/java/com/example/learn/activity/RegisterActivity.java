package com.example.learn.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    @BindView(R.id.et_userid)
    EditText etUserid;
    @BindView(R.id.et_username)
    EditText etUserName;
    @BindView(R.id.et_passwd)
    EditText etPasswd;
    @BindView(R.id.et_repasswd)
    EditText etRepasswd;
    @BindView(R.id.et_birth)
    EditText etBirth;
    @BindView(R.id.et_college)
    EditText etCollege;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.rg_gender)
    RadioGroup rgGender;
    @BindView(R.id.rb_boy)
    RadioButton rbBoy;
    @BindView(R.id.rb_girl)
    RadioButton rbGirl;
    @BindView(R.id.rg_user_type)
    RadioGroup rgUserType;
    @BindView(R.id.rb_student)
    RadioButton rbStudent;
    @BindView(R.id.rb_teacher)
    RadioButton rbTeacher;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    public int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void onBindView(Bundle savedInstanceState) {
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_register:


                register();
                break;
            default:
                break;
        }
    }


    private void register() {
        String userId = etUserid.getText().toString().trim();
        String userName = etUserName.getText().toString().trim();
        String passwd = etPasswd.getText().toString().trim();
        String rePasswd = etRepasswd.getText().toString().trim();
        String college = etCollege.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String birth = etBirth.getText().toString().trim();

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
