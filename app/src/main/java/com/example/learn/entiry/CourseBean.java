package com.example.learn.entiry;

public class CourseBean {

    private int subjectId;
    private String name;
    private String desc;
    private String userId;
    private String courseware;

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int id) {
        this.subjectId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseware() {
        return courseware;
    }

    public void setCourseware(String courseware) {
        this.courseware = courseware;
    }

    @Override
    public String toString() {
        return "CourseBean{" +
                "subjectId=" + subjectId +
                ", name='" + name + '\'' +
                '}';
    }
}
