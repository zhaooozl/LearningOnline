package com.example.learn.delegate.teacher.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.learn.learningonline.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_comment)
    TextView tv_comment;

    @BindView(R.id.tv_username)
    TextView tv_username;

    @BindView(R.id.tv_timestamp)
    TextView tv_timestamp;

    public CommentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
