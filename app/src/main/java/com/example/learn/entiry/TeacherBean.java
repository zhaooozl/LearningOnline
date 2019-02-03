package com.example.learn.entiry;

public class TeacherBean {

    private int userId;
    private String userName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    @Override
    public String toString() {
        return "TeacherBean{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
