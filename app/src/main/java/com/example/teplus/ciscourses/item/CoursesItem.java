package com.example.teplus.ciscourses.item;

public class CoursesItem {
    private String nameTh;
    private String descTh;
    private int credit;

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    private int year;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameTh() {
        return nameTh;
    }

    public void setNameTh(String nameTh) {
        this.nameTh = nameTh;
    }

    public String getDescTh() {
        return descTh;
    }

    public void setDescTh(String descTh) {
        this.descTh = descTh;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}