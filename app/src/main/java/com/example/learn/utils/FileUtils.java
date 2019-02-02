package com.example.learn.utils;

import android.content.Context;

import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

    public static String getRawFile(int id) {
        Context context = Configurator.getInstance().get(ConfigType.APP_CONTENT);
        InputStream is = context.getResources().openRawResource(id);
        BufferedInputStream bis = new BufferedInputStream(is);
        InputStreamReader isr = new InputStreamReader(bis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder builder = new StringBuilder();
        String str = null;
        try {
            while ((str = br.readLine()) != null) {
                builder.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                isr.close();
                bis.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return builder.toString();
    }

}
