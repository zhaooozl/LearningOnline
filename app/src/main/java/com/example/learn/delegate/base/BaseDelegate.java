package com.example.learn.delegate.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseDelegate extends Fragment {

    private static final String TAG = "BaseDelegate";

    private Unbinder mUnbinder = null;

    /**
     * delegate的布局
     * @return
     */
    public abstract Object getLayout();

    /**
     * 子类重写该方法，可以用来初始化UI等操作
     * @param savedInstanceState
     * @param view
     */
    public abstract void onBindView(Bundle savedInstanceState, View view);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        Object layout = getLayout();
        if (layout instanceof Integer) {
            rootView = inflater.inflate((Integer) layout, container, false);
        } else if (layout instanceof View) {
            rootView = (View) layout;
        } else {
            throw new RuntimeException("Unknow view type");
        }

        // 绑定黄油刀
        mUnbinder = ButterKnife.bind(this, rootView);
        onBindView(savedInstanceState, rootView);
        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Delegate销毁时，释放黄油刀
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    public void start(int resId, Fragment fragment) {
        Logger.i(TAG + " start");
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        List<Fragment> fragments = fm.getFragments();
        boolean isExist = false;
        for (Fragment f : fragments) {
//            Logger.d("fragment name: " + f.getTag() + " \nfragment id: " + f.getUserId());
            if (f.isVisible()) {
                ft.hide(f);
            }
            if (fragment.getClass().getName().equals(f.getTag())) {
//                ft.show(f);
                isExist = true;
            }

        }
        Logger.d(TAG + "start isExist: " + isExist);
        if (isExist) {
            ft.show(fragment);
        } else {
            ft.add(resId, fragment, fragment.getClass().getName());
        }
        ft.commit();
    }

    public void pop() {
        FragmentManager fm = getFragmentManager();
//        List<Fragment> fragments = fm.getFragments();

        boolean isPop = fm.popBackStackImmediate();
        Logger.d("isPop: " + isPop);
//        for (Fragment f:
//             fragments) {
//            Logger.d("pop: " + f.getTag() + " \nfragment id: " + f.getUserId());
//        }

//        FragmentManager fm2 = getChildFragmentManager();
//        List<Fragment> fragments2 = fm2.getFragments();
//        for (Fragment f :
//                fragments2) {
//
//            Logger.d("pop2: " + f.getTag() + " \nfragment id: " + f.getUserId());
//
//        }

    }

}
