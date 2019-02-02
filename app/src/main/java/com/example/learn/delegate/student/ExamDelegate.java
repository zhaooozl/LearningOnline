package com.example.learn.delegate.student;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.learn.config.UrlConfig;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.delegate.student.exam.ExamAdapter;
import com.example.learn.entiry.response.RespListBean;
import com.example.learn.entiry.response.RespObjBean;
import com.example.learn.entiry.student.exam.ExamBean;
import com.example.learn.entiry.student.exam.LayoutBean;
import com.example.learn.entiry.student.exam.Question;
import com.example.learn.learningonline.R;
import com.example.learn.net.version1.OKHttp;
import com.example.learn.net.version1.RequestCallback;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class ExamDelegate extends BaseDelegate implements OnClickListener,
        RadioGroup.OnCheckedChangeListener, TextWatcher{

    @BindView(R.id.rv_exam)
    RecyclerView mRecyclerView;

    @Override
    public Object getLayout() {
        return R.layout.delegate_exam;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        getExamData();
    }

    /**
     * 请求试卷数据
     */
    private void getExamData() {
        OKHttp.getInstance()
                .get(UrlConfig.EXAM_INFO, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        Logger.json(responseBody);
                        RespListBean<LayoutBean> baseBean = JSON.parseObject(responseBody, new TypeReference<RespListBean<LayoutBean>>() {
                        });

                        List<LayoutBean> layoutBean = baseBean.getData();
                        Logger.d("getExamData layoutBean: " + layoutBean.toString());


                        mRecyclerView.setAdapter(new ExamAdapter(ExamDelegate.this,  layoutBean));
                        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));

                    }

                    @Override
                    public void onError(int code, String msg) {

                    }

                    @Override
                    public void onFail(Call call, IOException e) {

                    }
                });
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
