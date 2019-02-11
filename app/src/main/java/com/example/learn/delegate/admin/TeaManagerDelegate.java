package com.example.learn.delegate.admin;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.learn.activity.MainActivity;
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

public class TeaManagerDelegate extends BaseDelegate implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    // 用户信息列表
    @BindView(R.id.rv_userinfo)
    RecyclerView mRecyclerView;

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mRefreshLayout;

    // 添加教师
    @BindView(R.id.ib_add_user)
    ImageView ivAdd;

    private LoadingDialog mLoadingDialog;

    private String mUserType = "1";

    @Override
    public Object getLayout() {
        return R.layout.delegate_student_manager;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        mLoadingDialog = new LoadingDialog(getActivity());
        ivAdd.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setRefreshing(true);

//        Bundle arguments = getArguments();
//        mUserType = arguments.getString("userType");
        getStudentInfo(mUserType);
    }

    private void getStudentInfo(String userType) {
        final String url = UrlConfig.USERINFO + "?operateType=q&userType=" + userType;
        OKHttp.getInstance()
                .get(url, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        Logger.json(responseBody);
                        RespListBean<UserInfoBean> respListBean = JSONObject.parseObject(responseBody, new TypeReference<RespListBean<UserInfoBean>>() {
                        });
                        List<UserInfoBean> userInfoBeans = respListBean.getData();
                        UserInfoAdapter userInfoAdapter = new UserInfoAdapter(userInfoBeans, TeaManagerDelegate.this);
                        mRecyclerView.setAdapter(userInfoAdapter);
                        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));
                        mRefreshLayout.setRefreshing(false);
                        mLoadingDialog.cancel();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        mRefreshLayout.setRefreshing(false);
                        mLoadingDialog.cancel();
                    }

                    @Override
                    public void onFail(Call call, IOException e) {
                        mRefreshLayout.setRefreshing(false);
                        mLoadingDialog.cancel();

                    }
                });
    }

    @Override
    public void onRefresh() {
        Logger.d("onRefresh: ");
        getStudentInfo(mUserType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_change:
                String userId = (String) v.getTag();
                EditUserInfoDelegate delegate = new EditUserInfoDelegate();
                Bundle bundle = new Bundle();
                bundle.putString("userId", userId);
                delegate.setArguments(bundle);
                MainActivity activity = (MainActivity) getActivity();
                activity.start(delegate);
                break;
            case R.id.iv_delete:
                String userId2 = (String) v.getTag();
                deleteUser(userId2);
                break;
            case R.id.ib_add_user:
                break;
            default:
                break;


        }
    }


    private void deleteUser(String userId) {
        mLoadingDialog.show();
        final String url = UrlConfig.USERINFO + "?operateType=d&userId=" + userId;
        OKHttp.getInstance()
                .get(url, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        Logger.json(responseBody);
                        RespObjBean<CommonStatusBean> respObjBean = JSONObject.parseObject(responseBody, new TypeReference<RespObjBean<CommonStatusBean>>() {});
                        CommonStatusBean data = respObjBean.getData();
                        if (data.getCode() == 0) {
                            getStudentInfo(mUserType);
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
}
