package com.example.learn.delegate.bottom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.delegate.admin.CourManagerDelegate;
import com.example.learn.delegate.admin.ExamManagerDelegate;
import com.example.learn.delegate.admin.StuManagerDelegate;
import com.example.learn.delegate.admin.TeaManagerDelegate;
import com.example.learn.delegate.student.ExamDelegate;
import com.example.learn.delegate.student.LearnDelegate;
import com.example.learn.delegate.base.BaseDelegate;
import com.example.learn.delegate.student.MyDelegate;
import com.example.learn.delegate.teacher.SubjectDelegate;
import com.example.learn.entiry.BottomMenuBean;
import com.example.learn.learningonline.R;
import com.joanzapata.iconify.widget.IconTextView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class BaseBottomDelegate extends BaseDelegate implements View.OnClickListener {

    @BindView(R.id.bottom_bar)
    LinearLayout mBottomBarContainer;

    @Override
    public Object getLayout() {
        return R.layout.delegate_bottom_base;
    }

//    private int mCurMenuIndex;

    private static final int DEFAULT_MENU_INDEX = 0;

    private List<BottomMenuBean> menuBeans = new ArrayList<>();

    private Map<Integer, BaseDelegate> delegates = new HashMap<>();

    private int userType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int normal = getResources().getColor(R.color.colorNormal);
        int theme = getResources().getColor(R.color.colorTheme);
        userType = Configurator.getInstance().get(ConfigType.USER_TYPE);
        if (userType == UserType.ADMIN) {
            BottomMenuBean bean1 = new BottomMenuBean(0, "{fa-graduation-cap}", "学生管理", normal, theme);
            BottomMenuBean bean2 = new BottomMenuBean(1, "{fa-user}", "教师管理", normal, theme);
            BottomMenuBean bean3 = new BottomMenuBean(2, "{fa-user}", "课件管理", normal, theme);
            BottomMenuBean bean4 = new BottomMenuBean(3, "{fa-user}", "测试管理", normal, theme);
            BottomMenuBean bean5 = new BottomMenuBean(4, "{fa-user}", "我的", normal, theme);
            menuBeans.add(bean1);
            menuBeans.add(bean2);
            menuBeans.add(bean3);
            menuBeans.add(bean4);
            menuBeans.add(bean5);
        } else if (userType == UserType.TEACHER) {
            BottomMenuBean learnBean = new BottomMenuBean(0, "{fa-graduation-cap}", "科目", normal, theme);
            BottomMenuBean myBean = new BottomMenuBean(1, "{fa-user}", "我的", normal, theme);
            menuBeans.add(learnBean);
            menuBeans.add(myBean);
        } else if (userType == UserType.STUDENT) {
            BottomMenuBean learnBean = new BottomMenuBean(0, "{fa-graduation-cap}", "学习", normal, theme);
            BottomMenuBean myBean = new BottomMenuBean(1, "{fa-user}", "我的", normal, theme);
            menuBeans.add(learnBean);
            menuBeans.add(myBean);
        }
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {
        for (int i = 0; i < menuBeans.size(); i++) {
            Logger.d("i=======: " + i);
            // load模版
            LayoutInflater.from(getContext()).inflate(R.layout.item_bottom, mBottomBarContainer);
            // 获取item
            LinearLayout item = (LinearLayout) mBottomBarContainer.getChildAt(i);
            BottomMenuBean menuItem = menuBeans.get(i);
            item.setTag(menuItem.getId());
            item.setOnClickListener(this);
            IconTextView icon = item.findViewById(R.id.tv_icon);
            TextView name = item.findViewById(R.id.tv_name);
            int resourceId;
            if (i == DEFAULT_MENU_INDEX) {
                resourceId = menuItem.getSelect();
            } else {
                resourceId = menuItem.getNormal();
            }
            icon.setText(menuItem.getIcon());
            icon.setTextColor(resourceId);
            name.setText(menuItem.getName());
            name.setTextColor(resourceId);
        }
        BaseDelegate delegate = getDelegate(DEFAULT_MENU_INDEX);
        start(R.id.delegate_container2, delegate);
    }

    @Override
    public void onClick(View v) {
        resetMenu();
        int i = (int) v.getTag();
        Logger.d("onClick: " + i);
        BottomMenuBean menuItem = menuBeans.get(i);
        LinearLayout item = (LinearLayout) v;
        IconTextView icon = item.findViewById(R.id.tv_icon);
        icon.setTextColor(menuItem.getSelect());
        TextView name = item.findViewById(R.id.tv_name);
        name.setTextColor(menuItem.getSelect());

        BaseDelegate curDelegate = getDelegate(i);
        start(R.id.delegate_container2, curDelegate);
    }

    private BaseDelegate getDelegate(int index) {
        BaseDelegate delegate = delegates.get(index);
        if (delegate == null) {
            if (userType == UserType.ADMIN) {
                if (index == 0) {
                    delegate = new StuManagerDelegate();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userType", "2");
//                    delegate.setArguments(bundle);
                } else if (index == 1) {
                    delegate = new TeaManagerDelegate();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userType", "1");
//                    delegate.setArguments(bundle);
                } else if (index == 2) {
                    delegate = new CourManagerDelegate();
                } else if (index == 3) {
                    delegate = new ExamManagerDelegate();
                } else if (index == 4) {
                    delegate = new MyDelegate();
                }
            } else if (userType == UserType.TEACHER) {
                if (index == 0) {
                    delegate = new SubjectDelegate();
                } else if (index == 1) {
                    delegate = new MyDelegate();
                } else if (index == 2) {

                }
            } else if (userType == UserType.STUDENT) {
                if (index == 0) {
                    delegate = new LearnDelegate();
                } else if (index == 1) {
                    delegate = new MyDelegate();
                }
            }
            delegates.put(index, delegate);
        }
        return delegate;
    }

    private void resetMenu() {
        for (int i = 0; i < menuBeans.size(); i++) {
            LinearLayout item = (LinearLayout) mBottomBarContainer.getChildAt(i);
            IconTextView icon = (IconTextView) item.findViewById(R.id.tv_icon);
            icon.setTextColor(menuBeans.get(i).getNormal());
            TextView name = item.findViewById(R.id.tv_name);
            name.setTextColor(menuBeans.get(i).getNormal());
        }
    }
}
