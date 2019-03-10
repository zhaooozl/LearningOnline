package com.example.learn.delegate.student;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.config.UrlConfig;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.delegate.student.exam.ExamAdapter;
import com.example.learn.delegate.student.exam.ExamItemType;
import com.example.learn.entiry.response.RespListBean;
import com.example.learn.entiry.student.exam.LayoutBean;
import com.example.learn.entiry.student.exam.SubmitBean;
import com.example.learn.learningonline.R;
import com.example.learn.net.version1.OKHttp;
import com.example.learn.net.version1.RequestCallback;
import com.example.learn.view.LoadingDialog;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class ExamDelegate extends BaseDelegate implements OnClickListener,
        RadioGroup.OnCheckedChangeListener, TextWatcher {

    @BindView(R.id.rv_exam)
    RecyclerView mRecyclerView;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @Override
    public Object getLayout() {
        return R.layout.delegate_exam;
    }

    private ExamAdapter examAdapter;

    private List<SubmitBean> submitBeans = new ArrayList<>();
    private Map<Integer, SubmitBean> submitBeanMap = new HashMap<>();

    private String userId = Configurator.getInstance().get(ConfigType.USERID);

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
//        getExamData();
        Bundle arguments = getArguments();
        int subjectId = arguments.getInt("subjectId");
        iv_back.setOnClickListener(this);
        getExamData(subjectId);
    }

    /**
     * 请求试卷数据
     */
    private void getExamData(int subjectId) {
        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.show();
        String url = UrlConfig.EXAM + "?subjectId=" + subjectId;
        OKHttp.getInstance()
                .get(url, new RequestCallback() {
                    @Override
                    public void onSuccess(String responseBody) {
                        Logger.json(responseBody);
                        RespListBean<LayoutBean> baseBean = JSON.parseObject(responseBody, new TypeReference<RespListBean<LayoutBean>>() {
                        });
                        List<LayoutBean> layoutBean = baseBean.getData();
                        Logger.d("getExamData layoutBean: " + layoutBean.toString());
                        examAdapter = new ExamAdapter(ExamDelegate.this, layoutBean);
                        mRecyclerView.setAdapter(examAdapter);
                        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));
                        loadingDialog.cancel();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        loadingDialog.cancel();
                    }

                    @Override
                    public void onFail(Call call, IOException e) {
                        loadingDialog.cancel();
                    }
                });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                pop();
                break;
            case R.id.btn_submit_exam:
                String submitData = JSON.toJSONString(submitBeans);
                Logger.json(submitData);

                final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
                loadingDialog.show();

                OKHttp.getInstance()
                        .post(UrlConfig.SUBMITPAPER, submitData, new RequestCallback() {
                            @Override
                            public void onSuccess(String responseBody) {
                                Logger.d("responsebody: " + responseBody);
                                loadingDialog.cancel();
                                ExamDelegate.this.pop();
                            }

                            @Override
                            public void onError(int code, String msg) {
                                loadingDialog.cancel();
                            }

                            @Override
                            public void onFail(Call call, IOException e) {
                                loadingDialog.cancel();
                            }
                        });
                break;


        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        Logger.d("onCheckedChanged i: " + i);
        int viewType = (int) radioGroup.getTag(R.id.second_tag);

        switch (viewType) {
            case ExamItemType.CHIOCE:
                String questionId = (String) radioGroup.getTag(R.id.first_tag);
                int checkedId = radioGroup.getCheckedRadioButtonId();
                String answer = null;
                switch (checkedId) {
                    case R.id.rb_option0:
                        answer = "A";
                        break;
                    case R.id.rb_option1:
                        answer = "B";
                        break;
                    case R.id.rb_option2:
                        answer = "C";
                        break;
                    case R.id.rb_option3:
                        answer = "D";
                        break;
                    default:
                        answer = "";
                        break;
                }

                SubmitBean submitBean = new SubmitBean();
                submitBean.setUserId(userId);
                submitBean.setQuestionId(questionId);
                submitBean.setAnswer(answer);
//                submitBeans.add(submitBean);
                insert2List(submitBean);
                break;
            case ExamItemType.JUDGE:

                String questionId2 = (String) radioGroup.getTag(R.id.first_tag);
                Log.i("questionId: ", questionId2);
                int checkedId2 = radioGroup.getCheckedRadioButtonId();
                String answer2 = null;
                switch (checkedId2) {
                    case R.id.rb_option0:
                        answer2 = "对";
                        break;
                    case R.id.rb_option1:
                        answer2 = "错";
                        break;
                    default:
                        answer2 = "";
                        break;
                }

                SubmitBean submitBean2 = new SubmitBean();
                submitBean2.setUserId(userId);
                submitBean2.setQuestionId(questionId2 + "");
                submitBean2.setAnswer(answer2);
//                submitBeans.add(submitBean2);
                insert2List(submitBean2);

                break;
            default:
                break;
        }
    }

    private String getSubmitData() {
        submitBeans.clear();
//        int count = mRecyclerView.getChildCount();
        int count = examAdapter.getItemCount();
        Logger.i("getSubmitData count: " + count);
        for (int i = 0; i < count; i++) {
//            long itemId = examAdapter.getItemId(i);
            View child = mRecyclerView.getChildAt(i);
            int id = child.getId();
            switch (id) {
                case R.id.rl_item_choice:
                    RadioGroup rgOptions = child.findViewById(R.id.rg_options);
                    String questionId = (String) rgOptions.getTag(R.id.first_tag);
                    int checkedId = rgOptions.getCheckedRadioButtonId();
                    String answer = null;
                    switch (checkedId) {
                        case R.id.rb_option0:
                            answer = "A";
                            break;
                        case R.id.rb_option1:
                            answer = "B";
                            break;
                        case R.id.rb_option2:
                            answer = "C";
                            break;
                        case R.id.rb_option3:
                            answer = "D";
                            break;
                        default:
                            answer = "";
                            break;
                    }

                    SubmitBean submitBean = new SubmitBean();
                    submitBean.setUserId(userId);
                    submitBean.setQuestionId(questionId);
                    submitBean.setAnswer(answer);
                    submitBeans.add(submitBean);

                    break;
                case R.id.rl_item_judge:
                    RadioGroup rgOptions2 = child.findViewById(R.id.rg_options);
                    String questionId2 = (String) rgOptions2.getTag(R.id.first_tag);
                    Log.i("questionId: ", questionId2);
                    int checkedId2 = rgOptions2.getCheckedRadioButtonId();
                    String answer2 = null;
                    switch (checkedId2) {
                        case R.id.rb_option0:
                            answer2 = "对";
                            break;
                        case R.id.rb_option1:
                            answer2 = "错";
                            break;
                        default:
                            answer2 = "";
                            break;
                    }

                    SubmitBean submitBean2 = new SubmitBean();
                    submitBean2.setUserId(userId);
                    submitBean2.setQuestionId(questionId2 + "");
                    submitBean2.setAnswer(answer2);
                    submitBeans.add(submitBean2);

                    break;
                case R.id.rl_item_fill_blank:
                    EditText etAnswer = child.findViewById(R.id.et_answer);
                    String questionId3 = (String) etAnswer.getTag(R.id.first_tag);
                    String answer3 = etAnswer.getText().toString().trim();
                    Log.i("questionId: ", questionId3 + "");
                    Log.i("answer: ", answer3);
                    SubmitBean submitBean3 = new SubmitBean();
                    submitBean3.setUserId(userId);
                    submitBean3.setQuestionId(questionId3 + "");
                    submitBean3.setAnswer(answer3);
                    submitBeans.add(submitBean3);

                    break;
                default:

                    break;
            }
        }
        return JSON.toJSONString(submitBeans);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        View focusedChild = mRecyclerView.getFocusedChild();
        EditText etAnswer = focusedChild.findViewById(R.id.et_answer);
        String questionId = (String) etAnswer.getTag(R.id.first_tag);
        String answer = etAnswer.getText().toString().trim();
        Logger.d("onTextChanged answer: " + answer);
        SubmitBean submitBean = new SubmitBean();
        submitBean.setUserId(userId);
        submitBean.setQuestionId(questionId);
        submitBean.setAnswer(answer);
        insert2List(submitBean);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    private void insert2List(SubmitBean bean) {
        for (SubmitBean s : submitBeans) {
            if (s.getQuestionId().equals(bean.getQuestionId())) {
                submitBeans.remove(s);
                submitBeans.add(bean);
                return;
            }
        }
        submitBeans.add(bean);
    }
}
