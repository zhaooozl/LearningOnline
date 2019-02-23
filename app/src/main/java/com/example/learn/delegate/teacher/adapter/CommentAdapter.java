package com.example.learn.delegate.teacher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.entiry.teacher.CommentBean;
import com.example.learn.learningonline.R;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    private Context mContext = Configurator.getInstance().get(ConfigType.APP_CONTENT);
    private List<CommentBean> mDatas;

    public CommentAdapter(List<CommentBean> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        CommentBean commentBean = mDatas.get(position);
        holder.tv_comment.setText(commentBean.getCommentDesc());
        holder.tv_username.setText(commentBean.getUserName());
        String timestamp = commentBean.getTimestamp();
        if (timestamp != null && !"".equals(timestamp)) {
            final String date = new SimpleDateFormat("MM-dd hh:mm").format(new Date(Long.parseLong(timestamp)));
            Logger.d("date: " + date);
            holder.tv_timestamp.setText(date);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
