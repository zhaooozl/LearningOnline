package com.example.learn.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learn.config.UrlConfig;
import com.example.learn.learningonline.R;
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionDialog extends Dialog {
    // 取消button
    @BindView(R.id.btn_cancel)
    public Button btn_cancel;
    // 确认button
    @BindView(R.id.btn_confirm)
    public Button btn_confirm;
    // 问题
    @BindView(R.id.et_question)
    public EditText et_question;
    // 选项
    @BindView(R.id.et_optiona)
    public EditText et_optiona;
    @BindView(R.id.et_optionb)
    public EditText et_optionb;
    @BindView(R.id.et_optionc)
    public EditText et_optionc;
    @BindView(R.id.et_optiond)
    public EditText et_optiond;
    // 分数
    @BindView(R.id.et_score)
    public EditText et_score;
    // 标题
    @BindView(R.id.tv_title)
    public TextView tv_title;
    // 标准答案
    @BindView(R.id.et_answer)
    public EditText et_answer;

    private String examPaperId;
    private int questionType;
    private int subjectId;

    private int mShowType;


    private View.OnClickListener onClickListener;

    public QuestionDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_question_select);
        ButterKnife.bind(this);

    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        btn_cancel.setOnClickListener(onClickListener);
        btn_confirm.setOnClickListener(onClickListener);
    }

    @Override
    public void show() {
        if (questionType == 3) {           // 选择题
            et_optiona.setVisibility(View.VISIBLE);
            et_optionb.setVisibility(View.VISIBLE);
            et_optionc.setVisibility(View.VISIBLE);
            et_optiond.setVisibility(View.VISIBLE);
//            tv_title.setText("添加选择题");
            et_answer.setHint("答案：（A B C D）");
        } else if (questionType == 4) {    // 判断题
            et_optiona.setVisibility(View.GONE);
            et_optionb.setVisibility(View.GONE);
            et_optionc.setVisibility(View.GONE);
            et_optiond.setVisibility(View.GONE);
//            tv_title.setText("添加判断题");
            et_answer.setHint("答案：（对 错）");
        } else if (questionType == 5) {    // 填空题
            et_optiona.setVisibility(View.GONE);
            et_optionb.setVisibility(View.GONE);
            et_optionc.setVisibility(View.GONE);
            et_optiond.setVisibility(View.GONE);
//            tv_title.setText("添加填空题");
            et_answer.setHint("答案");
        }
        super.show();
    }

    public void setExamPaperId(String examPaperId) {
        this.examPaperId = examPaperId;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getmShowType() {
        return mShowType;
    }

    public void setmShowType(int mShowType) {
        this.mShowType = mShowType;
    }

    public void clear() {
        tv_title.setText("");
        et_question.setText("");
        et_answer.setText("");
        et_score.setText("");
        et_optiona.setText("");
        et_optionb.setText("");
        et_optionc.setText("");
        et_optiond.setText("");
    }

    public String getParams() throws UnsupportedEncodingException {
        final String score = et_score.getText().toString().trim();
        final String question = et_question.getText().toString().trim();
        final String standardanswer = et_answer.getText().toString().trim();
        String optiona = "";
        String optionb = "";
        String optionc = "";
        String optiond = "";
        if (questionType == 3) {  // 选择题
            optiona = et_optiona.getText().toString().trim();
            optionb = et_optionb.getText().toString().trim();
            optionc = et_optionc.getText().toString().trim();
            optiond = et_optiond.getText().toString().trim();
        } else if (questionType == 4) {  // 判断题
            optiona = "对";
            optionb = "错";
        } else if (questionType == 5) {  // 填空题

        }

        final String eQuestion = URLEncoder.encode(question, "utf-8");
        final String eOptiona = URLEncoder.encode(optiona, "utf-8");
        final String eOptionb = URLEncoder.encode(optionb, "utf-8");
        final String eOptionc = URLEncoder.encode(optionc, "utf-8");
        final String eOptiond = URLEncoder.encode(optiond, "utf-8");

        final String params = "&questionType=" + questionType + "&score=" + score +
                "&question=" + eQuestion +
                "&standardanswer=" + standardanswer +
                "&optiona=" + eOptiona +
                "&optionb=" + eOptionb +
                "&optionc=" + eOptionc +
                "&optiond=" + eOptiond;

        return params;
    }

    public boolean isCompleted() {
        final String score = et_score.getText().toString().trim();
        final String question = et_question.getText().toString().trim();
        final String answer = et_answer.getText().toString().trim();
        final String optiona = et_optiona.getText().toString().trim();
        final String optionb = et_optionb.getText().toString().trim();
        final String optionc = et_optionc.getText().toString().trim();
        final String optiond = et_optiond.getText().toString().trim();
        boolean isCompleted = true;
        if ("".equals(question)) {
            Toast.makeText(getOwnerActivity(), "请填写问题", Toast.LENGTH_SHORT).show();
            isCompleted = false;
        }

        if ("".equals(score)) {
            Toast.makeText(getOwnerActivity(), "请填写分数", Toast.LENGTH_SHORT).show();
            isCompleted = false;
        }

        if ("".equals(answer)) {
            Toast.makeText(getOwnerActivity(), "请填写答案", Toast.LENGTH_SHORT).show();
            isCompleted = false;
        }

        if (questionType == 3) {
            if ("".equals(optiona) || "".equals(optionb) || "".equals(optionc) || "".equals(optiona)) {
                Toast.makeText(getOwnerActivity(), "请填写选项", Toast.LENGTH_SHORT).show();
                isCompleted = false;
            }
        }
        return isCompleted;
    }
}
