package com.example.learn.app;

import android.app.Application;

import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;
import com.example.learn.handler.ExHandler;
import com.example.learn.net.version2.ExOKHttp;
import com.example.learn.storage.ExPreferences;
import com.example.learn.storage.PrefKey;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class ExApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//
//        String userId = ExPreferences.getString(PrefKey.USERID.name());
//        boolean isLogin = ExPreferences.getBoolean(PrefKey.ISLOGIN.name());

        // 配置config
        Configurator
                .getInstance()
                .with(ConfigType.APP_CONTENT, getApplicationContext())  // 全局ApplicationContext
                .with(ConfigType.HANDLER, new ExHandler())             // 全局Handler
//                .with(ConfigType.USERID, userId)
//                .with(ConfigType.ISLOGIN, isLogin)
        ;
        // 初始化Logger
        Logger.addLogAdapter(new AndroidLogAdapter());

        // 图标库
        Iconify
                .with(new FontAwesomeModule())
                .with(new IoniconsModule());


    }


}
