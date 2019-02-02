package com.example.learn.entiry;

public class BottomMenuBean {

    private int id;

    private String icon;

    private String name;

    private int normal;

    private int select;

    public BottomMenuBean(int id, String icon, String name, int normal, int select) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.normal = normal;
        this.select = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }
}
