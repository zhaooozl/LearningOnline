package com.example.learn.activity.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.learningonline.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    private long ltm = 0;

    private Unbinder mBind;

    public abstract int getLayout();

    public abstract void onBindView(Bundle savedInstanceState);

    public static final List<AppCompatActivity> mActivitys = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mActivitys.add(this);

        mBind = ButterKnife.bind(this);

        registerPermission();

        onBindView(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivitys.remove(this);
        if (mBind != null) {
            mBind.unbind();
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            long ctm = System.currentTimeMillis();
//            if (ctm - ltm > 2000) {
//                Logger.d("ctm: " + ctm);
//                Toast.makeText(this, "再按一次退出！", Toast.LENGTH_SHORT).show();
//                ltm = ctm;
//            } else {
//                Logger.d("finish: " + ctm);
//                System.exit(0);
//            }
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//
//    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Logger.i(TAG + " onBackPressed");
        if (mActivitys.size() > 1) {
            mActivitys.get(mActivitys.size() - 1).finish();
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        final boolean isStateSaved = fragmentManager.isStateSaved();
        Logger.d("isStateSaved: " + isStateSaved);

        if (isStateSaved && Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
            // Older versions will throw an exception from the framework
            // FragmentManager.popBackStackImmediate(), so we'll just
            // return here. The Activity is likely already on its way out
            // since the fragmentManager has already been saved.
            return;
        }

        if (isStateSaved || !fragmentManager.popBackStackImmediate()) {
            long ctm = System.currentTimeMillis();
            if (ctm - ltm > 2000) {
                Logger.d("ctm: " + ctm);
                Toast.makeText(this, "再按一次退出！", Toast.LENGTH_SHORT).show();
                ltm = ctm;

            } else {
                Logger.d("finish: " + ctm);
                super.onBackPressed();

                System.exit(0);
            }
        }
    }

    public void startWithPop(int containerId, BaseDelegate delegate) {
//        Log.d("start with pop=====", "");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(containerId, delegate, delegate.getClass().getName());
        ft.commit();
    }

    public void startWithBack(int containerId, BaseDelegate delegate) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        List<Fragment> fragments = fm.getFragments();
        boolean isExist = false;
        for (Fragment f : fragments) {
            ft.hide(f);
            if (delegate.getClass().getName().equals(f.getTag())) {
                isExist = true;
            } else {
                isExist = false;
            }
            if (f.isVisible()) {
                ft.addToBackStack(f.getTag());
            }
        }
        Logger.i( " startWithBack isExist: " + isExist);

        if (isExist) {
            ft.show(delegate);
        } else {
            ft.add(containerId, delegate, delegate.getClass().getName());
        }
        ft.commit();
    }

    private void registerPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
}
