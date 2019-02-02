package com.example.learn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.example.learn.activity.base.BaseActivity;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.delegate.bottom.BaseBottomDelegate;
import com.example.learn.learningonline.R;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    // 首页BottomDelegate的容器
    @BindView(R.id.fl_container)
    FrameLayout mContainer;

    private int userType;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBindView(Bundle savedInstanceState) {

        Intent intent = getIntent();
        userType = intent.getIntExtra("userType", 0);
        Logger.d("onBindView userType: " + userType);
        BaseBottomDelegate delegate = new BaseBottomDelegate();
        Bundle bundle = new Bundle();
        bundle.putInt("userType", userType);
        delegate.setArguments(bundle);

        startWithPop(R.id.fl_container, delegate);
    }

    public void start(BaseDelegate delegate) {
        super.startWithBack(R.id.fl_container, delegate);
    }
}
