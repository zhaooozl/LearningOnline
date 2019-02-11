package com.example.learn.config;

import android.content.Context;

import java.util.HashMap;

/**
 * 配置器
 *
 * 单例，饿汉
 */
public class Configurator {

    // 保存配置
    private static final HashMap<Enum<ConfigType>, Object> APP_CONFIGS = new HashMap<>();

    // 单例
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }
    // 获取单例对象
    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }


    public final Configurator with(Enum<ConfigType> key, Object value) {
        APP_CONFIGS.put(key, value);
        return this;
    }

    public final <T> T get(Enum<ConfigType> key) {
        return (T) APP_CONFIGS.get(key);
    }

    public final void clearProfile() {
        if (APP_CONFIGS.containsKey(ConfigType.ISLOGIN)) {
            APP_CONFIGS.remove(ConfigType.ISLOGIN);
        }
        if (APP_CONFIGS.containsKey(ConfigType.USER_TYPE)) {
            APP_CONFIGS.remove(ConfigType.USER_TYPE);
        }
        if (APP_CONFIGS.containsKey(ConfigType.USERID)) {
            APP_CONFIGS.remove(ConfigType.USERID);
        }
    }
}
