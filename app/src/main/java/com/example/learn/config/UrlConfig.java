package com.example.learn.config;

public class UrlConfig {

    public static final String HOST = "http://192.168.43.125:8080/LearningOnline";

    public static final String LOCAL_HOST = "http://127.0.0.1:8080/LearningOnline";

    public static final String LOGIN = HOST + "/login";
    public static final String REGISTER = HOST + "/register";
    public static final String STUDENT_INFO = LOCAL_HOST + "/student";
    public static final String TEACHER = HOST + "/teacher";
    public static final String EXAM_INFO = LOCAL_HOST + "/exam";
    public static final String CHANGEPASSWD = HOST + "/changepasswd";

    // OKHTTP过滤器用
    public static final String LOCAL_LOGIN = LOCAL_HOST + "/login";
    public static final String LOACL_LEARN = LOCAL_HOST + "/learn";
    public static final String LOACL_COURSES = LOCAL_HOST + "/courses";
    public static final String LOCAL_STUDENT = LOCAL_HOST + "/student";
    public static final String LOACL_EXAM = LOCAL_HOST + "/exam";

}
