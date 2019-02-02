package com.example.learn.delegate.student.channel;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.learn.learningonline.R;

public class ChannelHolder extends RecyclerView.ViewHolder {

    TextView mChannelNameTV;

    public ChannelHolder(View itemView) {
        super(itemView);
        mChannelNameTV = itemView.findViewById(R.id.tv_channel_name);
    }
}
