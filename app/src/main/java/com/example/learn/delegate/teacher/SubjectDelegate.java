package com.example.learn.delegate.teacher;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.learn.activity.MainActivity;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.learningonline.R;
import com.joanzapata.iconify.widget.IconButton;

import butterknife.BindView;

public class SubjectDelegate extends BaseDelegate implements View.OnClickListener {

    @BindView(R.id.ib_add_subject)
    IconButton ibAddSubject;

    @Override
    public Object getLayout() {
        return R.layout.delegate_subject;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        ibAddSubject.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MainActivity activity = (MainActivity) getActivity();
        activity.start(new UploadDelegate());
    }
}
