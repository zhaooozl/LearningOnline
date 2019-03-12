package com.example.learn.delegate.student.content;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.entiry.CourseBean;
import com.example.learn.learningonline.R;
import com.orhanobut.logger.Logger;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentHolder> {

    private Context mContext = Configurator.getInstance().get(ConfigType.APP_CONTENT);

    private List<CourseBean> mDatas;
    private BaseDelegate delegate;

    public ContentAdapter(BaseDelegate delegate, List<CourseBean> mDatas) {
        this.delegate = delegate;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public ContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Logger.d("onCreateViewholder");
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_content, parent, false);
        ContentHolder holder = new ContentHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContentHolder holder, int position) {
        CourseBean bean = mDatas.get(position);
        Logger.d("onBindViewHolder: " + bean.toString());
        holder.courseName.setText(bean.getName());
//        holder.ivExam.setTag(ClickType.TAKE_EXAM);
//        holder.ivDownLoad.setTag(ClickType.DOWN_LOAD);
        holder.ivDownLoad.setTag(bean.getCourseware());
        holder.pbProgress.setVisibility(View.INVISIBLE);
        holder.ivExam.setOnClickListener(new ContentItemOnClickListener(mDatas.get(position).getSubjectId(), delegate));
        holder.ivDownLoad.setOnClickListener(new ContentItemOnClickListener(holder, delegate));
        holder.ivComment.setOnClickListener(new ContentItemOnClickListener(bean.getUserId(), delegate));
        holder.iv_query_score.setOnClickListener(new ContentItemOnClickListener(mDatas.get(position).getSubjectId(), delegate));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
