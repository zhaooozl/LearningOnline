package com.example.learn.delegate.student.channel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.delegate.student.LearnDelegate;
import com.example.learn.entiry.TeacherBean;
import com.example.learn.learningonline.R;

import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelHolder> {

    private Context mContext = Configurator.getInstance().get(ConfigType.APP_CONTENT);

    private List<TeacherBean> mDatas;

    private int channelCurIndex = 0;

    private LearnDelegate mDelegate;

    public ChannelAdapter(LearnDelegate delegate, List<TeacherBean> data) {
        this.mDelegate = delegate;
        this.mDatas = data;
    }

    @NonNull
    @Override
    public ChannelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_channel, parent, false);
        ChannelHolder holder = new ChannelHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelHolder holder, int position) {
        holder.mChannelNameTV.setText(mDatas.get(position).getName());
        if (channelCurIndex == position) {
            holder.mChannelNameTV.setTextColor(mContext.getResources().getColor(R.color.colorTheme));
        } else {
            holder.mChannelNameTV.setTextColor(mContext.getResources().getColor(R.color.colorNormal));
        }
        holder.mChannelNameTV.setTag(position);
        holder.mChannelNameTV.setOnClickListener(mDelegate);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setCurrentItem(int channelCurIndex) {
        this.channelCurIndex = channelCurIndex;
    }

    public int getCurrentItem() {
        return channelCurIndex;
    }

}
