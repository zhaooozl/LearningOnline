package com.example.learn.delegate.admin.content;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.learn.learningonline.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_userId)
    AppCompatTextView userId;
    @BindView(R.id.tv_user_name)
    AppCompatTextView userName;
    @BindView(R.id.iv_change)
    AppCompatImageView edit;
    @BindView(R.id.iv_delete)
    AppCompatImageView delete;

    public UserInfoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
