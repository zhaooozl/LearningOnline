package com.example.learn.delegate.teacher;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.learn.activity.MainActivity;
import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.config.UrlConfig;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.delegate.bottom.UserType;
import com.example.learn.delegate.student.LearnDelegate;
import com.example.learn.delegate.student.content.ContentAdapter;
import com.example.learn.entiry.CourseBean;
import com.example.learn.entiry.CourseResponseBean;
import com.example.learn.learningonline.R;
import com.example.learn.net.version1.OKHttp;
import com.example.learn.net.version1.RequestCallback;
import com.example.learn.net.version2.ExOKHttp;
import com.example.learn.net.version2.callback.ISuccess;
import com.joanzapata.iconify.widget.IconButton;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class SubjectDelegate extends BaseDelegate implements View.OnClickListener {

    @BindView(R.id.ib_add_subject)
    IconButton ibAddSubject;
    @BindView(R.id.rv_subject)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private String mUserId = Configurator.getInstance().get(ConfigType.USERID);
    private int mUserType = Configurator.getInstance().get(ConfigType.USER_TYPE);

    @Override
    public Object getLayout() {
        return R.layout.delegate_subject;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        ibAddSubject.setOnClickListener(this);
        getCoursesData(mUserId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_add_subject:
                MainActivity activity = (MainActivity) getActivity();
                activity.start(new UploadDelegate());
                break;

            default:

                break;


        }
    }

    // 获取课程数据
    private void getCoursesData(String userId) {
        mRefreshLayout.setRefreshing(true);
        String url = null;
        if (mUserType == UserType.ADMIN) {
            url = UrlConfig.SUBJECT + "?operateType=query";
        } else {
            url = UrlConfig.SUBJECT + "?operateType=query&userId=" + userId;
        }

        OKHttp.getInstance()
                .get(url, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        Logger.json(responseBody);
                        CourseResponseBean courseResponseBean = JSON.parseObject(responseBody, new TypeReference<CourseResponseBean>() {});
                        List<CourseBean> courseBeans = courseResponseBean.getData();
                        mRecyclerView.setAdapter(new ContentAdapter(SubjectDelegate.this, courseBeans));
                        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFail(Call call, IOException e) {
                        mRefreshLayout.setRefreshing(false);
                    }
                });
    }
}
