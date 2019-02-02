package com.example.learn.delegate.student;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.delegate.student.channel.ChannelAdapter;
import com.example.learn.delegate.student.content.ContentAdapter;
import com.example.learn.delegate.student.pager.ExPagerAdapter;
import com.example.learn.entiry.CourseBean;
import com.example.learn.entiry.CourseResponseBean;
import com.example.learn.entiry.LearnBean;
import com.example.learn.entiry.TeacherBean;
import com.example.learn.learningonline.R;
import com.example.learn.net.version2.ExOKHttp;
import com.example.learn.net.version2.callback.ISuccess;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;

public class LearnDelegate extends BaseDelegate implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, ViewPager.OnPageChangeListener {

    // 频道栏
    @BindView(R.id.rv_channel)
    RecyclerView mChannelRV;

    // 频道对应的内容page
    @BindView(R.id.vp_content)
    ViewPager mPager;

    // 刷新的圈圈
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private ChannelAdapter channelAdapter;
    private List<TeacherBean> teacherBeans;

    private ExPagerAdapter pagerAdapter;

    @Override
    public Object getLayout() {
        return R.layout.delegate_learn;
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        getChannelData();
        mRefreshLayout.setOnRefreshListener(this);
        mPager.addOnPageChangeListener(this);

    }

    // 获取教师数据
    private void getChannelData() {
        Logger.d("getChannelData: ");
        new ExOKHttp.Builder()
                .url("http://127.0.0.1:8080/learn")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String responseJson) {
                        Logger.json(responseJson);
                        LearnBean learnBean = JSON.parseObject(responseJson, new TypeReference<LearnBean>() {});
                        teacherBeans = learnBean.getData();
                        channelAdapter = new ChannelAdapter(LearnDelegate.this, teacherBeans);
                        mChannelRV.setAdapter(channelAdapter);

                        ExPagerAdapter pagerAdapter = new ExPagerAdapter(teacherBeans.size());
                        mPager.setAdapter(pagerAdapter);

                        getCoursesData(0, teacherBeans.get(0).getId());


                    }
                })
                .build()
                .get();
    }

    // 获取课程数据
    private void getCoursesData(final int index, int teacherId) {
        new ExOKHttp.Builder()
                .url("http://127.0.0.1:8080/courses")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String responseJson) {
                        Logger.json(responseJson);
                        CourseResponseBean courseResponseBean = JSON.parseObject(responseJson, new TypeReference<CourseResponseBean>() {});
                        List<CourseBean> courseBeans = courseResponseBean.getData();
                        View child = mPager.getChildAt(index);
                        RecyclerView contentRV = child.findViewById(R.id.rv_content);
                        contentRV.setAdapter(new ContentAdapter(LearnDelegate.this, courseBeans));
                        contentRV.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));

                        mRefreshLayout.setRefreshing(false);
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onClick(View view) {
        if (channelAdapter != null) {
            int index = (int) view.getTag();
            channelAdapter.setCurrentItem(index);
            channelAdapter.notifyDataSetChanged();

            mPager.setCurrentItem(index);
        }
    }

    @Override
    public void onRefresh() {
        int index = channelAdapter.getCurrentItem();
        TeacherBean teacherBean = teacherBeans.get(index);
        int id = teacherBean.getId();
        getCoursesData(index, id);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        channelAdapter.setCurrentItem(position);
        channelAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
