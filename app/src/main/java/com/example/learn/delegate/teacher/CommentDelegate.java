package com.example.learn.delegate.teacher;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.config.UrlConfig;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.delegate.teacher.adapter.CommentAdapter;
import com.example.learn.entiry.response.RespListBean;
import com.example.learn.entiry.teacher.CommentBean;
import com.example.learn.learningonline.R;
import com.example.learn.net.version1.OKHttp;
import com.example.learn.net.version1.RequestCallback;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;

public class CommentDelegate extends BaseDelegate implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srl_refresh;

    @BindView(R.id.rv_comment)
    RecyclerView rv_comment;

    @Override
    public Object getLayout() {
        return R.layout.delegate_comment;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        srl_refresh.setOnRefreshListener(this);
        this.onRefresh();
    }


    private void getComment() {
        final String userId = Configurator.getInstance().get(ConfigType.USERID);
        final String url = UrlConfig.COMMENT + "?operateType=query&teacherId=" + userId;
        OKHttp.getInstance()
                .get(url, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        Logger.d(responseBody);
                        RespListBean<CommentBean> respListBean = JSON.parseObject(responseBody, new TypeReference<RespListBean<CommentBean>>() {});
                        rv_comment.setAdapter(new CommentAdapter(respListBean.getData()));
                        rv_comment.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));
                        srl_refresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        srl_refresh.setRefreshing(false);
                    }

                    @Override
                    public void onFail(Call call, IOException e) {
                        srl_refresh.setRefreshing(false);
                    }
                });


    }

    @Override
    public void onRefresh() {
        srl_refresh.setRefreshing(true);
        getComment();
    }
}
