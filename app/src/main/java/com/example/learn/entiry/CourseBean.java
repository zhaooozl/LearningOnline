package com.example.learn.entiry;

public class CourseBean {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
