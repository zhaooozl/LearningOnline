package com.example.learn.delegate.student.pager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.learningonline.R;
import com.orhanobut.logger.Logger;

public class ExPagerAdapter extends PagerAdapter {

    private Context mContext = Configurator.getInstance().get(ConfigType.APP_CONTENT);

    private int count;

    private int pagerCurIndex;

    public ExPagerAdapter(int count) {
        this.count = count;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Logger.d("instantiateItem: " + position);
        View child = container.getChildAt(position);
        if (child == null) {
            child = View.inflate(mContext, R.layout.item_page, null);
            container.addView(child);
        }
        return child;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    public void setPagerCurIndex(int pagerCurIndex) {
        this.pagerCurIndex = pagerCurIndex;

    }
}
