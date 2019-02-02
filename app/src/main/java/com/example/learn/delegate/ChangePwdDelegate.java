package com.example.learn.delegate;

import android.os.Bundle;
import android.view.View;

import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.learningonline.R;

public class ChangePwdDelegate extends BaseDelegate {

    @Override
    public Object getLayout() {
        return R.layout.delegate_change_pwd;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {

    }
}
