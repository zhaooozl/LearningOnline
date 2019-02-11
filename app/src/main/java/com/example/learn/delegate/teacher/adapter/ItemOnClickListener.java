package com.example.learn.delegate.teacher.adapter;

import android.view.View;

import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.entiry.student.exam.LayoutBean;

public class ItemOnClickListener implements View.OnClickListener {

    private BaseDelegate delegate;

    private LayoutBean layoutBean;

    public ItemOnClickListener(BaseDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onClick(View v) {

    }
}
