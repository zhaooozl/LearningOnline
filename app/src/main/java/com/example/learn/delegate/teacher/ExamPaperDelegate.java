package com.example.learn.delegate.teacher;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.learn.config.UrlConfig;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.delegate.teacher.adapter.ExamAdapter2;
import com.example.learn.entiry.common.CommonStatusBean;
import com.example.learn.entiry.response.RespListBean;
import com.example.learn.entiry.response.RespObjBean;
import com.example.learn.entiry.student.exam.LayoutBean;
import com.example.learn.learningonline.R;
import com.example.learn.net.version1.OKHttp;
import com.example.learn.net.version1.RequestCallback;
import com.example.learn.view.LoadingDialog;
import com.example.learn.view.QuestionDialog;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class ExamPaperDelegate extends BaseDelegate implements View.OnClickListener {

    @BindView(R.id.tv_add_select)
    TextView tvAddSelect;
    @BindView(R.id.tv_add_judge)
    TextView tvAddJudge;
    @BindView(R.id.tv_add_fill)
    TextView tvAddfill;
    @BindView(R.id.rv_exampaper)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_add)
    ImageView iv_add;

    private QuestionDialog mQuestionDialog;

    private LoadingDialog mLoadingDialog;

    private ExamAdapter2 examAdapter2;

    private List<LayoutBean> mLayoutBeans;

    private int questionType;
    private int subjectId;
    private String mQuestionId;

    @Override
    public Object getLayout() {
        return R.layout.delegate_exam_paper;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        Bundle arguments = getArguments();
        subjectId = arguments.getInt("subjectId");
        iv_back.setOnClickListener(this);
        iv_add.setOnClickListener(this);

        tvAddSelect.setOnClickListener(this);
        tvAddJudge.setOnClickListener(this);
        tvAddfill.setOnClickListener(this);
        mLoadingDialog = new LoadingDialog(getActivity());
        mQuestionDialog = new QuestionDialog(getActivity());
        mQuestionDialog.setOnClickListener(this);
//        mQuestionDialog.setExamPaperId("1");
        mQuestionDialog.setSubjectId(subjectId);
        getExamPaper(subjectId);

    }

    private void getExamPaper(int subjectId) {
        String url = UrlConfig.EXAM + "?subjectId=" + subjectId;
        OKHttp.getInstance()
                .get(url, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        Logger.json(responseBody);
                        RespListBean<LayoutBean> baseBean = JSON.parseObject(responseBody, new TypeReference<RespListBean<LayoutBean>>() {
                        });
                        mLayoutBeans = baseBean.getData();
//                        Logger.d("getExamData layoutBean: " + mLayoutBeans.toString());
                        examAdapter2 = new ExamAdapter2(ExamPaperDelegate.this, mLayoutBeans);
                        mRecyclerView.setAdapter(examAdapter2);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:

                break;
            case R.id.iv_add:

                break;
            case R.id.tv_add_select:
                questionType = 3;
                mQuestionDialog.setQuestionType(questionType);
                mQuestionDialog.setmShowType(1);
                mQuestionDialog.show();
                break;
            case R.id.tv_add_judge:
                questionType = 4;
                mQuestionDialog.setQuestionType(questionType);
                mQuestionDialog.setmShowType(1);
                mQuestionDialog.show();

                break;
            case R.id.tv_add_fill:
                questionType = 5;
                mQuestionDialog.setQuestionType(questionType);
                mQuestionDialog.setmShowType(1);
                mQuestionDialog.show();

                break;
            case R.id.btn_cancel:
                mQuestionDialog.cancel();
                break;
            case R.id.btn_confirm:
                int type = mQuestionDialog.getmShowType();
                if (type == 1) {
                    addQuestion();
                } else if (type == 2){
                    updateQuestion(mQuestionId);
                }
                break;
            case R.id.tv_delete:
                Logger.d("tv_delete");
                int position = (int) v.getTag();
                LayoutBean layoutBean = mLayoutBeans.get(position);
                String questionId = layoutBean.getQuestionId();
                deleteQuestion(questionId);
                break;
            case R.id.tv_update:
                Logger.d("tv_update");
                int position2 = (int) v.getTag();
                LayoutBean layoutBean2 = mLayoutBeans.get(position2);
                mQuestionDialog.clear();
                mQuestionDialog.setmShowType(2);
                mQuestionDialog.setQuestionType(layoutBean2.getLayouttype());
                mQuestionDialog.et_question.setText(layoutBean2.getQuestion());
                mQuestionDialog.et_score.setText(layoutBean2.getScore());
                mQuestionId = layoutBean2.getQuestionId();
                if (layoutBean2.getLayouttype() == 3) {
                    mQuestionDialog.et_optiona.setText(layoutBean2.getOptions().get(0).getValue());
                    mQuestionDialog.et_optionb.setText(layoutBean2.getOptions().get(1).getValue());
                    mQuestionDialog.et_optionc.setText(layoutBean2.getOptions().get(2).getValue());
                    mQuestionDialog.et_optiond.setText(layoutBean2.getOptions().get(3).getValue());
                } else if (layoutBean2.getLayouttype() == 4) {
                    mQuestionDialog.et_optiona.setText(layoutBean2.getOptions().get(0).getValue());
                    mQuestionDialog.et_optionb.setText(layoutBean2.getOptions().get(1).getValue());
                }
                mQuestionDialog.show();
                break;
            default:

                break;

        }

    }

    private void addQuestion() {
        mLoadingDialog.show();
        String url = UrlConfig.QUESTION + "?operateType=insert" +
                "&subjectId=" + subjectId + mQuestionDialog.getParams();
        OKHttp.getInstance()
                .get(url, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        mQuestionDialog.cancel();
                        mLoadingDialog.cancel();
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

    private void deleteQuestion(String questionId) {
        mLoadingDialog.show();
        final String url = UrlConfig.QUESTION + "?operateType=delete" +
                "&questionId=" + questionId;

        OKHttp.getInstance()
                .get(url, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
//                        RespObjBean<CommonStatusBean> respObjBean = JSON.parseObject(responseBody, new TypeReference<RespObjBean<CommonStatusBean>>() {
//                        });
//                        CommonStatusBean statusBean = respObjBean.getData();
//                        int code = statusBean.getCode();
//                        String msg = statusBean.getMsg();
//                        if (code == 0) {
//
//                        } else {
//
//                        }
//                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        mLoadingDialog.cancel();
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

    private void updateQuestion(String questionId) {
        mLoadingDialog.show();
        final String url = UrlConfig.QUESTION + "?operateType=update" +
                "&questionId=" + questionId + mQuestionDialog.getParams();

        OKHttp.getInstance()
                .get(url, new RequestCallback() {
            @Override
            public void onSuccess(String responseBody) {
//                RespObjBean<CommonStatusBean> respObjBean = JSON.parseObject(responseBody, new TypeReference<RespObjBean<CommonStatusBean>>() {
//                });
//                CommonStatusBean statusBean = respObjBean.getData();
//                int code = statusBean.getCode();
//                String msg = statusBean.getMsg();
//                if (code == 0) {
//
//                } else {
//
//                }
//                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                mLoadingDialog.cancel();
                mQuestionDialog.cancel();
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
