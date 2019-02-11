package com.example.learn.entiry;

public class CourseBean {

    private int subjectId;
    private String name;

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

    @Override
    public String toString() {
        return "CourseBean{" +
                "subjectId=" + subjectId +
                ", name='" + name + '\'' +
                '}';
    }
}
