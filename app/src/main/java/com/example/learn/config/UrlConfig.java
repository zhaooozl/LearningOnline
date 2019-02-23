package com.example.learn.config;

public class UrlConfig {
    public static final String HOST = "http://192.168.1.111:8080/LearningOnline";
//    public static final String HOST = "http://192.168.43.125:8080/LearningOnline";
//    public static final String HOST = "http://192.168.2.1:8080/LearningOnline";

    public static final String LOCAL_HOST = "http://127.0.0.1:8080/LearningOnline";

    // 登录
    public static final String LOGIN = HOST + "/login";
    // 注册
    public static final String REGISTER = HOST + "/register";
    // 教师
    public static final String TEACHER = HOST + "/teacher";
    // 科目
    public static final String SUBJECT = HOST + "/subject";
    // 考试
    public static final String EXAM = HOST + "/exam";
    // 交卷
    public static final String SUBMITPAPER = HOST + "/submitpaper";
    // 课件
    public static final String COURSEWARE = HOST + "/courseware";
    // 删除课件
    public static final String DELETE_COURSEWARE = HOST + "/deletecourse";
    // 用户信息
    public static final String USERINFO = HOST + "/userinfo";
    // 试题
    public static final String QUESTION = HOST + "/question";
    // 上传
    public static final String UPLOAD = HOST + "/upload";
    // 评论
    public static final String COMMENT = HOST + "/comment";


    public static final String STUDENT_INFO = LOCAL_HOST + "/student";
    public static final String EXAM_INFO = LOCAL_HOST + "/exam";
    public static final String CHANGEPASSWD = HOST + "/changepasswd";

    // OKHTTP过滤器用
    public static final String LOCAL_LOGIN = LOCAL_HOST + "/login";
    public static final String LOACL_LEARN = LOCAL_HOST + "/learn";
    public static final String LOACL_COURSES = LOCAL_HOST + "/courses";
    public static final String LOCAL_STUDENT = LOCAL_HOST + "/student";
    public static final String LOACL_EXAM = LOCAL_HOST + "/exam";

}
