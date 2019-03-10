package com.example.learn.delegate.student;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.config.UrlConfig;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.delegate.student.paper.PaperAdapter;
import com.example.learn.entiry.response.RespListBean;
import com.example.learn.entiry.student.exam.LayoutBean;
import com.example.learn.learningonline.R;
import com.example.learn.net.version1.OKHttp;
import com.example.learn.net.version1.RequestCallback;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class PaperDelegate extends BaseDelegate implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    int subjectId;

    @BindView(R.id.rv_pager)
    RecyclerView mRecyclerView;

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @Override
    public Object getLayout() {
        return R.layout.delegate_paper;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        Bundle arguments = getArguments();
        subjectId = arguments.getInt("subjectId");
        mRefreshLayout.setOnRefreshListener(this);
        iv_back.setOnClickListener(this);
        onRefresh();
    }

    private void getPaperData() {
        final String userId = Configurator.getInstance().get(ConfigType.USERID);
        String url = UrlConfig.EXAM + "?sqlType=1&userId=" + userId + "" + "&subjectId=" + subjectId;
        Logger.d("getPaperData: " + url);
        OKHttp.getInstance()
                .get(url, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        Logger.json(responseBody);
                        RespListBean<LayoutBean> baseBean = JSON.parseObject(responseBody, new TypeReference<RespListBean<LayoutBean>>() {});
                        List<LayoutBean> layoutBean = baseBean.getData();
                        mRecyclerView.setAdapter(new PaperAdapter(layoutBean));
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

    @Override
    public void onRefresh() {
        getPaperData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                pop();
                break;


        }
    }
}
