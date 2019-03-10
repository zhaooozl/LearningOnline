package com.example.learn.delegate.student.paper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.delegate.student.ExamDelegate;
import com.example.learn.delegate.student.exam.ExamHolder;
import com.example.learn.delegate.student.exam.ExamItemType;
import com.example.learn.entiry.student.exam.LayoutBean;
import com.example.learn.entiry.student.exam.Option;
import com.example.learn.learningonline.R;
import com.orhanobut.logger.Logger;

import java.util.List;

public class PaperAdapter extends RecyclerView.Adapter<PaperHolder> {
    private Context mContext = Configurator.getInstance().get(ConfigType.APP_CONTENT);

    private List<LayoutBean> mDatas;


    public PaperAdapter(List<LayoutBean> datas) {
        this.mDatas = datas;
        Logger.d("getItemCount: " + mDatas.size());
    }

    @NonNull
    @Override
    public PaperHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        Logger.d("onCreateViewHolder viewType: " + viewType);
        if (viewType == ExamItemType.NAME) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_name_paper, parent, false);
//            view.setTag(ExamItemType.NAME);
        } else if (viewType == ExamItemType.TITLE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_title_exam, parent, false);
//            view.setTag(ExamItemType.TITLE);
        } else if (viewType == ExamItemType.CHIOCE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_choice_paper, parent, false);
//            view.setTag(ExamItemType.CHIOCE);
        } else if (viewType == ExamItemType.JUDGE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_judge_paper, parent, false);
//            view.setTag(ExamItemType.JUDGE);
        } else if (viewType == ExamItemType.FILL_BLANK) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_fill_blank_paper, parent, false);
//            view.setTag(ExamItemType.FILL_BLANK);
        }
        return new PaperHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull PaperHolder holder, int position) {
        Logger.d("onBindViewHolder index: " + position);
        LayoutBean layoutBean = mDatas.get(position);
        Logger.d("onBindViewHolder: " + layoutBean.toString());
        Logger.d("getLayouttype: " + layoutBean.getLayouttype());
        Logger.d("getQuestionId: " + layoutBean.getQuestionId());
        int viewType = getItemViewType(position);
        if (viewType == ExamItemType.NAME) {
            Logger.d("viewType TITLE");
            holder.examName.setText(layoutBean.getExamName());
            holder.totalScore.setText("总分:" + layoutBean.getScore());
            holder.tv_my_total_score.setText("得分：" + layoutBean.getMyTotalScore());
            holder.teacherName.setText("出卷人:" + layoutBean.getTeacherName());
        } else if (viewType == ExamItemType.TITLE) {
            Logger.d("viewType NAME");
            holder.examTitle.setText(layoutBean.getTitle());
        } else if (viewType == ExamItemType.CHIOCE) {
            Logger.d("viewType CHIOCE");
            holder.question.setText(layoutBean.getQuestion());
            Option option0 = layoutBean.getOptions().get(0);
            Logger.d("chioce: " + option0.getKey() + "." + option0.getValue());
            holder.option0.setText(option0.getKey() + "." + option0.getValue());
            Option option1 = layoutBean.getOptions().get(1);
            holder.option1.setText(option1.getKey() + "." + option1.getValue());
            Option option2 = layoutBean.getOptions().get(2);
            holder.option2.setText(option2.getKey() + "." + option2.getValue());
            Option option3 = layoutBean.getOptions().get(3);
            holder.option3.setText(option3.getKey() + "." + option3.getValue());
//            holder.options.check();
            holder.options.setClickable(false);
            holder.tv_standardanswer.setText(layoutBean.getStandardanswer());
            holder.tv_answer.setText(layoutBean.getAnswer());
            holder.tv_myScore.setText(layoutBean.getMyScore());
        } else if (viewType == ExamItemType.JUDGE) {
            Logger.d("viewType JUDGE");
            holder.question.setText(layoutBean.getQuestion());
            holder.tv_standardanswer.setText(layoutBean.getStandardanswer());
            holder.tv_answer.setText(layoutBean.getAnswer());
            holder.tv_myScore.setText(layoutBean.getMyScore());
        } else if (viewType == ExamItemType.FILL_BLANK) {
            Logger.d("viewType FILL_BLANK");
            holder.question.setText(layoutBean.getQuestion());
            holder.tv_standardanswer.setText(layoutBean.getStandardanswer());
            holder.tv_answer.setText(layoutBean.getAnswer());
            holder.tv_myScore.setText(layoutBean.getMyScore());
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
