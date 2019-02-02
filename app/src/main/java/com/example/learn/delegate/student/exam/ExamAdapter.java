package com.example.learn.delegate.student.exam;

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
import com.example.learn.entiry.student.exam.ExamBean;
import com.example.learn.entiry.student.exam.LayoutBean;
import com.example.learn.entiry.student.exam.Option;
import com.example.learn.entiry.student.exam.Question;
import com.example.learn.learningonline.R;
import com.orhanobut.logger.Logger;

import java.util.List;

public class ExamAdapter extends RecyclerView.Adapter<ExamHolder> {
    private Context mContext = Configurator.getInstance().get(ConfigType.APP_CONTENT);

    private List<LayoutBean> mDatas;

    private ExamDelegate delegate;

    public ExamAdapter(ExamDelegate delegate, List<LayoutBean> datas) {
        this.delegate = delegate;
        this.mDatas = datas;
        Logger.d("getItemCount: " + mDatas.size());
    }

    @NonNull
    @Override
    public ExamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        Logger.d("onCreateViewHolder viewType: " + viewType);
        if (viewType == ExamItemType.NAME) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_name_exam, parent, false);
        } else if (viewType == ExamItemType.TITLE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_title_exam, parent, false);
        } else if (viewType == ExamItemType.CHIOCE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_choice_exam, parent, false);
        } else if (viewType == ExamItemType.JUDGE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_judge_exam, parent, false);
        } else if (viewType == ExamItemType.FILL_BLANK) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_fill_blank_exam, parent, false);
        } else if (viewType == ExamItemType.SUBMIT) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_submit_exam, parent, false);
        }
        return new ExamHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamHolder holder, int position) {
        LayoutBean layoutBean = mDatas.get(position);
        Logger.d("onBindViewHolder: " + layoutBean.toString());
        Logger.d("getLayouttype: " + layoutBean.getLayouttype());
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

//            Logger.d("onBindViewHolder CHIOCE: " + layoutBean.toString());
//            Logger.d("title: " + layoutBean.getTitle());
            holder.question.setText(layoutBean.getQuestion());
            Option option0 = layoutBean.getOptions().get(0);
            Logger.d("chioce: " + option0.getKey() + "." + option0.getValue());
            holder.option0.setText(option0.getKey() + "." + option0.getValue());
//            holder.option0.
            Option option1 = layoutBean.getOptions().get(1);
            holder.option1.setText(option1.getKey() + "." + option1.getValue());
            Option option2 = layoutBean.getOptions().get(2);
            holder.option2.setText(option2.getKey() + "." + option2.getValue());
            Option option3 = layoutBean.getOptions().get(3);
            holder.option3.setText(option3.getKey() + "." + option3.getValue());

            holder.options.setOnCheckedChangeListener(delegate);
        } else if (viewType == ExamItemType.JUDGE) {
            Logger.d("viewType JUDGE");

//            Logger.d("onBindViewHolder JUDGE: " + layoutBean.toString());
//            Logger.d("title: " + layoutBean.getTitle());
            holder.question.setText(layoutBean.getQuestion());
            Option option0 = layoutBean.getOptions().get(0);
            holder.option0.setText(option0.getKey() + "." + option0.getValue());
            Option option1 = layoutBean.getOptions().get(1);
            holder.option1.setText(option1.getKey() + "." + option1.getValue());
        } else if (viewType == ExamItemType.FILL_BLANK) {
            Logger.d("viewType FILL_BLANK");

//            Logger.d("onBindViewHolder FILL_BLANK: " + layoutBean.toString());
//            Logger.d("title: " + layoutBean.getTitle());
            holder.question.setText(layoutBean.getQuestion());
            holder.answer.addTextChangedListener(delegate);
        } else if (viewType == ExamItemType.SUBMIT) {
            Logger.d("viewType SUBMIT");
            holder.submitBtn.setOnClickListener(delegate);
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
