package com.example.learn.delegate.admin;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.learn.config.UrlConfig;
import com.example.learn.delegate.admin.content.CourAdapter;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.entiry.admin.CoursewareBean;
import com.example.learn.entiry.common.CommonStatusBean;
import com.example.learn.entiry.response.RespListBean;
import com.example.learn.entiry.response.RespObjBean;
import com.example.learn.learningonline.R;
import com.example.learn.net.version1.OKHttp;
import com.example.learn.net.version1.RequestCallback;
import com.example.learn.view.LoadingDialog;
import com.joanzapata.iconify.widget.IconTextView;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class CourManagerDelegate extends BaseDelegate implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    // 课程列表
    @BindView(R.id.rv_courseware)
    RecyclerView mRecyclerView;

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private LoadingDialog mLoadingDialog;

    CourAdapter courAdapter;
    List<CoursewareBean> coursewareBeans;

    @Override
    public Object getLayout() {
        return R.layout.delegate_courseware_manager;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        mRefreshLayout.setOnRefreshListener(this);
        mLoadingDialog = new LoadingDialog(getActivity());
        mRefreshLayout.setRefreshing(true);
        getCourseware();
    }

    private void getCourseware() {
        OKHttp.getInstance()
                .get(UrlConfig.COURSEWARE, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        Logger.json(responseBody);
                        RespListBean<CoursewareBean> respListBean = JSONObject.parseObject(responseBody, new TypeReference<RespListBean<CoursewareBean>>() {
                        });
                        coursewareBeans = respListBean.getData();
                        courAdapter = new CourAdapter(coursewareBeans, CourManagerDelegate.this);
                        mRecyclerView.setAdapter(courAdapter);
                        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));

                        mLoadingDialog.cancel();
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        mLoadingDialog.cancel();
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFail(Call call, IOException e) {
                        mLoadingDialog.cancel();
                        mRefreshLayout.setRefreshing(false);
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_delete:
                int position = (int) v.getTag(R.id.first_tag);
                String subjectId = (String) v.getTag(R.id.second_tag);
                deleteCourserware(subjectId);
                break;
            default:

                break;
        }
    }

    private void deleteCourserware(String subjectId) {
        mLoadingDialog.show();
        final String url = UrlConfig.DELETE_COURSEWARE + "?subjectId=" + subjectId;
        OKHttp.getInstance()
                .get(url, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        Logger.json(responseBody);
                        RespObjBean<CommonStatusBean> respObjBean = JSONObject.parseObject(responseBody, new TypeReference<RespObjBean<CommonStatusBean>>() {});

                        CommonStatusBean data = respObjBean.getData();
                        if (data.getCode() == 0) {
                            getCourseware();
                        } else {
                            Toast.makeText(getActivity(), data.getMsg(), Toast.LENGTH_SHORT).show();
                            mLoadingDialog.cancel();
                        }

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


    @Override
    public void onRefresh() {
        Logger.d("onRefresh: ");
        getCourseware();
    }
}
