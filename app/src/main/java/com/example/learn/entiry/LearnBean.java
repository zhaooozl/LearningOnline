package com.example.learn.entiry;

import java.util.List;

public class LearnBean {

    private int code;
    private String msg;
    private List<TeacherBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<TeacherBean> getData() {
        return data;
    }

    public void setData(List<TeacherBean> data) {
        this.data = data;
    }
}
