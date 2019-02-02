package com.example.learn.entiry.student.exam;

import java.util.List;

public class Question {

    private int id;
    private int type;
    private String title;
    private List<Option> options;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", type=" + type +
                ", title='" + title + '\'' +
//                ", options=" + options.size() +
                '}';
    }
}
