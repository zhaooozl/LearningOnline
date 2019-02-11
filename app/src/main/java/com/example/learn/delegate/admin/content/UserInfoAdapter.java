package com.example.learn.delegate.admin.content;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.delegate.admin.StuManagerDelegate;
import com.example.learn.delegate.admin.TeaManagerDelegate;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.entiry.admin.UserInfoBean;
import com.example.learn.learningonline.R;
import com.orhanobut.logger.Logger;

import java.util.List;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoViewHolder> {

    private Context mContext = Configurator.getInstance().get(ConfigType.APP_CONTENT);

    private List<UserInfoBean> mDatas;
    private BaseDelegate mDelegate;

    public UserInfoAdapter(List<UserInfoBean> mDatas, BaseDelegate mDelegate) {
        this.mDatas = mDatas;
        this.mDelegate = mDelegate;
    }

    @NonNull
    @Override
    public UserInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Logger.d("onCreateViewHolder: ");
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false);
        return new UserInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfoViewHolder holder, int position) {
        Logger.d("onBindViewHolder position: " + position);
        UserInfoBean userInfoBean = mDatas.get(position);
        String userType = userInfoBean.getUserType();
        String str = "";
        if ("2".equals(userType)) {
            str = "学号：";

        } else if ("1".equals(userType)) {
            str = "工号：";
        }
        holder.userId.setText(str + userInfoBean.getUserId());
        holder.userName.setText("姓名：" + userInfoBean.getUserName());
        if (mDelegate instanceof StuManagerDelegate) {
            Logger.d("onBindViewHolder StuManagerDelegate");
            holder.delete.setOnClickListener((StuManagerDelegate) mDelegate);
            holder.edit.setOnClickListener((StuManagerDelegate) mDelegate);
        } else if (mDelegate instanceof TeaManagerDelegate) {
            Logger.d("onBindViewHolder TeaManagerDelegate");
            holder.delete.setOnClickListener((TeaManagerDelegate) mDelegate);
            holder.edit.setOnClickListener((TeaManagerDelegate) mDelegate);
        }
        holder.delete.setTag(userInfoBean.getUserId());
        holder.edit.setTag(userInfoBean.getUserId());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


}
