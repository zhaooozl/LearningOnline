package com.example.learn.delegate.teacher.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.delegate.student.ExamDelegate;
import com.example.learn.delegate.student.exam.ExamHolder;
import com.example.learn.delegate.student.exam.ExamItemType;
import com.example.learn.delegate.teacher.ExamPaperDelegate;
import com.example.learn.entiry.student.exam.LayoutBean;
import com.example.learn.entiry.student.exam.Option;
import com.example.learn.learningonline.R;
import com.orhanobut.logger.Logger;

import java.util.List;

public class ExamAdapter2 extends RecyclerView.Adapter<ExamHolder2> {
    private Context mContext = Configurator.getInstance().get(ConfigType.APP_CONTENT);

    private List<LayoutBean> mDatas;

    private BaseDelegate delegate;

    public ExamAdapter2(BaseDelegate delegate, List<LayoutBean> datas) {
        this.delegate = delegate;
        this.mDatas = datas;
        Logger.d("getItemCount: " + mDatas.size());
    }

    @NonNull
    @Override
    public ExamHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        Logger.d("onCreateViewHolder viewType: " + viewType);
        if (viewType == ExamItemType.NAME) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_name_exam, parent, false);
//            view.setTag(ExamItemType.NAME);
        } else if (viewType == ExamItemType.TITLE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_title_exam, parent, false);
//            view.setTag(ExamItemType.TITLE);
        } else if (viewType == ExamItemType.CHIOCE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_choice_exam2, parent, false);
//            view.setTag(ExamItemType.CHIOCE);
        } else if (viewType == ExamItemType.JUDGE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_judge_exam2, parent, false);
//            view.setTag(ExamItemType.JUDGE);
        } else if (viewType == ExamItemType.FILL_BLANK) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_fill_blank_exam2, parent, false);
//            view.setTag(ExamItemType.FILL_BLANK);
        } else if (viewType == ExamItemType.SUBMIT) {
//            view = LayoutInflater.from(mContext).inflate(R.layout.item_submit_exam, parent, false);
//            view.setTag(ExamItemType.SUBMIT);
        }
        return new ExamHolder2(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamHolder2 holder, int position) {
        Logger.d("onBindViewHolder index: " + position);
        LayoutBean layoutBean = mDatas.get(position);
        Logger.d("onBindViewHolder: " + layoutBean.toString());
        Logger.d("getLayouttype: " + layoutBean.getLayouttype());
        Logger.d("getQuestionId: " + layoutBean.getQuestionId());
        int viewType = getItemViewType(position);
        if (viewType == ExamItemType.NAME) {
            Logger.d("viewType TITLE");
            holder.examName.setText(layoutBean.getExamName());
            holder.totalScore.setText("总分:" + layoutBean.getTotalScore());
            holder.teacherName.setText("出卷人:" + layoutBean.getTeacherName());
        } else if (viewType == ExamItemType.TITLE) {
            Logger.d("viewType NAME");
            holder.examTitle.setText(layoutBean.getTitle());
        } else if (viewType == ExamItemType.CHIOCE) {
            Logger.d("viewType CHIOCE");
            holder.question.setText(layoutBean.getQuestion());
            holder.et_score.setText(layoutBean.getScore());
            Option option0 = layoutBean.getOptions().get(0);
            Logger.d("chioce: " + option0.getKey() + "." + option0.getValue());
            holder.option0.setText(option0.getKey() + "." + option0.getValue());
            Option option1 = layoutBean.getOptions().get(1);
            holder.option1.setText(option0.getKey() + "." + option1.getValue());
            Option option2 = layoutBean.getOptions().get(2);
            holder.option2.setText(option0.getKey() + "." + option2.getValue());
            Option option3 = layoutBean.getOptions().get(3);
            holder.option3.setText(option0.getKey() + "." + option3.getValue());
//            holder.ll_item_question.setOnClickListener((ExamPaperDelegate) delegate);
            holder.ll_item_question.setTag(position);
            holder.tv_delete.setOnClickListener((ExamPaperDelegate) delegate);
            holder.tv_update.setOnClickListener((ExamPaperDelegate) delegate);
            holder.tv_delete.setTag(position);
            holder.tv_update.setTag(position);
        } else if (viewType == ExamItemType.JUDGE) {
            Logger.d("viewType JUDGE");
            holder.question.setText(layoutBean.getQuestion());
            holder.et_score.setText(layoutBean.getScore());
//            holder.ll_item_question.setOnClickListener((ExamPaperDelegate) delegate);
            holder.ll_item_question.setTag(position);
            holder.tv_delete.setOnClickListener((ExamPaperDelegate) delegate);
            holder.tv_update.setOnClickListener((ExamPaperDelegate) delegate);
            holder.tv_delete.setTag(position);
            holder.tv_update.setTag(position);
        } else if (viewType == ExamItemType.FILL_BLANK) {
            Logger.d("viewType FILL_BLANK");
            holder.question.setText(layoutBean.getQuestion());
            holder.et_score.setText(layoutBean.getScore());
//            holder.ll_item_question.setOnClickListener((ExamPaperDelegate) delegate);
            holder.ll_item_question.setTag(position);
            holder.tv_delete.setOnClickListener((ExamPaperDelegate) delegate);
            holder.tv_update.setOnClickListener((ExamPaperDelegate) delegate);
            holder.tv_delete.setTag(position);
            holder.tv_update.setTag(position);
        } else if (viewType == ExamItemType.SUBMIT) {
            Logger.d("viewType SUBMIT");

//            holder.submitBtn.setOnClickListener((ExamPaperDelegate) delegate);
        }

    }

    @Override
    public int getItemCount() {
//        Logger.d("getItemCount: " + mDatas.size());
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getLayouttype();
    }


}
