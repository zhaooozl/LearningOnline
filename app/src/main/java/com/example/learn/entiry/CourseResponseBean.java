package com.example.learn.entiry;

import java.util.List;

public class CourseResponseBean {
    private int code;
    private String msg;
    private List<CourseBean> data;

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

    public List<CourseBean> getData() {
        return data;
    }

    public void setData(List<CourseBean> data) {
        this.data = data;
    }
}
