package com.example.learn.delegate.admin.content;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.delegate.admin.CourManagerDelegate;
import com.example.learn.entiry.admin.CoursewareBean;
import com.example.learn.learningonline.R;
import com.orhanobut.logger.Logger;

import java.util.List;

public class CourAdapter extends RecyclerView.Adapter<CourViewHolder> {

    private Context mContext = Configurator.getInstance().get(ConfigType.APP_CONTENT);

    private List<CoursewareBean> mDatas;
    private CourManagerDelegate mDelegate;

    public CourAdapter(List<CoursewareBean> mDatas, CourManagerDelegate mDelegate) {
        this.mDatas = mDatas;
        this.mDelegate = mDelegate;
    }

    @NonNull
    @Override
    public CourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Logger.d("onCreateViewHolder: ");
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_subject, parent, false);
        return new CourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourViewHolder holder, int position) {
        Logger.d("onBindViewHolder position: " + position);
        CoursewareBean coursewareBean = mDatas.get(position);
        holder.courseName.setText(coursewareBean.getSubjectName());
        holder.ivDelete.setOnClickListener(mDelegate);
        holder.ivDelete.setTag(R.id.first_tag, position);
        holder.ivDelete.setTag(R.id.second_tag, coursewareBean.getSubjectId());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setmDatas(List<CoursewareBean> mDatas) {
        this.mDatas = mDatas;
    }
}
