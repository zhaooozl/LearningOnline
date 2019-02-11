package com.example.learn.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.learn.config.UrlConfig;
import com.example.learn.learningonline.R;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionDialog extends Dialog {

    @BindView(R.id.btn_cancel)
    public TextView btn_cancel;
    @BindView(R.id.btn_confirm)
    public TextView btn_confirm;
    @BindView(R.id.et_question)
    public EditText et_question;
    @BindView(R.id.et_optiona)
    public EditText et_optiona;
    @BindView(R.id.et_optionb)
    public EditText et_optionb;
    @BindView(R.id.et_optionc)
    public EditText et_optionc;
    @BindView(R.id.et_optiond)
    public EditText et_optiond;
    @BindView(R.id.et_score)
    public EditText et_score;

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
        et_question.setText("");
        et_score.setText("");
        et_optiona.setText("");
        et_optionb.setText("");
        et_optionc.setText("");
        et_optiond.setText("");
    }

    public String getParams() {
        final String score = et_score.getText().toString().trim();
        final String question = et_question.getText().toString().trim();
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

        final String params = "&questionType=" + questionType + "&score=" + score +
                "&question=" + question +
                "&optiona=" + optiona +
                "&optionb=" + optionb +
                "&optionc=" + optionc +
                "&optiond=" + optiond;

        return params;
    }
}
