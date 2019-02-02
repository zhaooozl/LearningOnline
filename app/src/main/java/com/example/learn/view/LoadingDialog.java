package com.example.learn.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.learn.learningonline.R;

public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context) {
        super(context);
        // dialog 背景透明
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.view_loading_dialog);
        setCanceledOnTouchOutside(false);
//        ImageView ivLoading = findViewById(R.id.iv_loading);
//        Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate_loading);
//        ivLoading.startAnimation(animation);
        // 不使用返回键
        setCancelable(false);
    }


}
