package com.example.learn.delegate.student.exam;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.learn.learningonline.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamHolder extends RecyclerView.ViewHolder {
    // 试卷名称
    @Nullable
    @BindView(R.id.tv_name_exam)
    TextView examName;
    // 总分
    @Nullable
    @BindView(R.id.tv_total_score)
    TextView totalScore;
    // 出卷人
    @Nullable
    @BindView(R.id.tv_teacher_exam)
    TextView teacherName;
    // 题型标题
    @Nullable
    @BindView(R.id.tv_title_exam)
    TextView examTitle;
    // 问题标题
    @Nullable
    @BindView(R.id.tv_question)
    TextView question;
    // 选项组
    @Nullable
    @BindView(R.id.rg_options)
    RadioGroup options;
    // 选项0
    @Nullable
    @BindView(R.id.rb_option0)
    RadioButton option0;
    // 选项1
    @Nullable
    @BindView(R.id.rb_option1)
    RadioButton option1;
    // 选项2
    @Nullable
    @BindView(R.id.rb_option2)
    RadioButton option2;
    // 选项3
    @Nullable
    @BindView(R.id.rb_option3)
    RadioButton option3;
    // 填空
    @Nullable
    @BindView(R.id.et_answer)
    EditText answer;
    // 交卷
    @Nullable
    @BindView(R.id.btn_submit_exam)
    Button submitBtn;

    public ExamHolder(View itemView, int viewType) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
