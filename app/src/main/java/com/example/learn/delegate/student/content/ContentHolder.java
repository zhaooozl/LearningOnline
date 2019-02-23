package com.example.learn.delegate.student.content;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.learn.learningonline.R;
import com.joanzapata.iconify.widget.IconButton;
import com.joanzapata.iconify.widget.IconTextView;

public class ContentHolder extends ViewHolder {

    TextView courseName;

    IconTextView ivExam;

    IconTextView ivDownLoad;

    IconTextView ivComment;

    ProgressBar pbProgress;


    public ContentHolder(View itemView) {
        super(itemView);
        courseName = itemView.findViewById(R.id.tv_course_name);
        ivExam = itemView.findViewById(R.id.iv_exam);
        ivDownLoad = itemView.findViewById(R.id.iv_download);
        pbProgress = itemView.findViewById(R.id.pb_progress);
        ivComment = itemView.findViewById(R.id.iv_comment);

    }
}
