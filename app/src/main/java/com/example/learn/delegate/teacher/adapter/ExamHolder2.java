package com.example.learn.delegate.teacher.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.learn.learningonline.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamHolder2 extends RecyclerView.ViewHolder {
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

//    item
    @Nullable
    @BindView(R.id.ll_item_question)
    LinearLayout ll_item_question;
    // 问题标题
    @Nullable
    @BindView(R.id.et_question)
    TextView question;
    // 分数
    @Nullable
    @BindView(R.id.et_score)
    TextView et_score;
    // 选项0
    @Nullable
    @BindView(R.id.et_optiona)
    TextView option0;
    // 选项1
    @Nullable
    @BindView(R.id.et_optionb)
    TextView option1;
    // 选项2
    @Nullable
    @BindView(R.id.et_optionc)
    TextView option2;
    // 选项3
    @Nullable
    @BindView(R.id.et_optiond)
    TextView option3;
    // 删除
    @Nullable
    @BindView(R.id.tv_delete)
    ImageView tv_delete;
    // 更新
    @Nullable
    @BindView(R.id.tv_update)
    ImageView tv_update;

    // 填空
    @Nullable
    @BindView(R.id.et_answer)
    EditText answer;
    // 交卷
    @Nullable
    @BindView(R.id.btn_submit_exam)
    Button submitBtn;

    public ExamHolder2(View itemView, int viewType) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
