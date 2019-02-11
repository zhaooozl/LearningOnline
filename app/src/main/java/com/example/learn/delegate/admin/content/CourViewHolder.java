package com.example.learn.delegate.admin.content;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.learn.learningonline.R;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_course_name)
    TextView courseName;

    @BindView(R.id.iv_delete)
    AppCompatImageView ivDelete;

    public CourViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
